package com.changhong.sei.cicd.service;

import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-18 11:28
 */
class TagServiceTest {

    @Autowired
    private TagService service;

    @Test
    void getTags() {
        String json = "";
        Search search = JsonUtils.fromJson(json, Search.class);
        service.getTags(search);
    }
}