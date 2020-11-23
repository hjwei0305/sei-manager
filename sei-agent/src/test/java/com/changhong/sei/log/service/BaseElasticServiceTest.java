package com.changhong.sei.log.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.log.dto.LogSearch;
import com.changhong.sei.log.service.BaseElasticService;
import com.changhong.sei.util.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 13:48
 */
public class BaseElasticServiceTest extends BaseUnitTest {

    @Autowired
    private BaseElasticService service;

    @Test
    public void deleteIndex() {
    }

    @Test
    public void indexExist() {
    }

    @Test
    public void isExistsIndex() {
    }

    @Test
    public void deleteOne() {
    }

    @Test
    public void deleteBatch() {
    }

    @Test
    public void deleteByQuery() {
    }

    @Test
    public void search() {
    }

    @Test
    public void testSearch() {

    }

    @Test
    public void findByPage() {
    }

    @Test
    public void testSearch1() {
        LogSearch search = new LogSearch();

        search.setIdxName("sei-basic-*");

        ResultData<PageResult<HashMap<String, Object>>> resultData = service.findByPage(search);
        System.out.println(resultData);
    }

    public static void main(String[] args) {
        String s = "2020-10-27T09:22:49.893+0800";
        String a = "2020-11-12T17:01:38.841Z";

        System.out.println(DateUtils.parseTime(s, DateUtils.ISO_DATE_TIME_FORMAT));
        System.out.println(DateUtils.formatDate(DateUtils.parseTime(s, DateUtils.ISO_DATE_TIME_FORMAT), "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(DateUtils.parseTime(a, DateUtils.ISO_DATE_TIME_FORMAT));
        System.out.println(DateUtils.formatDate(DateUtils.parseTime(a, DateUtils.ISO_DATE_TIME_FORMAT), "yyyy-MM-dd HH:mm:ss.SSS"));
        Object obj = null;
        String s1 = String.valueOf(obj);
        System.out.println(s1);
    }
}