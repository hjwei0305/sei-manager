package com.changhong.sei.ge.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.ge.dto.ProjectGroupDto;
import com.changhong.sei.ge.entity.ProjectGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-19 08:49
 */
class ProjectGroupServiceTest extends BaseUnitTest {
    @Autowired
    private ProjectGroupService service;

    @Test
    @Rollback
    void save() {
        String json = "{\"parentId\":\"001C7E07-885F-11EB-A497-A6A41BB6A60B\",\"parentName\":\"TEST-OPS\",\"parentNodeLevel\":0,\"children\":[],\"managerAccount\":\"chao2.ma\",\"name\":\"TEST-OPS-DEV\",\"remark\":\"测试OPS建组\",\"managerAccountName\":\"马超\"}";
        ProjectGroup group = JsonUtils.fromJson(json, ProjectGroup.class);
        service.save(group);
    }
    @Test
    void getProjectGroupTree() {
    }

    @Test
    void findByNameLike() {
    }

    @Test
    void syncGitlabData() {
         service.syncGitlabData();
    }

    @Test
    void getGitlabGroupTree() {
        List<ProjectGroup> nodes = service.getGitlabGroupTree();
        System.out.println(nodes);
    }
}