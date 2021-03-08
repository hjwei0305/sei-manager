package com.changhong.sei.config.service;

import com.changhong.sei.config.entity.ReleasedConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.Ordered;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-02-20 15:52
 */
public class DbEnvironmentRepository implements EnvironmentRepository, Ordered {

    private final ReleasedConfigService configService;

    public DbEnvironmentRepository(ReleasedConfigService configService) {
        this.configService = configService;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        Environment environment = new Environment(application, profile);
        List<ReleasedConfig> configList = configService.getConfigs(application, profile, label);
        if (CollectionUtils.isNotEmpty(configList)) {
            Map<String, String> map = new HashMap<>();
            for (ReleasedConfig config : configList) {
                map.put(config.getKey(), config.getValue());
            }
            if (StringUtils.isNotBlank(label)) {
                environment.add(new PropertySource(application + "_" + profile + "_" + label, map));
            } else {
                environment.add(new PropertySource(application + "_" + profile, map));
            }
        }
        return environment;
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }
}
