package com.changhong.sei.deploy.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.deploy.dto.TaskHandleRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 11:31
 */
public class RequisitionOrderServiceTest extends BaseUnitTest {

    @Autowired
    private RequisitionOrderService service;

    @Test
    public void getByRelationId() {
    }

    @Test
    public void createRequisition() {
    }

    @Test
    public void modifyRequisition() {
    }

    @Test
    public void deleteRequisition() {
    }

    @Test
    public void submit() {
    }

    @Test
    public void handleTask() {
        String json = "{\"requisitionId\":\"172FAB4F-33B1-11EB-B17B-0242C0A84603\",\"message\":\"审核通过\",\"operationType\":\"PASSED\",\"taskId\":\"1751D951-33B1-11EB-B17B-0242C0A84603\"}";
        TaskHandleRequest request = JsonUtils.fromJson(json, TaskHandleRequest.class);
        ResultData resultData = service.handleTask(request);
        System.out.println(resultData);
    }

    @Test
    public void getTodoTaskNum() {
    }

    @Test
    public void getTodoTasks() {
    }
}