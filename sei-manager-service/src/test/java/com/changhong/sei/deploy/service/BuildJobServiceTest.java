package com.changhong.sei.deploy.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.BuildDetailDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        service.build("3B7EDA7A-3DEB-11EB-9C08-0242C0A84603", "admin");

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
}