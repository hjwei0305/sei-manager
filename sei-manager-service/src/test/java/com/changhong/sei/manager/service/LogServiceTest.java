package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.manager.dto.LogDetailResponse;
import com.changhong.sei.manager.dto.LogResponse;
import com.changhong.sei.manager.dto.LogSearch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 18:35
 */
public class LogServiceTest extends BaseUnitTest {
    @Autowired
    private LogService service;

    @Test
    public void findByPage() {
        LogSearch search = new LogSearch();
        search.setIdxName("sei-basic-");
        service.findByPage(search);
    }

    @Test
    public void detail() {
        ResultData<LogDetailResponse> resultData = service.detail("sei-basic-*", "Rl6jZ3UBL-RVZTytXIH3");
        System.out.println(resultData);
    }

    @Test
    public void findByTraceId() {
        ResultData<List<LogResponse>> resultData = service.findByTraceId("sei-basic-*", "ED7CDDC017F211EBBB7B0242C0A8460C");
    }
}