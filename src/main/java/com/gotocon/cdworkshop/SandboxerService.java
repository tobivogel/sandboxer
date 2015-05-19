package com.gotocon.cdworkshop;

import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.filters.LoggingFilter;
import com.gotocon.cdworkshop.health.StatusHealthCheck;
import com.gotocon.cdworkshop.logging.DropwizardLoggerFactory;
import com.gotocon.cdworkshop.resources.ExternalServiceResource;
import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.client.JerseyClientBuilder;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

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
        bootstrap.addBundle(new AssetsBundle("/com/gotocon/cdworkshop/assets", "/assets"));
    }

    @Override
    public void run(SandboxerServiceConfiguration configuration, Environment environment) throws Exception {
        DropwizardLoggerFactory.createFromDropwizardConfiguration("ServiceLoggingContext", configuration.getLoggingConfig());

        environment.addHealthCheck(new StatusHealthCheck(configuration));

        final Client client = new JerseyClientBuilder().using(environment).using(configuration.getJerseyClientConfiguration()).build();
        environment.addResource(new ExternalServiceResource(client, configuration));

        environment.addFilter(new LoggingFilter(), baseUrlFor(ExternalServiceResource.class));
    }

    private String baseUrlFor(Class resource) {
        URI builder = UriBuilder.fromResource(resource).build();
        return builder.toASCIIString();
    }
}
