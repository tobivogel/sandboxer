package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.connector.ServiceConnector;
import com.gotocon.cdworkshop.views.FreemarkerView;
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

    private final ServiceConnector serviceConnector;
    private final SandboxerServiceConfiguration configuration;

    public ExternalServiceResource(ServiceConnector serviceConnector, SandboxerServiceConfiguration configuration) {
        this.configuration = configuration;
        this.serviceConnector = serviceConnector;
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

        templateData.put("clients", fetchResponsesFromEndpoints(configuration.getClientEndpoints()));

        return new FreemarkerView("sandboxer", templateData);
    }

    private List<Map<String, String>> fetchResponsesFromEndpoints(String... clientEndpoints) {
        List<Map<String, String>> responses = new ArrayList<>();
        for (String endpoint : clientEndpoints) {
            final HashMap<String, String> result = new HashMap<>();
            result.put("endpoint", endpoint);
            try {
                HelloWorldVO helloWorldVO = serviceConnector.callEndpointForClass(endpoint, HelloWorldVO.class);
                result.put("response", helloWorldVO.getText());
            } catch (UniformInterfaceException | ClientHandlerException e) {
                result.put("response", String.format("Service with endpoint %s remains silent", endpoint));
            }
            responses.add(result);
        }
        return responses;
    }
}
