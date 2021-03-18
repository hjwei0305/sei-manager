package com.changhong.sei.ge.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.api.ProjectUserApi;
import com.changhong.sei.ge.dto.ProjectUserDto;
import com.changhong.sei.ge.entity.ProjectUser;
import com.changhong.sei.ge.service.ProjectUserService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目用户(ProjectUser)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "ProjectUserApi", tags = "项目用户服务")
@RequestMapping(path = "projectUser", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectUserController extends BaseEntityController<ProjectUser, ProjectUserDto> implements ProjectUserApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private ProjectUserService service;

    @Override
    public BaseEntityService<ProjectUser> getService() {
        return service;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<ProjectUserDto>> findAll() {
        List<ProjectUserDto> list;
        List<ProjectUser> users = service.findAll();
        if (CollectionUtils.isNotEmpty(users)) {
            list = users.stream().map(u -> dtoModelMapper.map(u, ProjectUserDto.class)).collect(Collectors.toList());
        } else {
            list = new ArrayList<>();
        }
        return ResultData.success(list);
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<ProjectUserDto>> findAllUnfrozen() {
        List<ProjectUserDto> list;
        List<ProjectUser> users = service.findAllUnfrozen();
        if (CollectionUtils.isNotEmpty(users)) {
            list = users.stream().map(u -> dtoModelMapper.map(u, ProjectUserDto.class)).collect(Collectors.toList());
        } else {
            list = new ArrayList<>();
        }
        return ResultData.success(list);    }
}