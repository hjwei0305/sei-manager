package com.changhong.sei.ge.controller;

import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.ge.api.ProjectGroupApi;
import com.changhong.sei.ge.dto.ProjectGroupDto;
import com.changhong.sei.ge.entity.ProjectGroup;
import com.changhong.sei.ge.service.ProjectGroupService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 项目组API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 22:09
 */
@RestController
@Api(value = "ProjectGroupApi", tags = "项目组API服务")
@RequestMapping(path = "projectGroup", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectGroupController extends BaseTreeController<ProjectGroup, ProjectGroupDto> implements ProjectGroupApi {
    @Autowired
    private ProjectGroupService service;

    @Override
    public BaseTreeService<ProjectGroup> getService() {
        return service;
    }

    /**
     * 获取整个菜单树
     *
     * @return 菜单树形对象集合
     */
    @Override
    public ResultData<List<ProjectGroupDto>> getGroupTree() {
        List<ProjectGroup> menus = service.getProjectGroupTree();
        List<ProjectGroupDto> dtos = menus.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name 名称
     * @return 返回的列表
     */
    @Override
    public ResultData<List<ProjectGroupDto>> findByNameLike(String name) {
        List<ProjectGroup> menus = service.findByNameLike(name);
        List<ProjectGroupDto> dtos = menus.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }
}
