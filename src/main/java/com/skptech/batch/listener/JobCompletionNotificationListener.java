package com.skptech.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("&&&&&&& JobCompletionNotificationListener->afterJob &&&&&&&");
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("&&&&&&& JobCompletionNotificationListener->beforeJob &&&&&&&");
    }
}
