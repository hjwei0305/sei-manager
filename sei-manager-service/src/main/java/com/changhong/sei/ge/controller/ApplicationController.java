package com.changhong.sei.ge.controller;

import com.changhong.sei.cicd.dto.ApplicationRequisitionDto;
import com.changhong.sei.cicd.entity.ApplicationRequisition;
import com.changhong.sei.common.AuthorityUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.api.ApplicationApi;
import com.changhong.sei.ge.dto.ApplicationDto;
import com.changhong.sei.ge.entity.Application;
import com.changhong.sei.ge.service.ApplicationService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用服务(ApplicationService)控制类
 *
 * @author sei
 * @since 2020-10-30 15:20:58
 */
@RestController
@Api(value = "ApplicationApi", tags = "应用服务")
@RequestMapping(path = "application", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController extends BaseEntityController<Application, ApplicationDto> implements ApplicationApi {
    /**
     * 应用服务服务对象
     */
    @Autowired
    private ApplicationService service;

    @Override
    public BaseEntityService<Application> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ApplicationDto>> findByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        // 添加数据权限过滤
        Set<String> ids = AuthorityUtil.getAuthorizedData();
        search.addFilter(new SearchFilter(BaseEntity.ID, ids));
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 分页查询应用申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ApplicationRequisitionDto>> findRequisitionByPage(Search search) {
        PageResult<ApplicationRequisition> pageResult = service.findRequisitionByPage(search);
        PageResult<ApplicationRequisitionDto> result = new PageResult<>(pageResult);
        List<ApplicationRequisition> requisitions = pageResult.getRows();
        if (CollectionUtils.isNotEmpty(requisitions)) {
            List<ApplicationRequisitionDto> dtos = requisitions.stream().map(e -> dtoModelMapper.map(e, ApplicationRequisitionDto.class)).collect(Collectors.toList());
            result.setRows(dtos);
        }
        return ResultData.success(result);
    }

    /**
     * 创建应用申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<ApplicationRequisitionDto> createRequisition(ApplicationDto dto) {
        return service.createRequisition(convertToEntity(dto));
    }

    /**
     * 创建应用申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<ApplicationRequisitionDto> modifyRequisition(ApplicationDto dto) {
        return service.modifyRequisition(convertToEntity(dto));
    }

    /**
     * 创建应用申请单
     *
     * @param id@return 操作结果
     */
    @Override
    public ResultData<Void> deleteRequisition(String id) {
        return service.deleteRequisition(id);
    }
}