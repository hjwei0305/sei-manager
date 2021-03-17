package com.changhong.sei.cicd.controller;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.cicd.dto.FlowInstanceTaskDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-17 20:59
 */
public class FlowDefinitionControllerTest extends BaseUnitTest {
    @Autowired
    private FlowDefinitionController controller;

    @Test
    public void saveType() {
    }

    @Test
    public void findTypeByPage() {
    }

    @Test
    public void findRedefinedTypes() {
    }

    @Test
    public void saveTypeNode() {
    }

    @Test
    public void deleteTypeNode() {
    }

    @Test
    public void getTypeNodeByTypeId() {
    }

    @Test
    public void getTypeVersionByTypeCode() {
    }

    @Test
    public void getTaskByInstanceId() {
    }

    @Test
    public void publish() {
    }

    @Test
    public void getFlowInstanceTask() {
    }

    @Test
    public void saveFlowInstanceTask() {
        String relation = "8BF4EFA2-3E9B-11EB-82CF-0242C0A84603";
        String instanceId = "3";
        String json = "[{\"id\":\"AAF4703C-4066-11EB-8FC5-0242C0A84603\",\"instanceId\":\"AAF4703B-4066-11EB-8FC5-0242C0A84603\",\"code\":\"1\",\"name\":\"技术经理审核\",\"handleAccount\":\"chao2.ma\",\"handleUserName\":\"马超\"},{\"id\":\"AAF4974D-4066-11EB-8FC5-0242C0A84603\",\"instanceId\":\"AAF4703B-4066-11EB-8FC5-0242C0A84603\",\"code\":\"2\",\"name\":\"产品经理审核\",\"handleAccount\":\"pan1.zhang\",\"handleUserName\":\"张盼\"},{\"id\":\"AAF4974E-4066-11EB-8FC5-0242C0A84603\",\"instanceId\":\"AAF4703B-4066-11EB-8FC5-0242C0A84603\",\"code\":\"3\",\"name\":\"管理员审核\",\"handleAccount\":\"admin\",\"handleUserName\":\"管理员\"}]";
        List<FlowInstanceTaskDto> taskList = JsonUtils.fromJson2List(json, FlowInstanceTaskDto.class);
        ResultData<Void> resultData = controller.saveFlowInstanceTask(relation, taskList);
        System.out.println(resultData);
    }
}