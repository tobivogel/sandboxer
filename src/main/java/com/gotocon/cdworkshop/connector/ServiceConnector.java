package com.gotocon.cdworkshop.connector;

import com.sun.jersey.api.client.Client;

import javax.ws.rs.core.MediaType;

public class ServiceConnector {
    private Client client;

    public ServiceConnector(Client client) {
        this.client = client;
    }

    public <T> T callEndpointForClass(String endpoint, Class<T> clazz) {
        return client.resource(endpoint).accept(MediaType.APPLICATION_JSON_TYPE).get(clazz);
    }
}
