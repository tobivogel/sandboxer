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
import java.util.Map;

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

        templateData.put("clients", contactExternalServices(configuration.getClientPorts()));

        return new FreemarkerView("sandboxer", templateData);
    }

    private List<Map<String, String>> contactExternalServices(Integer... ports) {
        List<Map<String, String>> responses = new ArrayList<>();
        for (Integer port : ports) {
            final HashMap<String, String> result = new HashMap<>();
            result.put("port", port.toString());
            try {
                result.put("response", this.client.resource("http://localhost:" + port + "/json").accept(MediaType.APPLICATION_JSON_TYPE).get(HelloWorldVO.class).getText());
            } catch (UniformInterfaceException | ClientHandlerException e) {
                result.put("response", String.format("Service on port %d remains silent", port));
            }
            responses.add(result);
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
