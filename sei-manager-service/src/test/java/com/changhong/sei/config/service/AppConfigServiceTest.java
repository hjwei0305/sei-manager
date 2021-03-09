package com.changhong.sei.config.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnit5Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-09 09:27
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AppConfigServiceTest {
    @Autowired
    private AppConfigService service;

    @Test
    void getYamlData() {
        String app = "sei-auth";
        String env = "Dev";
        String yaml = service.getYamlData(app, env);
        System.out.println(yaml);
    }

    @Test
    void saveYamlData() {
        String yaml = "spring:\n" +
                "  cloud:\n" +
                "    zookeeper:\n" +
                "      connect-string: 10.4.208.132:2181\n" +
                "      enabled: true\n" +
                "    consul:\n" +
                "      enabled: false\n" +
                "  jackson:\n" +
                "    serialization:\n" +
                "      fail_on_empty_beans: false\n" +
                "    date-format: yyyy-MM-dd HH:mm:ss\n" +
                "    time-zone: GMT+8\n" +
                "  datasource:\n" +
                "    password: 123456\n" +
                "    driverClassName: com.mysql.cj.jdbc.Driver\n" +
                "    url: jdbc:mysql://10.4.208.134:3306/sei_auth?characterEncoding=utf8&useSSL=false&useCompression=true&serverTimezone=Asia/Shanghai\n" +
                "    username: sei\n" +
                "    hikari:\n" +
                "      connection-timeout: 30000\n" +
                "      max-lifetime: 1800000\n" +
                "      minimum-idle: 10\n" +
                "      maximum-pool-size: 100\n" +
                "      idle-timeout: 180000\n" +
                "  kafka:\n" +
                "    bootstrap-servers: 10.4.208.132:9092\n" +
                "  jpa:\n" +
                "    show-sql: false\n" +
                "    hibernate:\n" +
                "      ddl-auto: none\n" +
                "      namingStrategy: org.hibernate.cfg.ImprovedNamingStrategy\n" +
                "    properties:\n" +
                "      hibernate:\n" +
                "        dialect: org.hibernate.dialect.MySQL5Dialect\n" +
                "        format_sql: true\n" +
                "  boot:\n" +
                "    admin:\n" +
                "      client:\n" +
                "        url: http://dsei.changhong.com/sei-manager\n" +
                "  redis:\n" +
                "    password: ZmkDbgaua3Rso33T\n" +
                "    database: 0\n" +
                "    port: 6379\n" +
                "    host: 10.4.208.132\n" +
                "hystrix:\n" +
                "  command:\n" +
                "    default:\n" +
                "      execution:\n" +
                "        isolation:\n" +
                "          thread:\n" +
                "            timeoutInMilliseconds: 360000\n" +
                "sei:\n" +
                "  serial:\n" +
                "    service:\n" +
                "      enable: true\n" +
                "      url: http://dsei.changhong.com/api-gateway/sei-serial\n" +
                "  auth:\n" +
                "    api-base-url: http://dsei.changhong.com/api-gateway\n" +
                "    password: 123456\n" +
                "    web-base-url: http://dsei.changhong.com/sei-portal-web\n" +
                "    password-expire: 180\n" +
                "    sso:\n" +
                "      agent-id: 123\n" +
                "      app-id: 123\n" +
                "      crop-secret: 123\n" +
                "      auth-type: weChat\n" +
                "  http:\n" +
                "    filter:\n" +
                "      ignore-auth-url: /auth/check,/auth/getAnonymousToken,/auth/verifyCode,/sso/,/task/\n" +
                "  notify:\n" +
                "    service:\n" +
                "      url: http://dsei.changhong.com/api-gateway/sei-notify\n" +
                "  edm:\n" +
                "    elasticsearch:\n" +
                "      password: dsei123456\n" +
                "      scheme: http\n" +
                "      host: 10.4.208.132:9200\n" +
                "      user: elastic\n" +
                "    service:\n" +
                "      url: http://dsei.changhong.com/api-gateway/edm-service\n" +
                "ribbon:\n" +
                "  ConnectTimeout: 180000\n" +
                "  ReadTimeout: 180000\n" +
                "logging:\n" +
                "  level:\n" +
                "    root: DEBUG\n" +
                "feign:\n" +
                "  client:\n" +
                "    config:\n" +
                "      default:\n" +
                "        loggerLevel: basic\n" +
                "        readTimeout: 600000\n" +
                "        connectTimeout: 600000";
        String app = "sei-auth";
        String env = "Dev";
        ResultData<Void> resultData = service.saveYamlData(app, env, yaml);
        System.out.println(resultData);
    }
}