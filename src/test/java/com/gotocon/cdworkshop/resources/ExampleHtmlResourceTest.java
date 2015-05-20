package com.gotocon.cdworkshop.resources;

import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.connector.HttpServiceConnector;
import com.gotocon.cdworkshop.model.WebsiteFragmentVO;
import com.gotocon.cdworkshop.views.FreemarkerView;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExampleHtmlResourceTest {

    private ExternalServiceResource externalServiceResource;
    private SandboxerServiceConfiguration serviceConfigurationMock;
    private HttpServiceConnector serviceConnectorMock;

    @Before
    public void setUp() {
        serviceConfigurationMock = mock(SandboxerServiceConfiguration.class);
        serviceConnectorMock = mock(HttpServiceConnector.class);
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
    public void shouldAddHttpStatusAndResponseFromClientToTemplate() throws Exception {
        // Given
        final String someEndpoint = "http://some.endpoint.com";
        final String expectedAuthor = "me";
        final String expectedComment = "nothing";
        final String expectedPayload = "someHTML";
        given(serviceConfigurationMock.getClientEndpoints()).willReturn(new String[]{someEndpoint});
        given(serviceConnectorMock.callEndpointForStatus(someEndpoint)).willReturn(ClientResponse.Status.OK.getStatusCode());
        given(serviceConnectorMock.callEndpointForClass(someEndpoint, WebsiteFragmentVO.class)).willReturn(new WebsiteFragmentVO(expectedAuthor, expectedComment, expectedPayload));

        // when
        final FreemarkerView actualView = (FreemarkerView)externalServiceResource.show();

        // then
        final Map templateData = (Map) actualView.getTemplateData();
        assertThat((List<?>) templateData.get("clients"), hasSize(1));
        final Map<String, String> clientResponse = ((List<Map<String, String>>) templateData.get("clients")).get(0);
        assertThat(clientResponse.get("endpoint"), is(someEndpoint));
        assertThat(clientResponse.get("status"), is("OK"));
        assertThat(clientResponse.get("statusCode"), is("200"));
        assertThat(clientResponse.get("author"), is(expectedAuthor));
        assertThat(clientResponse.get("comment"), is(expectedComment));
        assertThat(clientResponse.get("payload"), is(expectedPayload));
    }

    @Test
    public void shouldAddCorrespondingHttpStatusFromClientIfUnavailable() throws Exception {
        // Given
        final String someEndpoint = "http://some.endpoint.com";
        given(serviceConfigurationMock.getClientEndpoints()).willReturn(new String[]{someEndpoint});
        given(serviceConnectorMock.callEndpointForStatus(someEndpoint)).willReturn(ClientResponse.Status.SERVICE_UNAVAILABLE.getStatusCode());

        // when
        final FreemarkerView actualView = (FreemarkerView)externalServiceResource.show();

        // then
        final Map templateData = (Map) actualView.getTemplateData();
        assertThat((List<?>) templateData.get("clients"), hasSize(1));
        final Map<String, String> clientResponse = ((List<Map<String, String>>) templateData.get("clients")).get(0);
        assertThat(clientResponse.get("status"), is("NOK"));
        assertThat(clientResponse.get("statusCode"), is("503"));
        assertThat(clientResponse.get("author"), is(not(nullValue())));
        assertThat(clientResponse.get("comment"), is(not(nullValue())));
        assertThat(clientResponse.get("payload"), is(not(nullValue())));
    }
}