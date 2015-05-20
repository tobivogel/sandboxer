package com.gotocon.cdworkshop.resources;

import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.connector.ServiceConnector;
import com.gotocon.cdworkshop.views.FreemarkerView;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExampleHtmlResourceTest {

    private ExternalServiceResource externalServiceResource;
    private SandboxerServiceConfiguration serviceConfigurationMock;
    private ServiceConnector serviceConnectorMock;

    @Before
    public void setUp() {
        serviceConfigurationMock = mock(SandboxerServiceConfiguration.class);
        serviceConnectorMock = mock(ServiceConnector.class);
        given(serviceConfigurationMock.getVersionNumber()).willReturn("20.2");

        externalServiceResource = new ExternalServiceResource(serviceConnectorMock, serviceConfigurationMock);
    }

    @Test
    public void shouldPrepareTemplateWithoutAnyClientResponding() throws Exception {
        // Given
        given(serviceConfigurationMock.getClientEndpoints()).willReturn(new String[0]);

        // when
        final FreemarkerView actualView = (FreemarkerView) externalServiceResource.show();

        // then
        final Map templateData = (Map) actualView.getTemplateData();
        assertThat((String) templateData.get("versionNumber"), is("20.2"));
        assertThat((List<?>) templateData.get("clients"), hasSize(0));
    }

    @Test
    public void shouldAddResponseFromClientToTemplate() throws Exception {
        // Given
        final String someEndpoint = "http://some.endpoint.com";
        final String expectedResponse = "someResponse";
        given(serviceConfigurationMock.getClientEndpoints()).willReturn(new String[]{someEndpoint});
        given(serviceConnectorMock.callEndpointForClass(someEndpoint, HelloWorldVO.class)).willReturn(new HelloWorldVO(expectedResponse));

        // when
        final FreemarkerView actualView = (FreemarkerView)externalServiceResource.show();

        // then
        final Map templateData = (Map) actualView.getTemplateData();
        assertThat((List<?>) templateData.get("clients"), hasSize(1));
        final Map<String, String> clientResponse = ((List<Map<String, String>>) templateData.get("clients")).get(0);
        assertThat(clientResponse.get("endpoint"), is(someEndpoint));
        assertThat(clientResponse.get("response"), is(expectedResponse));
    }
}