package com.changhong.sei.integrated.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.integrated.vo.ProjectTagVo;
import com.changhong.sei.integrated.vo.ProjectType;
import com.changhong.sei.integrated.vo.ProjectVo;
import com.changhong.sei.util.IdGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 15:36
 */
public class GitlabServiceTest extends BaseUnitTest {

    @Autowired
    private GitlabService service;

    @Test
    public void createProject() {
        ProjectVo project = new ProjectVo();
        project.setType(ProjectType.WEB);
        project.setProjectId(IdGenerator.uuid2());
        project.setCode("sei-test-mac");
        project.setName("测试createProject");
        project.setNameSpace("com.changhong.sei.demo");
        project.setGroupId("384");
        project.setGroupName("TEST");
        ResultData<ProjectVo> resultData = service.createProject(project);
        System.out.println(resultData);
    }

    @Test
    public void createProjectTag() {
        ProjectTagVo project = new ProjectTagVo();
        project.setProjectId("1423");
        project.setTagName("1.0.2-beta");
        project.setMessage("测试createProject TAG");
        ResultData<ProjectTagVo> resultData = service.createProjectTag(project);
        System.out.println(resultData);
    }
}