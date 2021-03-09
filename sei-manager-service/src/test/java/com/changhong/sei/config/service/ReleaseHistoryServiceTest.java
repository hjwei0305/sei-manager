package com.changhong.sei.config.service;

import com.changhong.sei.core.dto.ResultData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-09 15:03
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReleaseHistoryServiceTest {
    @Autowired
    private ReleaseHistoryService service;

    @Test
    void getVersions() {
    }

    @Test
    void getLastReleaseHistory() {
    }

    @Test
    void crossEnvCompare() {
        String appCode = "sei-auth";
        String currentEnv = "Dev";
        String targetEnv = "Test";
        ResultData<Map<String, String>> data = service.crossEnvCompare(appCode, currentEnv, targetEnv);
        System.out.println(data);
    }

    @Test
    void crossEnvCompareResult() {
    }
}