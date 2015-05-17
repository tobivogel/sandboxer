package com.gotocon.cdworkshop.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.views.FreemarkerView;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/html")
public class SandboxerHtmlResource {

    private SandboxerServiceConfiguration configuration;

    public SandboxerHtmlResource(SandboxerServiceConfiguration configuration){
        this.configuration = configuration;
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

        final ArrayList<Integer> servicePorts = new ArrayList();
        servicePorts.add(8092);
        servicePorts.add(8094);
        templateData.put("servicePorts", servicePorts);

        return new FreemarkerView("sandboxer", templateData);
    }
}
