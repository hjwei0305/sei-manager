package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.api.TagApi;
import com.changhong.sei.deploy.dto.TagDto;
import com.changhong.sei.deploy.service.TagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResultData<List<String>> getBranches(String gitId) {
        return service.getProjectBranches(gitId);
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
    public ResultData<List<TagDto>> getTags(String gitId) {
        return service.getTags(gitId);
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