package com.changhong.sei.cicd.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.cicd.dto.TagDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 应用标签(Tag)API
 *
 * @author sei
 * @since 2020-11-26 14:45:24
 */
@Valid
@FeignClient(name = "sei-manager", path = "tag")
public interface TagApi {

    /**
     * 获取项目分支
     *
     * @param gitId git项目id
     * @return 项目分支列表
     */
    @GetMapping(path = "getBranches")
    @ApiOperation(value = "获取项目分支", notes = "获取项目分支")
    ResultData<List<Map<String, String>>> getBranches(@RequestParam("gitId") String gitId);

    /**
     * 获取最新的标签
     *
     * @param moduleId 模块id
     * @return 创建结果
     */
    @GetMapping(path = "getLastTag")
    @ApiOperation(value = "获取最新的标签", notes = "获取最新的标签")
    ResultData<TagDto> getLastTag(@RequestParam("moduleId") String moduleId);

    /**
     * 通过Id获取一个Tag
     *
     * @param id 业务实体Id
     * @return 业务实体
     */
    @GetMapping(path = "getTag")
    @ApiOperation(value = "获取一个标签", notes = "获取一个标签")
    ResultData<TagDto> getTag(@RequestParam("id") String id);

    /**
     * 获取项目标签
     *
     * @param search search
     * @return 创建结果
     */
    @PostMapping(path = "getTags", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取项目标签", notes = "获取项目标签")
    ResultData<PageResult<TagDto>> getTags(@RequestBody Search search);

    /**
     * 创建标签
     *
     * @param request 创建标签请求
     * @return 创建结果
     */
    @PostMapping(path = "create")
    @ApiOperation(value = "创建标签", notes = "创建标签")
    ResultData<Void> create(@RequestBody TagDto request);

    /**
     * 删除标签
     *
     * @param id 业务实体Id
     * @return 操作结果
     */
    @DeleteMapping(path = "delete/{id}")
    @ApiOperation(value = "删除标签", notes = "删除标签")
    ResultData<Void> delete(@PathVariable("id") String id);

    /**
     * 同步gitlab项目标签
     *
     * @param moduleId 模块id
     * @return 同步结果
     */
    @PostMapping(path = "syncTag")
    @ApiOperation(value = "同步gitlab项目标签", notes = "同步gitlab项目标签")
    ResultData<Void> syncTag(@RequestParam("moduleId") String moduleId);
}