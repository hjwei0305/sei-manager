package com.changhong.sei.config.controller;

import com.changhong.sei.common.TokenInterceptor;
import com.changhong.sei.config.api.AuthWhitelistApi;
import com.changhong.sei.config.dto.AuthWhitelistDto;
import com.changhong.sei.config.dto.SyncAuthWhitelistDto;
import com.changhong.sei.config.entity.AuthWhitelist;
import com.changhong.sei.config.service.AuthWhitelistService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.entity.RuntimeEnv;
import com.changhong.sei.ge.service.RuntimeEnvService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
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
    @Autowired
    private RuntimeEnvService runtimeEnvService;
    @Autowired
    private TokenInterceptor tokenInterceptor;

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

    /**
     * 通过应用和环境代码获取应用认证白名单
     *
     * @param appCode 应用代码
     * @param envCode 环境代码
     * @return 业务实体
     */
    @Override
    public ResultData<List<AuthWhitelistDto>> findByAppEnv(String appCode, String envCode) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AuthWhitelist.FIELD_APP_CODE, appCode));
        search.addFilter(new SearchFilter(AuthWhitelist.FIELD_ENV_CODE, envCode));
        List<AuthWhitelist> list = service.findByFilters(search);
        List<AuthWhitelistDto> dtoList = list.stream().map(a -> dtoModelMapper.map(a, AuthWhitelistDto.class)).collect(Collectors.toList());
        return ResultData.success(dtoList);
    }

    /**
     * 同步配置到其他环境
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<Void> syncConfigs(SyncAuthWhitelistDto dto) {
        return service.syncConfigs(dto);
    }

    /**
     * 发布
     *
     * @param env 环境代码
     * @return 操作结果
     */
    @Override
    public ResultData<Void> publish(String env) {
        RuntimeEnv runtimeEnv = runtimeEnvService.findByCode(env);
        if (Objects.isNull(runtimeEnv)) {
            return ResultData.fail("运行环境中未找到代码[" + env + "].");
        }
        RestTemplate restTemplate = new RestTemplate();
        //向restTemplate中添加自定义的拦截器
        restTemplate.getInterceptors().add(tokenInterceptor);

        String baseUrl = runtimeEnv.getGatewayServer();
        return restTemplate.postForObject(baseUrl + "/routes/reloadCache", null, ResultData.class);
    }
}