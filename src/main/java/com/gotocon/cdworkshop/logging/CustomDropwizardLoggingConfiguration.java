package com.gotocon.cdworkshop.logging;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.yammer.dropwizard.config.LoggingConfiguration.*;

public class CustomDropwizardLoggingConfiguration {

    @NotNull
    @JsonProperty
    private ImmutableMap<String, AppenderConfiguration> appenders = ImmutableMap.of();

    @NotNull
    @JsonProperty
    private ImmutableMap<String, LoggerConfiguration> loggers = ImmutableMap.of();

    public CustomDropwizardLoggingConfiguration() {
    }

    public ImmutableMap<String, AppenderConfiguration> getAppenders() {
        return appenders;
    }

    public ImmutableMap<String, LoggerConfiguration> getLoggers() {
        return loggers;
    }

    public static class AppenderConfiguration {
        @Valid
        @NotNull
        @JsonProperty
        private ConsoleConfiguration console = new ConsoleConfiguration();

        @Valid
        @NotNull
        @JsonProperty
        private FileConfiguration file = new FileConfiguration();

        @Valid
        @NotNull
        @JsonProperty
        private SyslogConfiguration syslog = new SyslogConfiguration();

        public AppenderConfiguration() {
        }

        public ConsoleConfiguration getConsole() {
            return console;
        }

        public FileConfiguration getFile() {
            return file;
        }

        public SyslogConfiguration getSyslog() {
            return syslog;
        }
    }

    public static class LoggerConfiguration {
        @JsonProperty
        private Level level;

        @JsonProperty
        private List<String> appenders;

        @JsonProperty
        private Boolean additive;

        public LoggerConfiguration() {
        }

        public Level getLevel() {
            return level;
        }

        public List<String> getAppenders() {
            return appenders;
        }

        public Boolean isAdditive() {
            return additive;
        }
    }
}
