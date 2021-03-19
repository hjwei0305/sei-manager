package com.changhong.sei.ge.api;

import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.ge.dto.ProjectGroupDto;
import com.changhong.sei.manager.dto.MenuDto;
import com.changhong.sei.manager.dto.UserGroupDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 项目组API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:56
 */
@FeignClient(name = "sei-manager", path = "projectGroup")
public interface ProjectGroupApi extends BaseTreeApi<ProjectGroupDto> {
    /**
     * 获取整个项目组树
     *
     * @return 项目组树形对象集合
     */
    @GetMapping(path = "getGroupTree")
    @ApiOperation(notes = "查询所有的项目组树", value = "查询所有的项目组树")
    ResultData<List<ProjectGroupDto>> getGroupTree();

    /**
     * 根据名称模糊查询
     *
     * @param name 名称
     * @return 返回的列表
     */
    @GetMapping(path = "findByNameLike")
    @ApiOperation(value = "根据名称模糊查询", notes = "根据名称模糊查询")
    ResultData<List<ProjectGroupDto>> findByNameLike(@RequestParam("name") String name);

    /**
     * 同步gitlab群组
     */
    @GetMapping(path = "syncGitlabData")
    @ApiOperation(value = "同步gitlab群组", notes = "同步gitlab群组")
    ResultData<Void> syncGitlabData();
}
