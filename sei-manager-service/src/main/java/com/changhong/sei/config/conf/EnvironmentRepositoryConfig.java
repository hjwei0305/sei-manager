package com.changhong.sei.config.conf;

import com.changhong.sei.config.service.DbEnvironmentRepository;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-02-20 15:55
 */
@Configuration
public class EnvironmentRepositoryConfig {

    @Bean
    public EnvironmentRepository environmentRepository() {
        return new DbEnvironmentRepository();
    }
}
