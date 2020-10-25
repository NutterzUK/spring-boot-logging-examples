package com.example.demo.rule;

// org.apache.logging.log4j is tied to a specific logging implementation. This coupling is contained within this class.
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class LogVerifier extends ExternalResource {

    private static final String APPENDER_NAME = "TestAppender";
    private ByteArrayOutputStream outContent;

    @Override
    protected void before() {
        outContent = new ByteArrayOutputStream();
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        loggerContext.getConfiguration().getRootLogger().setLevel(Level.INFO);
        Appender appender = WriterAppender.newBuilder()
                .setName(APPENDER_NAME)
                .setTarget(new OutputStreamWriter(outContent))
                .build();
        appender.start();
        loggerContext.getConfiguration().addAppender(appender);
        loggerContext.getRootLogger().addAppender(loggerContext.getConfiguration().getAppender(appender.getName()));
        loggerContext.updateLoggers();
    }

    @Override
    protected void after() {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext();
        Configuration configuration = loggerContext.getConfiguration();
        configuration.getRootLogger().removeAppender(APPENDER_NAME);
    }

    public void verifyMessage(String message) {
        assertThat(outContent.toString(), containsString(message));
    }
}