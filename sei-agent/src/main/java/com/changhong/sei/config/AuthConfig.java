package com.changhong.sei.config;

import com.changhong.sei.core.filter.SessionUserAuthenticationHandler;
import com.changhong.sei.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-24 15:50
 */
public class AuthConfig {

    @Bean
    public SessionUserAuthenticationHandler sessionUserAuthenticationHandler() {
        return new JwtAuthenticationFilter();
    }
}
