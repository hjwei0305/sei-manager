package com.changhong.sei.deploy.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.api.AppModuleApi;
import com.changhong.sei.deploy.dto.AppModuleDto;
import com.changhong.sei.deploy.entity.AppModule;
import com.changhong.sei.deploy.entity.Application;
import com.changhong.sei.deploy.service.AppModuleService;
import com.changhong.sei.deploy.service.ApplicationService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用模块(AppModule)控制类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@RestController
@Api(value = "AppModuleApi", tags = "应用模块服务")
@RequestMapping(path = "appModule", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppModuleController extends BaseEntityController<AppModule, AppModuleDto> implements AppModuleApi {
    /**
     * 应用模块服务对象
     */
    @Autowired
    private AppModuleService service;
    @Autowired
    private ApplicationService applicationService;

    @Override
    public BaseEntityService<AppModule> getService() {
        return service;
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<AppModuleDto>> findByPage(Search search) {
        PageResult<AppModule> pageResult = service.findByPage(search);
        PageResult<AppModuleDto> result = new PageResult<>(pageResult);

        List<AppModule> appModules = pageResult.getRows();
        if (CollectionUtils.isNotEmpty(appModules)) {
            Map<String, String> appMap = new HashMap<>();
            List<Application> applications = applicationService.findAll();
            if (CollectionUtils.isNotEmpty(applications)) {
                appMap = applications.stream().collect(Collectors.toMap(Application::getId, Application::getName));
            }

            AppModuleDto moduleDto;
            List<AppModuleDto> dtos = new ArrayList<>();
            for (AppModule module : appModules) {
                moduleDto = dtoModelMapper.map(module, AppModuleDto.class);
                moduleDto.setAppName(appMap.get(module.getAppId()));

                dtos.add(moduleDto);
            }
            result.setRows(dtos);
        }

        return ResultData.success(result);
    }

    /**
     * 通过应用Id获取模块清单
     *
     * @param appId 应用id
     * @return 模块清单
     */
    @Override
    public ResultData<List<AppModuleDto>> findAppId(String appId) {
        Application application = applicationService.findOne(appId);
        if (Objects.isNull(application)) {
            return ResultData.fail("[" + appId + "]应用不存在");
        }

        List<AppModuleDto> dtos = new ArrayList<>();
        List<AppModule> appModules = service.findListByProperty("", appId);
        if (CollectionUtils.isNotEmpty(appModules)) {
            String appName = application.getName();
            AppModuleDto moduleDto;
            for (AppModule module : appModules) {
                moduleDto = dtoModelMapper.map(module, AppModuleDto.class);
                moduleDto.setAppName(appName);

                dtos.add(moduleDto);
            }
        }
        return ResultData.success(dtos);
    }
}