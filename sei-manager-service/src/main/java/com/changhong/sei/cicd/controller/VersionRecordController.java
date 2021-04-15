package com.changhong.sei.cicd.controller;

import com.changhong.sei.common.AuthorityUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.cicd.api.VersionRecordApi;
import com.changhong.sei.cicd.dto.VersionRecordDto;
import com.changhong.sei.cicd.dto.VersionRecordRequisitionDto;
import com.changhong.sei.cicd.entity.VersionRecord;
import com.changhong.sei.cicd.entity.VersionRecordRequisition;
import com.changhong.sei.cicd.service.VersionRecordService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 版本发布记录(VersionRecord)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "ReleaseVersionApi", tags = "版本发布记录服务")
@RequestMapping(path = "releaseVersion", produces = MediaType.APPLICATION_JSON_VALUE)
public class VersionRecordController extends BaseEntityController<VersionRecord, VersionRecordDto> implements VersionRecordApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private VersionRecordService service;

    @Override
    public BaseEntityService<VersionRecord> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<VersionRecordDto>> findByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        // 添加数据权限过滤
        Set<String> ids = AuthorityUtil.getAuthorizedModuleIds();
        if (Objects.nonNull(ids)) {
            search.addFilter(new SearchFilter(VersionRecord.FIELD_MODULE_ID, ids, SearchFilter.Operator.IN));
        }
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 分页查询发布申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<VersionRecordRequisitionDto>> findRequisitionByPage(Search search) {
        PageResult<VersionRecordRequisition> pageResult = service.findRequisitionByPage(search);
        PageResult<VersionRecordRequisitionDto> result = new PageResult<>(pageResult);
        List<VersionRecordRequisition> requisitions = pageResult.getRows();
        if (CollectionUtils.isNotEmpty(requisitions)) {
            List<VersionRecordRequisitionDto> dtos = requisitions.stream().map(e -> dtoModelMapper.map(e, VersionRecordRequisitionDto.class)).collect(Collectors.toList());
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
    public ResultData<VersionRecordRequisitionDto> createRequisition(@Valid VersionRecordDto dto) {
        return service.createRequisition(convertToEntity(dto));
    }

    /**
     * 修改编辑应用版本申请单
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<VersionRecordRequisitionDto> modifyRequisition(@Valid VersionRecordDto dto) {
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