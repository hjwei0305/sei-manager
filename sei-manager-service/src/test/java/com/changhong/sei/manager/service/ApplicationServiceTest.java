package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.manager.dto.ApplicationResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-30 14:08
 */
public class ApplicationServiceTest extends BaseUnitTest {

    @Autowired
    private ApplicationService service;

    @Test
    public void getServices() {
        ResultData<List<ApplicationResponse>> result = service.getServices();
        System.out.println(result);
    }

    @Test
    public void testGetServices() {
        ResultData<List<ApplicationResponse>> resultData = service.getServiceInstance("edm-service");
        System.out.println(resultData);
    }
}