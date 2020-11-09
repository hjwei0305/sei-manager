package com.changhong.sei.datamodel.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.datamodel.api.DataSourceApi;
import com.changhong.sei.datamodel.dto.DBType;
import com.changhong.sei.datamodel.dto.DBTypeDto;
import com.changhong.sei.datamodel.dto.DataSourceDto;
import com.changhong.sei.datamodel.dto.DataSourceRequest;
import com.changhong.sei.datamodel.entity.DataSource;
import com.changhong.sei.datamodel.service.DataSourceService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据源(DataSource)控制类
 *
 * @author sei
 * @since 2020-07-28 23:24:27
 */
@RestController
@Api(value = "DataSourceApi", tags = "数据源服务")
@RequestMapping(path = "dataSource", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataSourceController extends BaseEntityController<DataSource, DataSourceDto>
        implements DataSourceApi {
    /**
     * 数据源服务对象
     */
    @Autowired
    private DataSourceService service;

    @Override
    public BaseEntityService<DataSource> getService() {
        return service;
    }

    /**
     * 保存业务实体
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<DataSourceDto> saveRequest(DataSourceRequest dto) {
        if (Objects.isNull(dto)) {
            return ResultData.fail(ContextUtil.getMessage("core_service_00002"));
        }

        // 数据转换 to Entity
        DataSource entity = convertToEntity(dto);
        OperateResultWithData<DataSource> result;
        try {
            if (StringUtils.isNotEmpty(dto.getPassword())) {
                byte[] password;
//                password = Base64.decodeBase64(dto.getPassword());
                password = dto.getPassword().getBytes(StandardCharsets.UTF_8);
                // 更新密码
                entity.setPassword(password);
            } else {
                // todo 有密码改为无密码如何考虑?
                if (StringUtils.isNotEmpty(entity.getId())) {
                    // 页面不展示密码,故在编辑时需要回填原密码
                    DataSource originDs = service.findOne(entity.getId());
                    if (Objects.nonNull(originDs)) {
                        entity.setPassword(originDs.getPassword());
                    }
                }
            }

            result = service.save(entity);
        } catch (Exception e) {
            // 捕获异常，并返回
            LogUtil.error("保存业务实体异常！", e);
            // 保存业务实体异常！{0}
            return ResultData.fail(ContextUtil.getMessage("core_service_00003", e.getMessage()));
        }
        if (result.notSuccessful()) {
            return ResultData.fail(result.getMessage());
        }
        // 数据转换 to DTO
        DataSourceDto resultData = convertToDto(result.getData());
        return ResultData.success(result.getMessage(), resultData);
    }

    /**
     * 删除数据源实体
     *
     * @param id 数据源实体Id
     * @return 操作结果
     */
    @Override
    public ResultData<String> delete(String id) {
        try {
            OperateResult result = getService().delete(id);
            return ResultDataUtil.convertFromOperateResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("删除数据源实体异常！", e);
            // 删除业务实体异常！{0}
            return ResultData.fail(ContextUtil.getMessage("core_service_00004", e.getMessage()));
        }
    }

    /**
     * 通过Id获取一个数据源实体
     *
     * @param id 数据源实体Id
     * @return 业务实体
     */
    @Override
    public ResultData<DataSourceDto> findOne(String id) {
        DataSource entity;
        try {
            entity = getService().findOne(id);
        } catch (Exception e) {
            LogUtil.error("获取数据源实体异常！", e);
            // 获取业务实体异常！{0}
            return ResultData.fail(ContextUtil.getMessage("core_service_00005", e.getMessage()));
        }
        // 转换数据 to DTO
        DataSourceDto dto = convertToDto(entity);
        return ResultData.success(dto);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<DataSourceDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }

    /**
     * 获取所有支持的数据库类型
     */
    @Override
    public ResultData<List<DBTypeDto>> getDBTypes() {
        List<DBTypeDto> dtos = new ArrayList<>();
        for (DBType type : DBType.values()) {
            dtos.add(new DBTypeDto(type));
        }
        return ResultData.success(dtos);
    }
}