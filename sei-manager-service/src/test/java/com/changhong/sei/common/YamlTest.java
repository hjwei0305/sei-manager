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
        String yaml = "sei:\n" +
                "  application:\n" +
                "    code: \"BASIC\"\n" +
                "    env: \"dev\"";
        YamlTransferUtils.yaml2Map(yaml);
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
