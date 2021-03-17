package com.changhong.sei.cicd.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.cicd.dto.TaskHandleRequest;
import com.changhong.sei.cicd.dto.TaskSubmitRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        TaskSubmitRequest submitRequest = new TaskSubmitRequest();
        submitRequest.setRequisitionId("97668A83-406B-11EB-B9A4-0242C0A84603");
        ResultData<Void> resultData = service.submit(submitRequest);
        System.out.println(resultData);
    }

    @Test
    public void handleTask() {
        String json = "{\"requisitionId\":\"89701D50-3DAB-11EB-84E1-0242C0A84603\",\"message\":\"审核通过\",\"operationType\":\"PASSED\",\"taskId\":\"89A0A332-3DAB-11EB-84E1-0242C0A84603\"}";
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