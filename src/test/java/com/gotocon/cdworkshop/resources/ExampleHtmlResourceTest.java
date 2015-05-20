package com.gotocon.cdworkshop.resources;

import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.views.FreemarkerView;
import com.sun.jersey.api.client.Client;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExampleHtmlResourceTest {

    @Test
    public void shouldReturnViewWithoutAnyClientportsSpecified() throws Exception {
        // Given
        SandboxerServiceConfiguration serviceConfigurationMock = mock(SandboxerServiceConfiguration.class);
        Client clientMock = mock(Client.class);
//        final WebResource webResourceMock = mock(WebResource.class);
//        WebResource.Builder builderMock = mock(WebResource.Builder.class);
//        given(clientMock.resource(anyString())).willReturn(webResourceMock);
//        given(webResourceMock.accept(any(MediaType.class))).willReturn(builderMock);

        given(serviceConfigurationMock.getVersionNumber()).willReturn("20.2");
        given(serviceConfigurationMock.getClientEndpoints()).willReturn(new String[0]);

        ExternalServiceResource externalServiceResource = new ExternalServiceResource(clientMock, serviceConfigurationMock);

        // when
        final FreemarkerView actualView = (FreemarkerView)externalServiceResource.show();

        // then
        assertThat((String)((Map)actualView.getTemplateData()).get("versionNumber"), is("20.2"));
    }

}