package com.changhong.sei.deploy.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.TagDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * 获取最新的标签
     *
     * @param moduleCode 模块代码
     * @return 创建结果
     */
    @GetMapping(path = "getLastTag")
    @ApiOperation(value = "获取最新的标签", notes = "获取最新的标签")
    ResultData<TagDto> getLastTag(@RequestParam("moduleCode") String moduleCode);

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
     * @param moduleCode 模块代码
     * @return 创建结果
     */
    @GetMapping(path = "getTags")
    @ApiOperation(value = "获取项目标签", notes = "获取项目标签")
    ResultData<List<TagDto>> getTags(@RequestParam("moduleCode") String moduleCode);

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

}