package com.gotocon.cdworkshop.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gotocon.cdworkshop.logging.CustomDropwizardLoggingConfiguration;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SandboxerServiceConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String versionNumber = "undefined";

    @Valid
    @NotNull
    @JsonProperty("logging")
    private CustomDropwizardLoggingConfiguration loggingConfig = new CustomDropwizardLoggingConfiguration();

    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    public String getVersionNumber() {
        return versionNumber;
    }

    public CustomDropwizardLoggingConfiguration getLoggingConfig() {
        return loggingConfig;
    }
}
