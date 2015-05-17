package com.gotocon.cdworkshop;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.filters.LoggingFilter;
import com.gotocon.cdworkshop.health.StatusHealthCheck;
import com.gotocon.cdworkshop.logging.DropwizardLoggerFactory;
import com.gotocon.cdworkshop.resources.SandboxerHtmlResource;
import com.gotocon.cdworkshop.resources.SandboxerJsonResource;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class SandboxerService extends Service<SandboxerServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new SandboxerService().run(args);
    }

    @Override
    public void initialize(Bootstrap<SandboxerServiceConfiguration> bootstrap) {
        bootstrap.setName("sandboxer-service");
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(SandboxerServiceConfiguration configuration, Environment environment) throws Exception {
        DropwizardLoggerFactory.createFromDropwizardConfiguration("ServiceLoggingContext", configuration.getLoggingConfig());

        environment.addResource(new SandboxerJsonResource());
        environment.addResource(new SandboxerHtmlResource(configuration));

        environment.addHealthCheck(new StatusHealthCheck(configuration));

        environment.addFilter(new LoggingFilter(), baseUrlFor(SandboxerJsonResource.class));
        environment.addFilter(new LoggingFilter(), baseUrlFor(SandboxerHtmlResource.class));
    }

    private String baseUrlFor(Class resource) {
        URI builder = UriBuilder.fromResource(resource).build();
        return builder.toASCIIString();
    }
}
