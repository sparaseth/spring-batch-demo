package com.skptech.batch;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class SpringBatchDemoApplication implements CommandLineRunner {

    @Autowired
    private Job job;
    @Autowired
    private JobLauncher jobLauncher;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Date date = new Date();
        JobParameters jobParameters = new JobParametersBuilder().addDate("launchDate", date).toJobParameters();
        LOGGER.info("Job started at " + LocalDate.now());
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
            LOGGER.error("Error occurred while running the job");
            e.printStackTrace();
        }
        LOGGER.info("Job completed at " + LocalDate.now());
    }

}
