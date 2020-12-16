package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.deploy.api.FlowDefinitionApi;
import com.changhong.sei.deploy.dto.FlowTypeDto;
import com.changhong.sei.deploy.dto.FlowTypeNodeDto;
import com.changhong.sei.deploy.dto.FlowInstanceTaskDto;
import com.changhong.sei.deploy.dto.FlowInstanceDto;
import com.changhong.sei.deploy.entity.FlowInstance;
import com.changhong.sei.deploy.entity.FlowInstanceTask;
import com.changhong.sei.deploy.entity.FlowType;
import com.changhong.sei.deploy.entity.FlowTypeNode;
import com.changhong.sei.deploy.service.*;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 流程定义(FlowDefinition)控制类
 *
 * @author sei
 * @since 2020-12-15 18:09:43
 */
@RestController
@Api(value = "FlowDefinitionApi", tags = "流程定义服务")
@RequestMapping(path = "flow/definition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FlowDefinitionController implements FlowDefinitionApi {
    @Autowired
    private FlowTypeService typeService;
    @Autowired
    private FlowInstanceService instanceService;
    @Autowired
    private FlowTypeNodeService nodeService;
    @Autowired
    private FlowInstanceTaskService instanceTaskService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 保存流程类型
     *
     * @param dto dto
     * @return 返回结果
     */
    @Override
    public ResultData<FlowTypeDto> saveType(FlowTypeDto dto) {
        ResultData<FlowType> resultData = typeService.saveType(modelMapper.map(dto, FlowType.class));
        if (resultData.getSuccess()) {
            return ResultData.success(modelMapper.map(resultData.getData(), FlowTypeDto.class));
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 分页查询流程类型
     *
     * @param search search
     * @return 分页数据结果
     */
    @Override
    public ResultData<PageResult<FlowTypeDto>> findTypeByPage(Search search) {
        PageResult<FlowType> pageResult = typeService.findTypeByPage(search);
        List<FlowTypeDto> typeDtoList = new ArrayList<>();
        List<FlowType> types = pageResult.getRows();
        if (CollectionUtils.isNotEmpty(types)) {
            for (FlowType type : types) {
                typeDtoList.add(modelMapper.map(type, FlowTypeDto.class));
            }
        }
        PageResult<FlowTypeDto> result = new PageResult<>(pageResult);
        result.setRows(typeDtoList);
        return ResultData.success(result);
    }

    /**
     * 保存流程类型节点
     *
     * @param dto dto
     * @return 返回结果
     */
    @Override
    public ResultData<FlowTypeNodeDto> saveTypeNode(FlowTypeNodeDto dto) {
        ResultData<FlowTypeNode> resultData = nodeService.saveTypeNode(modelMapper.map(dto, FlowTypeNode.class));
        if (resultData.getSuccess()) {
            return ResultData.success(modelMapper.map(resultData.getData(), FlowTypeNodeDto.class));
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 删除流程类型节点
     *
     * @param ids 流程类型节点Id集合
     * @return 操作结果
     */
    @Override
    public ResultData<Void> deleteTypeNode(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultData.fail("无删除的参数");
        }
        nodeService.deleteTypeNode(ids);
        return ResultData.success();
    }

    /**
     * 通过流程类型获取节点清单
     *
     * @param typeId 流程类型id
     * @return 返回结果
     */
    @Override
    public ResultData<List<FlowTypeNodeDto>> getTypeNodeByTypeId(String typeId) {
        List<FlowTypeNodeDto> nodeDtoList = new ArrayList<>();
        List<FlowTypeNode> nodeList = nodeService.getTypeNodeByTypeId(typeId);
        if (CollectionUtils.isNotEmpty(nodeList)) {
            for (FlowTypeNode node : nodeList) {
                nodeDtoList.add(modelMapper.map(node, FlowTypeNodeDto.class));
            }
        }
        return ResultData.success(nodeDtoList);
    }

    /**
     * 通过流程类型获取版本清单
     *
     * @param typeCode 流程类型代码
     * @return 返回结果
     */
    @Override
    public ResultData<List<FlowInstanceDto>> getTypeVersionByTypeCode(String typeCode) {
        List<FlowInstanceDto> nodeDtoList = new ArrayList<>();
        List<FlowInstance> list = instanceService.getTypeVersionByTypeCode(typeCode);
        if (CollectionUtils.isNotEmpty(list)) {
            for (FlowInstance node : list) {
                nodeDtoList.add(modelMapper.map(node, FlowInstanceDto.class));
            }
        }
        return ResultData.success(nodeDtoList);
    }

    /**
     * 通过流程类型获取节点清单
     *
     * @param instanceId 流程实例id
     * @return 返回结果
     */
    @Override
    public ResultData<List<FlowInstanceTaskDto>> getTaskByInstanceId(String instanceId) {
        List<FlowInstanceTaskDto> nodeDtoList = new ArrayList<>();
        List<FlowInstanceTask> list = instanceTaskService.getTypeNodeRecord(instanceId);
        if (CollectionUtils.isNotEmpty(list)) {
            for (FlowInstanceTask node : list) {
                nodeDtoList.add(modelMapper.map(node, FlowInstanceTaskDto.class));
            }
        }
        return ResultData.success(nodeDtoList);
    }

    /**
     * 发布流程类型
     *
     * @param typeId 流程类型id
     * @return 发布结果
     */
    @Override
    public ResultData<Void> publish(String typeId) {
        return typeService.publish(typeId);
    }
}