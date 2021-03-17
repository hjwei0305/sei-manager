package com.changhong.sei.config.service;

import com.changhong.sei.config.dao.AuthWhitelistDao;
import com.changhong.sei.config.dto.SyncAuthWhitelistDto;
import com.changhong.sei.config.entity.AuthWhitelist;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.entity.RuntimeEnv;
import com.changhong.sei.ge.service.RuntimeEnvService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 网关认证白名单(AuthWhitelist)业务逻辑实现类
 *
 * @author sei
 * @since 2021-02-22 21:43:58
 */
@Service("authWhitelistService")
public class AuthWhitelistService extends BaseEntityService<AuthWhitelist> {
    @Autowired
    private AuthWhitelistDao dao;
    @Autowired
    private RuntimeEnvService runtimeEnvService;

    @Override
    protected BaseEntityDao<AuthWhitelist> getDao() {
        return dao;
    }

    /**
     * 同步配置到其他环境
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> syncConfigs(SyncAuthWhitelistDto dto) {
        if (Objects.isNull(dto)) {
            return ResultData.fail("配置数据不能为空.");
        }

        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(AuthWhitelist.FIELD_APP_CODE, dto.getAppCode()));
        search.addFilter(new SearchFilter(AuthWhitelist.FIELD_ENV_CODE, dto.getEnvCode()));
        List<AuthWhitelist> list = dao.findByFilters(search);
        if (CollectionUtils.isEmpty(list)) {
            return ResultData.success();
        }

        List<RuntimeEnv> envList = runtimeEnvService.findAllUnfrozen();
        if (CollectionUtils.isEmpty(envList)) {
            return ResultData.fail("没有可用的运行环境");
        }
        Map<String, String> envMap = envList.stream().collect(Collectors.toMap(RuntimeEnv::getCode, RuntimeEnv::getName));
        AuthWhitelist authWhitelist;
        List<AuthWhitelist> whitelists = new ArrayList<>();
        for (String env : dto.getTargetEnvList()) {
            search.clearAll();
            search.addFilter(new SearchFilter(AuthWhitelist.FIELD_APP_CODE, dto.getAppCode()));
            search.addFilter(new SearchFilter(AuthWhitelist.FIELD_ENV_CODE, env));
            List<AuthWhitelist> envConfigLists = dao.findByFilters(search);
            Map<String, AuthWhitelist> map = envConfigLists.stream().collect(Collectors.toMap(AuthWhitelist::getUri, a -> a));
            for (AuthWhitelist obj : list) {
                authWhitelist = map.get(obj.getUri());
                if (Objects.isNull(authWhitelist)) {
                    authWhitelist = new AuthWhitelist();
                    authWhitelist.setAppCode(obj.getAppCode());
                    authWhitelist.setAppName(obj.getAppName());
                    authWhitelist.setEnvCode(env);
                    authWhitelist.setEnvName(envMap.get(env));
                    authWhitelist.setMethod(obj.getMethod());
                    authWhitelist.setUri(obj.getUri());
                    authWhitelist.setRemark(obj.getRemark());
                    whitelists.add(authWhitelist);
                }
            }
        }
        this.save(whitelists);
        return ResultData.success();
    }
}