package com.changhong.sei.config.controller;

import com.changhong.sei.config.api.AuthWhitelistApi;
import com.changhong.sei.config.dto.AuthWhitelistDto;
import com.changhong.sei.config.entity.AuthWhitelist;
import com.changhong.sei.config.service.AuthWhitelistService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 网关认证白名单(AuthWhitelist)控制类
 *
 * @author sei
 * @since 2021-02-22 21:43:59
 */
@RestController
@Api(value = "AuthWhitelistApi", tags = "网关认证白名单服务")
@RequestMapping(path = AuthWhitelistApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthWhitelistController extends BaseEntityController<AuthWhitelist, AuthWhitelistDto> implements AuthWhitelistApi {
    /**
     * 配置变更集服务对象
     */
    @Autowired
    private AuthWhitelistService service;

    @Override
    public BaseEntityService<AuthWhitelist> getService() {
        return service;
    }

    /**
     * 获取指定环境的白名单
     *
     * @param envCode 环境代码
     * @return 业务实体
     */
    @Override
    public ResultData<List<AuthWhitelistDto>> get(String envCode) {
        List<AuthWhitelist> list = service.findListByProperty(AuthWhitelist.FIELD_ENV_CODE, envCode);
        List<AuthWhitelistDto> dtoList = list.stream().map(a -> dtoModelMapper.map(a, AuthWhitelistDto.class)).collect(Collectors.toList());
        return ResultData.success(dtoList);
    }
}