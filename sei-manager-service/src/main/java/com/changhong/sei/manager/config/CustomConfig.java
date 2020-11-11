package com.changhong.sei.manager.config;

import com.changhong.sei.manager.config.properties.IgnoreConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-10 17:06
 */
@ConfigurationProperties(prefix = "custom.config")
public class CustomConfig {

    /**
     * 不需要拦截的地址
     */
    private IgnoreConfigProperties ignores = new IgnoreConfigProperties();

    public IgnoreConfigProperties getIgnores() {
        return ignores;
    }

    public void setIgnores(IgnoreConfigProperties ignores) {
        this.ignores = ignores;
    }
}
