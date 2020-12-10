package com.changhong.sei.common;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.manager.commom.EmailManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-10 11:12
 */
public class ThymeLeafHelperTest extends BaseUnitTest {

    @Autowired
    private EmailManager emailManager;

    @Test
    public void getTemplateEngine() throws InterruptedException {
        Context context = new Context();
        context.setVariable("userName", "马超");
        context.setVariable("account", "mac");
        context.setVariable("password", "123");
        String content = ThymeLeafHelper.getTemplateEngine().process("CreateUser.html", context);

        emailManager.sendMail("测试", content, "chao2.ma@changhong.com");

        Thread.sleep(10000);
    }
}