package com.changhong.sei.log.service;

import com.changhong.sei.core.test.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-09 12:54
 */
public class ScheduleServiceTest extends BaseUnitTest {

    @Autowired
    private ScheduleService service;
    @Test
    public void cron() {
        service.cron();
    }
}