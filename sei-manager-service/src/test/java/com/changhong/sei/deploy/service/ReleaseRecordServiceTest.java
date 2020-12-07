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
        service.build("C670D133-3609-11EB-8A8C-0242C0A84603");

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}