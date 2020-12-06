package com.changhong.sei.integrated.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-03 22:43
 */
public class JenkinsServiceTest extends BaseUnitTest {

    @Autowired
    private JenkinsService service;

//    @Test
//    public void getJob() {
//        JobWithDetails details = service.getJob("dsei_auth-6.0");
//
//        System.out.println(details);
//    }

    @Test
    public void buildJob() {
        ResultData<Integer> resultData = service.buildJob("dsei_auth-6.0");

        System.out.println(resultData);
    }

    @Test
    public void getBuildInfo() {
//        ResultData<JobBuildInfo> resultData = service.getBuildInfo("dsei_auth-6.0", 3);

//        System.out.println(resultData);
    }
}