package com.changhong.sei.common;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-11 15:40
 */
public class TestPattern {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)(?:\\.)(\\d+)(?:\\.)(\\d+)");
    private static final Pattern PATTERN_ = Pattern.compile("^[1-9]\\d{0,1}\\.(\\d){1,3}\\.(\\d){1,4}$");

    @Test
    public void test() {
        String version = "01.120.0000";
        Matcher matcher = PATTERN_.matcher(version);
        System.out.println(matcher.matches());
        if (matcher.matches()) {
            Matcher m = PATTERN.matcher(version);
            if (m.find()) {
//                System.out.println(m.group(0));
                System.out.println(m.group(1));
                System.out.println(m.group(2));
                System.out.println(m.group(3));
            }
        }
    }
}
