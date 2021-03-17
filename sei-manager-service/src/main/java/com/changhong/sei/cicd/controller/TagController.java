package com.changhong.sei.cicd.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.cicd.api.TagApi;
import com.changhong.sei.cicd.dto.TagDto;
import com.changhong.sei.cicd.service.TagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用标签(Tag)控制类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@RestController
@Api(value = "TagApi", tags = "应用标签服务")
@RequestMapping(path = "tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController implements TagApi {

    @Autowired
    private TagService service;

    /**
     * 获取项目分支
     *
     * @param gitId git项目id
     * @return 项目分支列表
     */
    @Override
    public ResultData<List<Map<String, String>>> getBranches(String gitId) {
        ResultData<List<String>> resultData = service.getProjectBranches(gitId);
        if (resultData.successful()) {
            List<String> list = resultData.getData();
            Map<String, String> map;
            List<Map<String, String>> mapList = new ArrayList<>();
            for (String name : list) {
                map = new HashMap<>(7);
                map.put("key", name);
                mapList.add(map);
            }
            return ResultData.success(mapList);
        }
        return ResultData.fail(resultData.getMessage());
    }

    /**
     * 获取最新的标签
     *
     * @param moduleId 模块id
     * @return 创建结果
     */
    @Override
    public ResultData<TagDto> getLastTag(String moduleId) {
        return service.getLastTag(moduleId);
    }

    /**
     * 通过Id获取一个Tag
     *
     * @param id 业务实体Id
     * @return 业务实体
     */
    @Override
    public ResultData<TagDto> getTag(String id) {
        return service.getTag(id);
    }

    /**
     * 获取项目标签
     *
     * @param gitId gitId
     * @return 创建结果
     */
    @Override
    public ResultData<PageResult<TagDto>> getTags(String gitId, Search search) {
        return service.getTags(gitId, search);
    }

    /**
     * 创建标签
     *
     * @param request 创建标签请求
     * @return 创建结果
     */
    @Override
    public ResultData<Void> create(TagDto request) {
        return service.createTag(request);
    }

    /**
     * 删除标签
     *
     * @param id 业务实体Id
     * @return 操作结果
     */
    @Override
    public ResultData<Void> delete(String id) {
        return service.deleteTag(id);
    }

    /**
     * 同步gitlab项目标签
     *
     * @param moduleId 模块id
     * @return 同步结果
     */
    @Override
    public ResultData<Void> syncTag(String moduleId) {
        return service.syncTag(moduleId);
    }
}