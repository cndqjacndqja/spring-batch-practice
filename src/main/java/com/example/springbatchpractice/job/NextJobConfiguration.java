package com.example.springbatchpractice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
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
                    .on("FAILED") // FAILED일 경우
                    .to(NextStep3()) // STEP2로 이동한다.
                    .on("*") // STEP2의 결과에 상관없이
                    .end()  // STEP2로 이동하면 FLOW가 종료한다.
                .from(NextStep1()) // STEP1로부터
                    .on("*") // FAILED 외에 모든 경우
                    .to(NextStep2()) // STEP2로 이동한다.
                    .next(NextStep3()) // STEP2가 정상적으로 끝나면 STEP3로 이동한다.
                    .on("*") // STEP3의 결과 관계없이
                    .end() // STEP3로 이동하면 FLOW가 종료한다.
                .end()
                .build();
    }

    @Bean
    public Step NextStep1() {
        return stepBuilderFactory.get("Step1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Next Step1----][");
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step NextStep2() {
        return stepBuilderFactory.get("Step2")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Next Step2----][");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step NextStep3() {
        return stepBuilderFactory.get("Step3")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("][----Next Step3----][");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

}
