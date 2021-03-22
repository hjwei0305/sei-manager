package com.changhong.sei.ge.service;

import com.changhong.sei.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-22 17:57
 */
class AppModuleServiceTest extends BaseUnitTest {

    @Autowired
    private AppModuleService service;

    @Test
    void updateFrozen() {

        service.updateFrozen("91B7F003-8AD5-11EB-9EC8-0242C0A8431A");
    }
}