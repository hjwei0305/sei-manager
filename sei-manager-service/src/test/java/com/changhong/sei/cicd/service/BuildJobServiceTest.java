package com.changhong.sei.cicd.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.cicd.entity.BuildJob;
import com.changhong.sei.cicd.entity.Tag;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.cicd.dto.BuildDetailDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-07 15:46
 */
public class BuildJobServiceTest extends BaseUnitTest {

    @Autowired
    private BuildJobService service;

    @Test
    public void build() {
        BuildJob buildJob = service.findOne("3B7EDA7A-3DEB-11EB-9C08-0242C0A84603");
        service.build(buildJob, "admin");

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBuildDetail() {
        String id = "1132B7E9-3EA3-11EB-8908-0242C0A84603";
        ResultData<BuildDetailDto> resultData = service.getBuildDetail(id);
        System.out.println(resultData);
    }

    @Test
    public void getLastTagName() {
        String envCode = "dev";
        String moduleId = "C1B195AC-8AD5-11EB-9EC8-0242C0A8431A";
        String resultData = service.getLastTagName(envCode, moduleId);
        System.out.println(resultData);
    }

    @Test
    public void getTags() {
        String envCode = "Test";
        String moduleId = "A5FA51A6-A318-11EB-A2C6-0242C0A8431A";
        List<Tag> tags = service.getTags(envCode, moduleId, "6.0.10");
        System.out.println(tags);
    }
}