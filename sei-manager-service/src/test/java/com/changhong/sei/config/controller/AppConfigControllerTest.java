package com.changhong.sei.config.controller;

import com.changhong.sei.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-08 11:37
 */
public class AppConfigControllerTest extends BaseUnitTest {

    @Autowired
    private AppConfigController controller;

    @Test
    public void getAppList() {
        controller.getAppList("");
    }
}