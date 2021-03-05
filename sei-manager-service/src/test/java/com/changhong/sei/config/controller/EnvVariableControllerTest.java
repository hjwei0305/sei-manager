package com.changhong.sei.config.controller;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.config.dto.EnvVariableValueDto;
import com.changhong.sei.config.entity.EnvVariableValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-05 08:14
 */
public class EnvVariableControllerTest extends BaseUnitTest {

    @Autowired
    private EnvVariableController controller;

    @Test
    public void getAllKey() {
    }

    @Test
    public void getVariableValues() {
    }

    @Test
    public void saveVariableValue() {
        List<EnvVariableValueDto> list = new ArrayList<>();
        EnvVariableValueDto variableValue;
        variableValue = new EnvVariableValueDto();
        variableValue.setId("92BB0027-7D70-11EB-BDAD-BA9FFBC27DBD");
        variableValue.setEnvCode("Dev");
        variableValue.setEnvName("开发环境");
        variableValue.setKey("HOST");
        variableValue.setValue("1");
        variableValue.setRemark("主机地址");
        list.add(variableValue);

        variableValue = new EnvVariableValueDto();
        variableValue.setEnvCode("Demo");
//        variableValue.setEnvCode("Test");
//        variableValue.setEnvName("测试环境");
        variableValue.setEnvName("演示环境");
        variableValue.setKey("HOST");
        variableValue.setValue("3");
        variableValue.setRemark("主机地址");
        list.add(variableValue);

        controller.saveVariableValue(list);
    }

    @Test
    public void deleteVariableValue() {
    }
}