package com.gotocon.cdworkshop.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.net.SyslogAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;
import com.google.common.collect.ImmutableMap;
import com.yammer.dropwizard.logging.LogbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.gotocon.cdworkshop.logging.CustomDropwizardLoggingConfiguration.AppenderConfiguration;

public class DropwizardLoggerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DropwizardLoggerFactory.class);
    private static LoggerContext context;
    private static String ROOT_LOGGER_NAME = "root";

    public static void createFromDropwizardConfiguration(String loggerContextName, CustomDropwizardLoggingConfiguration loggingConfig) {
        LoggerContext originalLoggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        // assume SLF4J is bound to logback in the current environment
        context = originalLoggerContext;
        context.reset();
        context.setName(loggerContextName);

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        //Build all appenders
        ImmutableMap<String, AppenderConfiguration> appenderConfigs = loggingConfig.getAppenders();
        Map<String, Appender> appenders = new HashMap<String, Appender>();
        for (String appenderName : appenderConfigs.keySet()) {
            AppenderConfiguration currentAppenderConfig = appenderConfigs.get(appenderName);
            if (currentAppenderConfig.getConsole().isEnabled()) {
                ConsoleAppender<ILoggingEvent> consoleAppender = LogbackFactory.buildConsoleAppender(currentAppenderConfig.getConsole(), context, currentAppenderConfig.getConsole().getLogFormat());
                consoleAppender.setName(appenderName);
                appenders.put(appenderName, consoleAppender);
            }
            if (currentAppenderConfig.getFile().isEnabled()) {
                FileAppender<ILoggingEvent> fileAppender = LogbackFactory.buildFileAppender(currentAppenderConfig.getFile(), context, currentAppenderConfig.getFile().getLogFormat());
                fileAppender.setName(appenderName);
                appenders.put(appenderName, fileAppender);
            }
            if (currentAppenderConfig.getSyslog().isEnabled()) {
                SyslogAppender syslogAppender = LogbackFactory.buildSyslogAppender(currentAppenderConfig.getSyslog(), context, appenderName, currentAppenderConfig.getSyslog().getLogFormat());
                syslogAppender.setName(appenderName);
                appenders.put(appenderName, syslogAppender);
            }
        }

        ImmutableMap<String, CustomDropwizardLoggingConfiguration.LoggerConfiguration> loggerConfigs = loggingConfig.getLoggers();

        //Root logger
        ch.qos.logback.classic.Logger root = context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        CustomDropwizardLoggingConfiguration.LoggerConfiguration rootConfig = loggerConfigs.get(ROOT_LOGGER_NAME);
        configureLogger(root, appenders, rootConfig);

        //Other loggers
        for (String currentLoggerName : loggerConfigs.keySet()) {
            if (ROOT_LOGGER_NAME != currentLoggerName) {
                ch.qos.logback.classic.Logger logger = context.getLogger(currentLoggerName);
                configureLogger(logger, appenders, loggerConfigs.get(currentLoggerName));
            }
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(originalLoggerContext);
    }

    private static void configureLogger(ch.qos.logback.classic.Logger logger, Map<String, Appender> appenders, CustomDropwizardLoggingConfiguration.LoggerConfiguration loggerConfig) {
        if (loggerConfig != null) {
            if (loggerConfig.getLevel() != null) {
                logger.setLevel(loggerConfig.getLevel());
            }
            if (loggerConfig.isAdditive() != null) {
                logger.setAdditive(loggerConfig.isAdditive());
            }
            if (loggerConfig.getAppenders() != null) {
                for (String currentAppenderName : loggerConfig.getAppenders()) {
                    logger.addAppender(appenders.get(currentAppenderName));
                }
            }
        }
    }

    private static void printLoggingConfiguration(LoggerContext lc) {
        System.out.println(lc.getName());
        for (ch.qos.logback.classic.Logger log : lc.getLoggerList()) {
//            System.out.println(log.getName() + "/ " + log.getLevel() + "/ " + hasAppenders(log));
            if (log.getLevel() != null || hasAppenders(log)) {
                System.out.println(log.getName() + "(level=" + log.getLevel() + ", additive=" + log.isAdditive() + ")-->" + getAppenders(log));
            }
        }
    }

    private static boolean hasAppenders(ch.qos.logback.classic.Logger logger) {
        Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders();
        return it.hasNext();
    }

    private static String getAppenders(ch.qos.logback.classic.Logger logger) {
        StringBuilder builder = new StringBuilder();
        Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders();
        while (it.hasNext()) {
            builder.append(it.next().getName());
            builder.append("|");
        }
        return builder.toString();
    }
}
