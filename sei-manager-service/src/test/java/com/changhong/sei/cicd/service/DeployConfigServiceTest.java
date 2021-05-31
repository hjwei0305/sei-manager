package com.changhong.sei.cicd.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-05-31 11:09
 */
class DeployConfigServiceTest extends BaseUnitTest {
    @Autowired
    private DeployConfigService service;

    @Test
    void getDeployConfig() {
    }

    @Test
    void initializeDeploy() {
        String id = "FDE02B90-B2DF-11EB-AF1C-0242C0A8431A";
        ResultData<Void> resultData = service.initializeDeploy(id);
        System.out.println(resultData);
    }
}