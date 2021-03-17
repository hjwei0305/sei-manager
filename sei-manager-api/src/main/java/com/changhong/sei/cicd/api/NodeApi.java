package com.changhong.sei.cicd.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.cicd.dto.NodeDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 服务器节点(Node)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = "node")
public interface NodeApi extends BaseEntityApi<NodeDto>, FindAllApi<NodeDto> {

    /**
     * 根据环境代码获取节点
     *
     * @param env 环境代码
     * @return 返回节点
     */
    @GetMapping(path = "getNode")
    @ApiOperation(value = "根据环境代码获取节点", notes = "根据环境代码获取节点")
    ResultData<List<NodeDto>> getNode(@RequestParam("env") String env);
}