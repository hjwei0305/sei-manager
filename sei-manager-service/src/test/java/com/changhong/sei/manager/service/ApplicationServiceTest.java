package com.changhong.sei.manager.service;

import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.ge.entity.Application;
import com.changhong.sei.ge.service.ApplicationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void findId() {
        Application result = service.findOne("AC708D97-1A82-11EB-9804-B2736CF14622");
        System.out.println(result);
    }

}