package com.changhong.sei.cicd.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.cicd.api.ReleaseVersionApi;
import com.changhong.sei.cicd.dto.ReleaseVersionDto;
import com.changhong.sei.cicd.dto.ReleaseVersionRequisitionDto;
import com.changhong.sei.cicd.entity.ReleaseVersion;
import com.changhong.sei.cicd.entity.ReleaseVersionRequisition;
import com.changhong.sei.cicd.service.ReleaseVersionService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 版本发布记录(ReleaseVersion)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "ReleaseVersionApi", tags = "版本发布记录服务")
@RequestMapping(path = "releaseVersion", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReleaseVersionController extends BaseEntityController<ReleaseVersion, ReleaseVersionDto> implements ReleaseVersionApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private ReleaseVersionService service;

    @Override
    public BaseEntityService<ReleaseVersion> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ReleaseVersionDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 分页查询发布申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<ReleaseVersionRequisitionDto>> findRequisitionByPage(Search search) {
        PageResult<ReleaseVersionRequisition> pageResult = service.findRequisitionByPage(search);
        PageResult<ReleaseVersionRequisitionDto> result = new PageResult<>(pageResult);
        List<ReleaseVersionRequisition> requisitions = pageResult.getRows();
        if (CollectionUtils.isNotEmpty(requisitions)) {
            List<ReleaseVersionRequisitionDto> dtos = requisitions.stream().map(e -> dtoModelMapper.map(e, ReleaseVersionRequisitionDto.class)).collect(Collectors.toList());
            result.setRows(dtos);
        }
        return ResultData.success(result);
    }

    /**
     * 创建应用版本申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<ReleaseVersionRequisitionDto> createRequisition(@Valid ReleaseVersionDto dto) {
        return service.createRequisition(convertToEntity(dto));
    }

    /**
     * 修改编辑应用版本申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<ReleaseVersionRequisitionDto> modifyRequisition(@Valid ReleaseVersionDto dto) {
        return service.modifyRequisition(convertToEntity(dto));
    }

    /**
     * 删除应用版本申请单
     *
     * @param id 申请单id
     * @return 操作结果
     */
    @Override
    public ResultData<Void> deleteRequisition(String id) {
        return service.deleteRequisition(id);
    }
}