package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.views.FreemarkerView;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/services")
public class ExternalServiceResource {

    private final Client client;
    private final SandboxerServiceConfiguration configuration;

    public ExternalServiceResource(Client client, SandboxerServiceConfiguration configuration) {
        this.configuration = configuration;
        this.client = client;
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_HTML)
    public View show() throws JsonProcessingException {
        return getView(configuration);
    }

    private View getView(SandboxerServiceConfiguration configuration) throws JsonProcessingException {
        String versionNumber = configuration.getVersionNumber();

        HashMap<String, Object> templateData = new HashMap<>();
        templateData.put("versionNumber", versionNumber);

        final List<Integer> servicePorts = new ArrayList();
        servicePorts.add(8092);
        servicePorts.add(8094);
        templateData.put("responses", contactExternalServices(servicePorts.toArray(new Integer[0])));

        return new FreemarkerView("sandboxer", templateData);
    }

    private List<String> contactExternalServices(Integer... ports) {
        List<String> responses = new ArrayList<>();
        for (Integer port : ports) {
            try {
                responses.add(this.client.resource("http://localhost:" + port + "/json").accept(MediaType.APPLICATION_JSON_TYPE).get(HelloWorldVO.class).getText());
            } catch (UniformInterfaceException | ClientHandlerException e) {
                responses.add(String.format("Service on port %d remains silent", port));
            }
        }
        return responses;
    }

    static class HelloWorldVO {
        private String text;

        public HelloWorldVO() {
            this.text = "Hello, World!";
        }

        public String getText() {
            return text;
        }

        @Override
        public boolean equals(Object other) {
            return org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this);
        }
    }
}
