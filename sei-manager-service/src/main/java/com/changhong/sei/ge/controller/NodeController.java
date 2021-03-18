package com.changhong.sei.ge.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.api.NodeApi;
import com.changhong.sei.ge.dto.NodeDto;
import com.changhong.sei.ge.entity.Node;
import com.changhong.sei.ge.service.NodeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 服务器节点(Node)控制类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@RestController
@Api(value = "NodeApi", tags = "服务器节点服务")
@RequestMapping(path = "node", produces = MediaType.APPLICATION_JSON_VALUE)
public class NodeController extends BaseEntityController<Node, NodeDto> implements NodeApi {
    /**
     * 服务器节点服务对象
     */
    @Autowired
    private NodeService service;

    @Override
    public BaseEntityService<Node> getService() {
        return service;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<NodeDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<NodeDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 根据环境代码获取节点
     *
     * @param env 环境代码
     * @return 返回节点
     */
    @Override
    public ResultData<List<NodeDto>> getNode(String env) {
        return ResultData.success(convertToDtos(service.getNode(env)));
    }
}