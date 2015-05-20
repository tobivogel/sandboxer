package com.gotocon.cdworkshop.resources;

import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;
import com.gotocon.cdworkshop.connector.ServiceConnector;
import com.gotocon.cdworkshop.views.FreemarkerView;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExampleHtmlResourceTest {

    @Test
    public void shouldReturnViewWithoutAnyClientportsSpecified() throws Exception {
        // Given
        SandboxerServiceConfiguration serviceConfigurationMock = mock(SandboxerServiceConfiguration.class);
        ServiceConnector serviceConnector = mock(ServiceConnector.class);

        given(serviceConfigurationMock.getVersionNumber()).willReturn("20.2");
        given(serviceConfigurationMock.getClientEndpoints()).willReturn(new String[0]);

        ExternalServiceResource externalServiceResource = new ExternalServiceResource(serviceConnector, serviceConfigurationMock);

        // when
        final FreemarkerView actualView = (FreemarkerView)externalServiceResource.show();

        // then
        final Map templateData = (Map) actualView.getTemplateData();
        assertThat((String) templateData.get("versionNumber"), is("20.2"));
        assertThat((List<?>) templateData.get("clients"), hasSize(0));
    }
}