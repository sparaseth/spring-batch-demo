package com.skptech.batch.config;

import com.skptech.batch.listener.JobCompletionNotificationListener;
import com.skptech.batch.model.Employee;
import com.skptech.batch.processor.EmployeeProcessor;
import com.skptech.batch.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeRepository employeeRepository;

    @Value("${file.input}")
    private String fileInput;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                              EmployeeRepository employeeRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public FlatFileItemReader<Employee> reader() {
        LOGGER.info("&&&&&&&& BatchConfiguration->reader &&&&&&&&");
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource(fileInput));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> employeeLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "first_name", "last_name", "email", "gender", "ip_address", "country", "dob");

        BeanWrapperFieldSetMapper<Employee> wrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        wrapperFieldSetMapper.setTargetType(Employee.class);

        employeeLineMapper.setLineTokenizer(delimitedLineTokenizer);
        employeeLineMapper.setFieldSetMapper(wrapperFieldSetMapper);
        return employeeLineMapper;
    }

    @Bean
    public RepositoryItemWriter<Employee> writer() {
        LOGGER.info("&&&&&&&& BatchConfiguration->writer &&&&&&&&");
        RepositoryItemWriter<Employee> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(employeeRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }

    @Bean
    public EmployeeProcessor processor() {
        LOGGER.info("&&&&&&&& BatchConfiguration->processor &&&&&&&&");
        return new EmployeeProcessor();
    }

    @Bean
    public Step step1() {
        LOGGER.info("&&&&&&&& BatchConfiguration->step1 &&&&&&&&");
        return stepBuilderFactory.get("csv-step").<Employee, Employee>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importEmployee(JobCompletionNotificationListener listener) {
        LOGGER.info("&&&&&&&& BatchConfiguration->importEmployee &&&&&&&&");
        return jobBuilderFactory.get("importEmployees")
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

}
