package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.deploy.dto.NodeDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 服务器节点(Node)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "node")
public interface NodeApi extends BaseEntityApi<NodeDto>, FindAllApi<NodeDto> {

}