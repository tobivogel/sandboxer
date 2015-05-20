package com.gotocon.cdworkshop.connector;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.MediaType;

import static java.lang.String.format;

public class HttpServiceConnector {
    private Client client;

    public HttpServiceConnector(Client client) {
        this.client = client;
    }

    public <T> T callEndpointForClass(String endpoint, Class<T> clazz) {
        return client.resource(endpoint).accept(MediaType.APPLICATION_JSON_TYPE).get(clazz);
    }

    public Integer callEndpointForStatus(String endpoint) {
        final ClientResponse clientResponse;
        try {
            clientResponse = client.resource(endpoint).get(ClientResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException e) {
            System.out.println(format("Service (%s) unavailable", endpoint));
            return HttpStatus.SC_SERVICE_UNAVAILABLE;
        }
        return clientResponse.getStatus();
    }
}
