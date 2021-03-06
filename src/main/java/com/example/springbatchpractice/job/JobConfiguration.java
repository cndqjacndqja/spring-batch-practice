package com.example.springbatchpractice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job TestJob() {
        return jobBuilderFactory.get("Job")
                .start(TestStep1())
                .next(TestStep2())
                .build();
    }

    @Bean
    public Step TestStep1() {
        return stepBuilderFactory.get("Step")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Test Step1----][");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step TestStep2() {
        return stepBuilderFactory.get("Step")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Test Step2----][");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
