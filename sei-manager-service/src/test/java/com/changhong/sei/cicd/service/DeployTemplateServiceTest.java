package com.changhong.sei.cicd.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        ResultData<String> resultData = service.generateJobXml("854D0AEA-35DF-11EB-8AB2-0242C0A84603");
        System.out.println(resultData);
        System.out.println(resultData.getData());
    }

    @Test
    public void syncJenkinsJob() {
        ResultData<Void> resultData = service.syncJenkinsJob("F877CF48-3B91-11EB-8E8B-0242C0A84603");
        System.out.println(resultData);
        System.out.println(resultData.getData());
    }
}