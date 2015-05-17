package com.gotocon.cdworkshop.health;

import com.yammer.metrics.core.HealthCheck;
import com.gotocon.cdworkshop.configuration.SandboxerServiceConfiguration;

public class StatusHealthCheck extends HealthCheck{

    private SandboxerServiceConfiguration configuration;

    public StatusHealthCheck(SandboxerServiceConfiguration configuration) {
        super("StatusHealthCheck");
        this.configuration = configuration;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy(String.format("Service Version: %s", configuration.getVersionNumber()));
    }
}
