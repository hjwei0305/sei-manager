package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 00:15
 */
public class DeployTemplateServiceTest extends BaseUnitTest {

    @Autowired
    private DeployTemplateService service;

    @Test
    public void generateXml() {
        ResultData<String> resultData = service.generateJobXml("F662AB75-0EAB-11EB-8124-0242C0A8460F");
        System.out.println(resultData);
        System.out.println(resultData.getData());
    }
}