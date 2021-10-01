package com.example.springbatchpractice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class NextJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job NextJob() {
        return jobBuilderFactory.get("NextJob")
                .start(NextStep1())
                .next(NextStep2())
                .build();
    }

    @Bean
    public Step NextStep1() {
        return stepBuilderFactory.get("Step")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Next Step1----][");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step NextStep2() {
        return stepBuilderFactory.get("Step")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Next Step2----][");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

}
