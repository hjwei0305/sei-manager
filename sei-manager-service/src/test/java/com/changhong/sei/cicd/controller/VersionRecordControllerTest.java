package com.changhong.sei.cicd.controller;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.cicd.dto.VersionRecordDto;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-29 23:07
 */
class VersionRecordControllerTest extends BaseUnitTest {

    @Autowired
    private VersionRecordController controller;

    @Test
    void createRequisition() {
        String json = "{\"appId\":\"E55DDDA7-82CE-11EB-B615-0242C0A8431A\",\"gitId\":\"1509\",\"moduleId\":\"1235F7FE-82CF-11EB-B615-0242C0A8431A\",\"moduleCode\":\"soms-v7\",\"refTagId\":\"4003C9D5-8A32-11EB-9E7E-0242C0A8431A\",\"name\":\"首次部署到演示环境\",\"appName\":\"财务共享运营API\",\"moduleName\":\"财务共享运营API\",\"refTag\":\"7.0.1\",\"remark\":\"## 验证部署\\n1. 部署演示环境验证\\n2. 演示环境api\",\"version\":\"7.0.1\"}";
        VersionRecordDto dto = JsonUtils.fromJson(json, VersionRecordDto.class);
        controller.createRequisition(dto);
    }

    @Test
    void modifyRequisition() {
    }

    @Test
    void deleteRequisition() {
    }
}