package com.changhong.sei.integrated.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.ge.dto.ModuleType;
import com.changhong.sei.integrated.vo.ProjectVo;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.ProjectUser;
import org.gitlab4j.api.models.Release;
import org.gitlab4j.api.models.Tag;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        project.setType(ModuleType.PRODUCT_JAVA.name());
//        project.setProjectId(IdGenerator.uuid2());
        project.setCode("sei-test-mac1");
        project.setName("测试createProject");
        project.setNameSpace("com.changhong.sei.demo");
        project.setGroupId("384");
        ResultData<ProjectVo> resultData = service.createProject(project);
        System.out.println(resultData);
    }

    @Test
    public void deleteProject() {
        service.deleteProject("1436");
    }

    @Test
    public void getProject() {
        ResultData<ProjectVo> resultData = service.getProject("test/ops-java");
        System.out.println(resultData);
    }

    @Test
    public void createProjectTag() {
        String gitId = "1423";
        String tagName = "1.0.4";
        String message = "测试createProject TAG";
        String branch = "master";
        ResultData<Tag> resultData = service.createProjectTag(gitId, tagName, branch, message);
        System.out.println(resultData);
    }

    @Test
    public void getProjectTags() {
        String gitId = "1455";
        ResultData<List<Tag>> resultData = service.getProjectTags(gitId);
        System.out.println(resultData);
    }

    @Test
    public void createProjectRelease() {
        String gitId = "1423";
        String tagName = "1.0.4";
        String message = "测试createProject TAG";
        ResultData<Release> resultData = service.createProjectRelease(gitId, message, tagName + "-beta", tagName, message);
        System.out.println(resultData);
    }

    @Test
    public void getProjectUser() {
        String id = "4";
        ResultData<List<ProjectUser>> resultData = service.getProjectUser(id);
        System.out.println(resultData);
    }

    @Test
    public void getGroup() {
        String id = "388";
        ResultData<Group> resultData = service.getGroup(id);
        System.out.println(resultData);
    }

    @Test
    public void createGroup() {
        Group group = new Group();
        group.setName("OPS-TEST");
        group.setPath("ops-test");
        group.setDescription("ops测试");
        ResultData<String> resultData = service.createGroup(group);
        System.out.println(resultData);
    }

    @Test
    public void deleteGroup() {
        ResultData<Void> resultData = service.deleteGroup("389");
        System.out.println(resultData);
    }

    @Test
    public void forkProject() {
        ResultData<ProjectVo> resultData = service.forkProject("1593", "392");
        System.out.println(resultData);
    }
}