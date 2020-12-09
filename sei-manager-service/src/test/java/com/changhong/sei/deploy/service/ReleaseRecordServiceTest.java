package com.changhong.sei.deploy.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.ReleaseRecordDetailDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-07 15:46
 */
public class ReleaseRecordServiceTest extends BaseUnitTest {

    @Autowired
    private ReleaseRecordService service;

    @Test
    public void build() {
        service.build("C670D133-3609-11EB-8A8C-0242C0A84603", "admin");

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBuildDetail() {
        String id = "E01AC2FF-3902-11EB-AC0B-0242C0A84603";
        ResultData<ReleaseRecordDetailDto> resultData = service.getBuildDetail(id);
        System.out.println(resultData);
    }
}