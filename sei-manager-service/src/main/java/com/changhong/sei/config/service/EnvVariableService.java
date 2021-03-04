package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.EnvVariableDao;
import com.changhong.sei.config.dao.EnvVariableValueDao;
import com.changhong.sei.config.dto.EnvVariableValueDto;
import com.changhong.sei.config.entity.EnvVariable;
import com.changhong.sei.config.entity.EnvVariableValue;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public List<EnvVariableValue> findEnvVariableValues(String key) {
        return variableValueDao.findListByProperty(EnvVariableValue.FIELD_KEY, key);
    }

    /**
     * 批量保存变量
     */
    @Transactional
    public ResultData<Void> saveVariableValue(List<EnvVariableValue> variableValues) {


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