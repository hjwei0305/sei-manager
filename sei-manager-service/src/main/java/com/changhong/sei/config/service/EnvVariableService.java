package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.EnvVariableDao;
import com.changhong.sei.config.dao.EnvVariableValueDao;
import com.changhong.sei.config.entity.EnvVariable;
import com.changhong.sei.config.entity.EnvVariableValue;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.deploy.entity.RuntimeEnv;
import com.changhong.sei.deploy.service.RuntimeEnvService;
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
 * 环境变量(ConfEnvVariable)业务逻辑实现类
 *
 * @author sei
 * @since 2021-03-02 14:26:26
 */
@Service("envVariableService")
public class EnvVariableService extends BaseEntityService<EnvVariable> {
    @Autowired
    private EnvVariableDao dao;
    @Autowired
    private EnvVariableValueDao variableValueDao;
    @Autowired
    private RuntimeEnvService runtimeEnvService;

    @Override
    protected BaseEntityDao<EnvVariable> getDao() {
        return dao;
    }

    /**
     * 主键删除
     *
     * @param s 主键
     * @return 返回操作结果对象
     */
    @Override
    @Transactional
    public OperateResult delete(String s) {
        EnvVariable variable = dao.findOne(s);
        if (Objects.isNull(variable)) {
            return OperateResult.operationFailure("不存在删除的对象.");
        }
        if (UseStatus.NONE == variable.getUseStatus()) {
            return super.preDelete(s);
        } else if (UseStatus.ENABLE == variable.getUseStatus()) {
            // 更新为禁用
            variable.setUseStatus(UseStatus.DISABLE);
            dao.save(variable);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 获取所有环境变量key列表
     *
     * @return key列表
     */
    public List<EnvVariable> findAllKey() {
        return dao.findAll();
    }

    /**
     * 通过key获取各个环境变量清单
     *
     * @param key 环境变量key
     * @return 环境变量清单
     */
    public ResultData<List<EnvVariableValue>> findEnvVariableValues(String key) {
        EnvVariable variable = findByProperty(EnvVariable.CODE_FIELD, key);
        if (Objects.isNull(variable)) {
            return ResultData.fail(key + "-不是一个环境变量.");
        }

        List<RuntimeEnv> envList = runtimeEnvService.findAllUnfrozen();
        if (CollectionUtils.isEmpty(envList)) {
            return ResultData.fail("无可用的运行环境.");
        }
        List<EnvVariableValue> values = variableValueDao.findListByProperty(EnvVariableValue.FIELD_KEY, key);
        if (Objects.isNull(values)) {
            values = new ArrayList<>();
        }

        Map<String, EnvVariableValue> data = values.stream().collect(Collectors.toMap(EnvVariableValue::getEnvCode, v -> v));

        List<EnvVariableValue> results = new ArrayList<>();
        EnvVariableValue variableValue;
        for (RuntimeEnv env : envList) {
            variableValue = data.get(env.getCode());
            if (Objects.isNull(variableValue)) {
                variableValue = new EnvVariableValue();
                variableValue.setEnvCode(env.getCode());
                variableValue.setEnvName(env.getName());
                variableValue.setKey(key);
                variableValue.setRemark(variable.getRemark());
            }
            results.add(variableValue);
        }

        return ResultData.success(results);
    }

    /**
     * 批量保存变量
     */
    @Transactional
    public ResultData<Void> saveVariableValue(List<EnvVariableValue> variableValues) {
        if (CollectionUtils.isEmpty(variableValues)) {
            return ResultData.fail("持久化的环境变量值不存在.");
        }
        variableValueDao.save(variableValues);
        return ResultData.success();
    }

    /**
     * 主键删除
     *
     * @param ids 主键
     * @return 返回操作结果对象
     */
    @Transactional
    public OperateResult deleteVariableValue(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return OperateResult.operationFailure("删除的数据为空.");
        }
        for (String id : ids) {
            EnvVariable variable = dao.findOne(id);
            if (Objects.isNull(variable)) {
                continue;
            }
            if (UseStatus.NONE == variable.getUseStatus()) {
                super.preDelete(id);
            } else if (UseStatus.ENABLE == variable.getUseStatus()) {
                // 更新为禁用
                variable.setUseStatus(UseStatus.DISABLE);
                dao.save(variable);
            }
        }
        return OperateResult.operationSuccess();
    }
}