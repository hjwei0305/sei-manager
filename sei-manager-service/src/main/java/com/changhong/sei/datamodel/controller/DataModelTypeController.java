package com.changhong.sei.datamodel.controller;

import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.datamodel.api.DataModelTypeApi;
import com.changhong.sei.datamodel.dto.DataModelTypeDto;
import com.changhong.sei.datamodel.entity.DataModelType;
import com.changhong.sei.datamodel.service.DataModelTypeService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * 数据模型分类(DataModelType)控制类
 *
 * @author sei
 * @since 2020-07-28 23:24:26
 */
@RestController
@Api(value = "DataModelTypeApi", tags = "数据模型分类服务")
@RequestMapping(path = "dataModelType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataModelTypeController extends BaseTreeController<DataModelType, DataModelTypeDto>
        implements DataModelTypeApi {
    /**
     * 数据模型分类服务对象
     */
    @Autowired
    private DataModelTypeService service;

    @Override
    public BaseTreeService<DataModelType> getService() {
        return service;
    }

    /**
     * 获取数据模型类型的树
     *
     * @return 获取数据模型类型的树
     */
    @Override
    public ResultData<List<DataModelTypeDto>> getModelTypeTree() {
        List<DataModelTypeDto> tree = new LinkedList<>();
        List<DataModelType> roots = service.getAllRootNode();
        if (CollectionUtils.isNotEmpty(roots)) {
            for (DataModelType root : roots) {
                DataModelType node = service.getTree(root.getId());
                tree.add(convertToDto(node));
            }
        }

        return ResultData.success(tree);
    }

    /**
     * 根据模型代码获取一个节点的树
     *
     * @param code 节点code
     * @return 节点树
     */
    @Override
    public ResultData<DataModelTypeDto> getTreeByCode(String code) {
        ResultData<DataModelType> resultData = service.getTreeByCode(code);
        if (resultData.successful()) {
            return ResultData.success(convertToDto(resultData.getData()));
        }
        return ResultData.fail(resultData.getMessage());
    }
}