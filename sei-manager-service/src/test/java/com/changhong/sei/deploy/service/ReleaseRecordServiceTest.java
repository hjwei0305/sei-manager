package com.changhong.sei.deploy.service;

import com.changhong.sei.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-07 15:46
 */
public class ReleaseRecordServiceTest extends BaseUnitTest {

    @Autowired
    private ReleaseRecordService service;

    @Test
    public void build() {
        service.build("");
    }
}