package com.skptech.batch.scheduler;

import com.skptech.batch.processor.EmployeeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@EnableScheduling
public class BatchScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchScheduler.class);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    private Job job;
    private JobLauncher jobLauncher;

    public BatchScheduler(Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(fixedRate = 2000)
    public void launchJob() {
        LOGGER.info("&&&&&&&& BatchScheduler->launchJob Started &&&&&&&&");
        /*
        TODO - Run spring batch through scheduler
        Date date = new Date();

        JobParameters jobParameters = new JobParametersBuilder().addDate("launchDate", date).toJobParameters();
        System.out.println("Job started at " + LocalDate.now());
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        }
        System.out.println("Job completed at " + LocalDate.now());
        */
    }

}
