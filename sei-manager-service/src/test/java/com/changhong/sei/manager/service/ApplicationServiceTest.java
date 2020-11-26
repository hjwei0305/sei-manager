package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.deploy.service.ApplicationService;
import com.changhong.sei.deploy.dto.ApplicationDto;
import com.changhong.sei.deploy.dto.ApplicationResponse;
import com.changhong.sei.deploy.entity.Application;
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
    public void save() {
        Application app = new Application();
//        app.setCode("test");
        app.setName("test测试");
        app.setVersion("test-1.0.1");
        app.setRemark("http://test:8080/test");
        OperateResultWithData<Application> result = service.save(app);
        System.out.println(result);
    }

    @Test
    public void getServiceCodes() {
         service.getServiceCodes();
    }

    @Test
    public void findId() {
        Application result = service.findOne("AC708D97-1A82-11EB-9804-B2736CF14622");
        System.out.println(result);
    }

    @Test
    public void getServices() {
        ResultData<List<ApplicationDto>> result = service.getServices();
        System.out.println(result);
    }

    @Test
    public void testGetServices() {
        ResultData<List<ApplicationResponse>> resultData = service.getServiceInstance("edm-service");
        System.out.println(resultData);
    }
}