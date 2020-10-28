package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.manager.dto.LogDetailResponse;
import com.changhong.sei.manager.dto.LogResponse;
import com.changhong.sei.manager.dto.LogSearch;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 13:42
 */
@Service
public class LogService {
    @Autowired
    private BaseElasticService elasticService;

    /**
     * 分页查询
     */
    public ResultData<PageResult<LogResponse>> findByPage(@Valid LogSearch search) {
        ResultData<PageResult<HashMap<String, Object>>> pageData = elasticService.findByPage(search);
        if (pageData.successful()) {
            PageResult<HashMap<String, Object>> mapPageResult = pageData.getData();
            PageResult<LogResponse> pageResult = new PageResult<>(mapPageResult);

            List<HashMap<String, Object>> list = mapPageResult.getRows();
            List<LogResponse> logs = new ArrayList<>(list.size());
            for (HashMap<String, Object> map : list) {
                logs.add(buildLog(map));
            }
            pageResult.setRows(logs);
            return ResultData.success(pageResult);
        } else {
            return ResultData.fail(pageData.getMessage());
        }
    }

    /**
     * 获取日志明细
     */
    public ResultData<LogDetailResponse> detail(String serviceName, String id) {
        LogSearch search = new LogSearch();
        search.setIdxName(serviceName);
        search.addFilter(new SearchFilter("_id", id));

        ResultData<List<HashMap<String, Object>>> resultData = elasticService.search(search);
        if (resultData.successful()) {
            List<HashMap<String, Object>> list = resultData.getData();
            if (CollectionUtils.isNotEmpty(list)) {
                LogDetailResponse lodDetail = buildLogDetail(list.get(0));
                return ResultData.success(lodDetail);
            }
        }
        return ResultData.fail(resultData.getMessage());
    }

    /**
     * 根据TraceId获取日志
     */
    public ResultData<List<LogResponse>> findByTraceId(String serviceName, String traceId) {
        LogSearch search = new LogSearch();
        search.setIdxName(serviceName);
        search.addFilter(new SearchFilter(LogResponse.SEARCH_TRACE_ID, traceId));

        ResultData<List<HashMap<String, Object>>> resultData = elasticService.search(search);
        if (resultData.successful()) {
            List<HashMap<String, Object>> list = resultData.getData();
            if (CollectionUtils.isNotEmpty(list)) {
                List<LogResponse> loList = new ArrayList<>(list.size());
                for (HashMap<String, Object> map : list) {
                    loList.add(buildLogDetail(map));
                }
                return ResultData.success(loList);
            }
        }
        return ResultData.fail(resultData.getMessage());
    }

    private LogResponse buildLog(HashMap<String, Object> map) {
        LogResponse log = new LogDetailResponse();

        Object temp = map.get("timestamp");
        try {
            if (Objects.nonNull(temp)) {
                Date date = DateUtils.parseTime(String.valueOf(temp), DateUtils.ISO_DATE_TIME_FORMAT);
                temp = DateUtils.formatTime(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.setTimestamp(String.valueOf(temp));
        temp = map.get("_id");
        log.setId(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("traceId");
        log.setTraceId(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("currentServer");
        log.setCurrentServer(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("fromServer");
        log.setFromServer(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("env");
        log.setEnv(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("serviceName");
        log.setServiceName(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("logger");
        log.setLogger(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("level");
        log.setLevel(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("message");
        log.setMessage(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        return log;
    }

    private LogDetailResponse buildLogDetail(HashMap<String, Object> map) {
        LogDetailResponse log = new LogDetailResponse();
        Object temp = map.get("timestamp");
        try {
            if (Objects.nonNull(temp)) {
                Date date = DateUtils.parseTime(String.valueOf(temp), DateUtils.ISO_DATE_TIME_FORMAT);
                temp = DateUtils.formatTime(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.setTimestamp(String.valueOf(temp));
        temp = map.get("_id");
        log.setId(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("traceId");
        log.setTraceId(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("currentServer");
        log.setCurrentServer(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("fromServer");
        log.setFromServer(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("env");
        log.setEnv(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("serviceName");
        log.setServiceName(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("logger");
        log.setLogger(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("level");
        log.setLevel(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("message");
        log.setMessage(Objects.nonNull(temp) ? String.valueOf(temp) : "");

        temp = map.get("host");
        log.setHost(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("seiVersion");
        log.setVersion(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("userId");
        log.setUserId(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("account");
        log.setAccount(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("userName");
        log.setUserName(Objects.nonNull(temp) ? String.valueOf(temp) : "");
        temp = map.get("stackTrace");
        log.setStackTrace(Objects.nonNull(temp) ? String.valueOf(temp) : "");

        return log;
    }
}
