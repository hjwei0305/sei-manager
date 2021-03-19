package com.changhong.sei.ge.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.ge.dto.ProjectGroupDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    void getProjectGroupTree() {
    }

    @Test
    void findByNameLike() {
    }

    @Test
    void getGitlabGroupTree() {
        List<ProjectGroupDto> nodes = service.getGitlabGroupTree();
        System.out.println(nodes);
    }
}