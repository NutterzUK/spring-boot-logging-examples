package com.example.demo.rule;

// ch.qos.logback is tied to a specific logging implementation. This coupling is contained within this class.
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class LogVerifier implements TestRule {

    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    private final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                beforeTest();
                base.evaluate();
                afterTest();
            }
        };
    }

    private void beforeTest() {
        logger.addAppender(listAppender);
        listAppender.start();
    }

    private void afterTest() {
        listAppender.stop();
        listAppender.list.clear();
        logger.detachAppender(listAppender);
    }

    public void verifyMessage(String message) {
        assertThat(listAppender.list.stream().map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.toList()),
                contains(message));
    }
}