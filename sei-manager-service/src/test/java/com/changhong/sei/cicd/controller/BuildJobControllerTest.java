package com.changhong.sei.cicd.controller;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.cicd.dto.BuildJobDto;
import com.changhong.sei.cicd.dto.BuildJobRequisitionDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-03 16:38
 */
public class BuildJobControllerTest extends BaseUnitTest {

    @Autowired
    private BuildJobController controller;

    @Test
    public void createRequisition() {
        String s = "{\"envCode\":\"Dev\",\"appId\":\"D3217D12-33B0-11EB-B17B-0242C0A84603\",\"gitId\":\"1427\",\"moduleCode\":\"sei-auth1\",\"name\":\"test\",\"envName\":\"开发环境\",\"appName\":\"认证中心\",\"moduleName\":\"认证服务java\",\"tagName\":\"6.1.1-beta\",\"expCompleteTime\":\"2020-12-03 16:37:00\",\"remark\":\"test\"}";
        BuildJobDto dto = JsonUtils.fromJson(s, BuildJobDto.class);
        controller.createRequisition(dto);
    }

    @Test
    public void modifyRequisition() {
        String s = "{\"id\":\"C3D875EB-8D13-11EB-8FFE-0242C0A84620\",\"envCode\":\"Dev\",\"envName\":\"开发环境\",\"appId\":\"EAE9C887-4597-11EB-9F4F-0242C0A84603\",\"appName\":\"资金管理\",\"gitId\":\"1455\",\"moduleId\":\"4BD8B1FA-4598-11EB-9F4F-0242C0A84603\",\"moduleCode\":\"fms-baf\",\"moduleName\":\"资金管理基础配置\",\"refTagId\":\"EBE05DF6-8708-11EB-8CD1-0242C0A84603\",\"refTag\":\"5.0.6\",\"frozen\":true,\"name\":\"test\",\"remark\":\"aaa111\",\"script\":null,\"expCompleteTime\":\"2021-03-25 10:22:00\",\"buildStatus\":null,\"buildTime\":null,\"buildAccount\":null,\"allowBuild\":true}";
        BuildJobDto dto = JsonUtils.fromJson(s, BuildJobDto.class);
        controller.modifyRequisition(dto);
    }

    @Test
    public void findRequisitionByPage() {
        Search search = Search.createSearch();

        ResultData<PageResult<BuildJobRequisitionDto>> resultData = controller.findRequisitionByPage(search);
        System.out.println(resultData);
    }
}