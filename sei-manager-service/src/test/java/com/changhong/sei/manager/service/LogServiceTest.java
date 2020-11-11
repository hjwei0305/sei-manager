package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.log.dto.LogDetailResponse;
import com.changhong.sei.log.dto.LogResponse;
import com.changhong.sei.log.dto.LogSearch;
import com.changhong.sei.log.service.LogService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        search.setIdxName("sei-basic-*");
        search.addQuickSearchProperty(LogResponse.SEARCH_TRACE_ID);
        search.addQuickSearchProperty(LogResponse.SEARCH_MESSAGE);
        search.addQuickSearchProperty(LogResponse.SEARCH_LEVEL);
        search.setQuickSearchValue("sei");
        List<SearchFilter> filters = Lists.newArrayList();
        filters.add(new SearchFilter(LogResponse.SEARCH_MESSAGE, "请求", SearchFilter.Operator.LK));
        search.setFilters(filters);
//        search.addFilter(new SearchFilter(LogResponse.SEARCH_TIMESTAMP, "2020-10-28 18:22:49", SearchFilter.Operator.LE));
//        search.addFilter(new SearchFilter(LogResponse.SEARCH_TIMESTAMP, "2020-10-28 17:22:49", SearchFilter.Operator.GE));
        ResultData<PageResult<LogResponse>> resultData = service.findByPage(search);
        System.out.println(resultData);
    }

    @Test
    public void detail() {
        ResultData<LogDetailResponse> resultData = service.detail("sei-manager-*", "efm2hHUBL-RVZTyt3HOA");
        System.out.println(resultData.getData().getMessage());
    }

    @Test
    public void findByTraceId() {
        ResultData<List<LogResponse>> resultData = service.findByTraceId("sei-basic-*", "7D0EBFA618F311EBBB7B0242C0A8460C");
        System.out.println(resultData);
    }
}