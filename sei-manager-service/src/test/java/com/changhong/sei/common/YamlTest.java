package com.changhong.sei.common;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-08 12:52
 */
public class YamlTest {

    @Test
    public void properties2Yaml() {
        String s = YamlTransferUtils.properties2Yaml("sei.application.code=BASIC\n" +
                "sei.application.env=dev");
        System.out.println(s);
    }

    @Test
    public void yml2Properties() {
        String yaml = "sei:\n" +
                "  application:\n" +
                "    code: \"BASIC\"\n" +
                "    env: \"dev\"";
        YamlTransferUtils.yaml2Properties(yaml);
    }

    @Test
    public void yaml2Map() {
        String yaml = "application_parameters:\n" +
                "  default-user:\n" +
                "    tenant-code: ${TENANT_CODE}\n" +
                "    account: ${TENANT_ACCOUNT}\n" +
                "\n" +
                "spring:\n" +
                "  main: \n" +
                "    allow-bean-definition-overriding: true\n" +
                "  jackson: \n" +
                "    date-format: yyyy-MM-dd HH:mm:ss\n" +
                "    time-zone: GMT+8    \n" +
                "    serialization:\n" +
                "      fail_on_empty_beans: false \n" +
                "  datasource:\n" +
                "    driver-class-name: com.mysql.cj.jdbc.Driver\n" +
                "    url: jdbc:mysql://${DB_SERVER}/sei_eai?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai\n" +
                "    username: ${DB_USERNAME}\n" +
                "    password: ${DB_PASSWORD}\n" +
                "    hikari:\n" +
                "      minimum-idle: 10\n" +
                "      maximum-pool-size: 100\n" +
                "      connection-timeout: 30000\n" +
                "      idle-timeout: 180000\n" +
                "      max-lifetime: 1800000\n" +
                "  jpa:\n" +
                "    hibernate:\n" +
                "      ddl-auto: none\n" +
                "      namingStrategy: org.hibernate.cfg.ImprovedNamingStrategy\n" +
                "    properties:\n" +
                "      hibernate:\n" +
                "        dialect: org.hibernate.dialect.MySQL5Dialect\n" +
                "        format_sql: true\n" +
                "    show-sql: true\n" +
                "  cache:\n" +
                "    redis:\n" +
                "      time-to-live: 20s\n" +
                "  redis:\n" +
                "    host: ${REDIS_SERVER}\n" +
                "    port: ${REDIS_PORT}\n" +
                "    password: ${REDIS_PASSWORD}\n" +
                "    database: 0\n" +
                "    \n" +
                "sei:\n" +
                "  serial:\n" +
                "    service:\n" +
                "      url: http://${GATEWAY_HOST}/sei-serial\n" +
                "      enable: true\n" +
                "  flow:\n" +
                "    server-name: flow-service\n" +
                "    server-url: http://${FLOW_SERVER}/flow-service/\n" +
                "  http:\n" +
                "    filter:\n" +
                "      ignore-auth-url:  /webservice/ErpVoucherSyncService\n" +
                "eai:\n" +
                "  sap-sender: SEI_DEV\n" +
                "ECC_PI_URL_SUBJECTBALANCE: http://10.3.1.224:8000/sap/bc/srt/rfc/sap/zws_fi_fssc_kmye/800/zws_fi_fssc_kmye/zws_fi_fssc_kmye\n" +
                "ECC_PI_URL_ZONGZHANGALLNEW: http://10.3.1.224:8000/sap/bc/srt/rfc/sap/zws_fi_fssc_gi_detail/800/zws_fi_fssc_gi_detail/zws_fi_fssc_gi_detail\n" +
                "\n" +
                "SAP_USER: rsx\n" +
                "SAP_PASSWORD: 123456bb";
        Map<String, Object> dataMap = YamlTransferUtils.yaml2Map(yaml);
        System.out.println(dataMap);
    }

    @Test
    public void map2Yaml() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("sei.application.code", "BASIC");
        data.put("sei.application.env", "dev");
        data.put("sei.traits", new String[]{"ONE_HAND", "ONE_EYE"});
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);
        System.out.println(writer.toString());
        System.out.println(YamlTransferUtils.properties2Yaml(writer.toString()));
    }
}
