package com.changhong.sei.log.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-08 18:07
 */
@Component
@EnableScheduling
public class ScheduleService {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleService.class);

    @Value("${sei.log.cleanup.days:35}")
    private int cleanupDays;
    @Value("${spring.cloud.config.profile}")
    private String env;
    @Autowired
    private BaseElasticService elasticService;

    // 创建 Pattern 对象
    private static final Pattern PATTERN = Pattern.compile("\\d{4}\\.\\d{1,2}\\.\\d{1,2}");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    /**
     * 0/5 * * * * ?  每隔5秒触发一次
     * 0 0/2 * * * ?  每隔2分钟触发一次
     * 0 15 1 * * ?   每天1:15触发
     */
    @Scheduled(cron = "0 40 14 * * ?")
    public void cron() {
        LogUtil.bizLog("启动日志清理");
        ResultData<Set<String>> resultData = elasticService.getAllIndex();
        if (resultData.successful()) {
            LocalDate targetData = LocalDate.now().minusDays(cleanupDays);

            int count = 0;
            Set<String> set = resultData.getData();
            for (String index : set) {
                if (StringUtils.containsIgnoreCase(index, "-".concat(env).concat("-"))) {
                    // 现在创建 matcher 对象
                    Matcher m = PATTERN.matcher(index);
                    if (m.find()) {
                        String dateStr = m.group(0);
                        LocalDate localDate = LocalDate.parse(dateStr, FMT);
                        if (localDate.isBefore(targetData)) {
                            elasticService.deleteIndex(index);
                            count++;
                            LogUtil.bizLog("成功清理{}的日志数据", index);
                        }
                    }
                }
            }
            LogUtil.bizLog("完成{}个日志清理", count);
        } else {
            LOG.error("定时清理日志出错: {}", resultData.getMessage());
        }
    }
}
