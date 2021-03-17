package com.changhong.sei.cicd.schedule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-08 18:07
 */
@EnableScheduling
@Configuration
@ConditionalOnProperty(value = "sei.build.timing", havingValue = "true")
public class ScheduleConfig {

    @Bean
    public ScheduleService scheduleService() {
        return new ScheduleService();
    }
}
