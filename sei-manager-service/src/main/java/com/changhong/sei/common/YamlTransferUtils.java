package com.changhong.sei.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能：yaml配置处理
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-08 13:29
 */
public class YamlTransferUtils {

    private static final String DOT = ".";

    /**
     * yaml转properties字符串
     *
     * @param yamlContent yaml内容
     * @return properties字符串
     */
    public static String yaml2Properties(String yamlContent) {
        StringBuilder result = new StringBuilder();

        YAMLFactory yamlFactory = new YAMLFactory();
        try (YAMLParser parser = yamlFactory.createParser(yamlContent)) {

            StringBuilder key = new StringBuilder();
            String value;
            JsonToken token = parser.nextToken();
            while (token != null) {
                if (!JsonToken.START_OBJECT.equals(token)) {
                    if (JsonToken.FIELD_NAME.equals(token)) {
                        if (key.length() > 0) {
                            key.append(DOT);
                        }
                        key.append(parser.getCurrentName());

                        token = parser.nextToken();
                        if (JsonToken.START_OBJECT.equals(token)) {
                            continue;
                        }
                        value = parser.getText();
                        result.append(key).append("=").append(value).append("\n\r");

                        int dotOffset = key.lastIndexOf(DOT);
                        if (dotOffset > 0) {
                            key = new StringBuilder(key.substring(0, dotOffset));
                        }
                    } else if (JsonToken.END_OBJECT.equals(token)) {
                        int dotOffset = key.lastIndexOf(DOT);
                        if (dotOffset > 0) {
                            key = new StringBuilder(key.substring(0, dotOffset));
                        } else {
                            key = new StringBuilder();
                            result.append("\n\r");
                        }
                    }
                }  // do nothing

                token = parser.nextToken();
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * yaml转Map对象
     * @param yamlContent yaml内容
     * @return Map对象
     */
    public static Map<String, Object> yaml2Map(String yamlContent) {
        Map<String, Object> result = new HashMap<>();
        YAMLFactory yamlFactory = new YAMLFactory();
        try (YAMLParser parser = yamlFactory.createParser(yamlContent)) {
            StringBuilder key = new StringBuilder();
            String value;
            JsonToken token = parser.nextToken();
            while (token != null) {
                if (!JsonToken.START_OBJECT.equals(token)) {
                    if (JsonToken.FIELD_NAME.equals(token)) {
                        if (key.length() > 0) {
                            key.append(DOT);
                        }
                        key.append(parser.getCurrentName());

                        token = parser.nextToken();
                        if (JsonToken.START_OBJECT.equals(token)) {
                            continue;
                        }
                        value = parser.getText();
                        result.put(key.toString(), value);

                        int dotOffset = key.lastIndexOf(DOT);
                        if (dotOffset > 0) {
                            key = new StringBuilder(key.substring(0, dotOffset));
                        }
                    } else if (JsonToken.END_OBJECT.equals(token)) {
                        int dotOffset = key.lastIndexOf(DOT);
                        if (dotOffset > 0) {
                            key = new StringBuilder(key.substring(0, dotOffset));
                        } else {
                            key = new StringBuilder();
                        }
                    }
                }  // do nothing

                token = parser.nextToken();
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String properties2Yaml(String content) {
        JavaPropsFactory factory = new JavaPropsFactory();
        try (JsonParser parser = factory.createParser(content)) {
            Writer writer = new StringWriter();

            YAMLFactory yamlFactory = new YAMLFactory();
            try (YAMLGenerator generator = yamlFactory.createGenerator(writer)) {
                JsonToken token = parser.nextToken();
                while (token != null) {
                    if (JsonToken.START_OBJECT.equals(token)) {
                        generator.writeStartObject();
                    } else if (JsonToken.FIELD_NAME.equals(token)) {
                        generator.writeFieldName(parser.getCurrentName());
                    } else if (JsonToken.VALUE_STRING.equals(token)) {
                        generator.writeString(parser.getText());
                    } else if (JsonToken.END_OBJECT.equals(token)) {
                        generator.writeEndObject();
                    }
                    token = parser.nextToken();
                }
                generator.flush();

                return writer.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
