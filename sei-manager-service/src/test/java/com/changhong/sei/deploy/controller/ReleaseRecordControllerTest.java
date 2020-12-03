package com.changhong.sei.deploy.controller;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.deploy.dto.ReleaseRecordDto;
import com.changhong.sei.deploy.dto.ReleaseRecordRequisitionDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-03 16:38
 */
public class ReleaseRecordControllerTest extends BaseUnitTest {

    @Autowired
    private ReleaseRecordController controller;

    @Test
    public void createRequisition() {
        String s = "{\"envCode\":\"Dev\",\"appId\":\"D3217D12-33B0-11EB-B17B-0242C0A84603\",\"gitId\":\"1427\",\"moduleCode\":\"sei-auth1\",\"name\":\"test\",\"envName\":\"开发环境\",\"appName\":\"认证中心\",\"moduleName\":\"认证服务java\",\"tagName\":\"6.1.1-beta\",\"expCompleteTime\":\"2020-12-03 16:37:00\",\"remark\":\"test\"}";
        ReleaseRecordDto dto = JsonUtils.fromJson(s, ReleaseRecordDto.class);
        controller.createRequisition(dto);
    }

    @Test
    public void findRequisitionByPage() {
        Search search = Search.createSearch();

        ResultData<PageResult<ReleaseRecordRequisitionDto>> resultData = controller.findRequisitionByPage(search);
        System.out.println(resultData);
    }
}