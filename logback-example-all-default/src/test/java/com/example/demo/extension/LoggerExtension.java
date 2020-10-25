package com.example.demo.extension;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerExtension implements BeforeEachCallback, AfterEachCallback {
    private Logger logger;
    private ListAppender<ILoggingEvent> appender;
    private final String loggerName;
    private final Level level;

    public LoggerExtension() {
        this(Level.ALL, Logger.ROOT_LOGGER_NAME);
    }

    public LoggerExtension(Level level, String loggerName) {
        this.loggerName = loggerName;
        this.level = level;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        appender = new ListAppender<>();
        logger = (Logger) LoggerFactory.getLogger(loggerName);
        logger.addAppender(appender);
        logger.setLevel(level);
        appender.start();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        logger.detachAppender(appender);
    }

    public void verifyMessage(String message) {
        assertThat(appender.list.stream().map(ILoggingEvent::getFormattedMessage)
                        .collect(Collectors.toList()).contains(message));
    }
}