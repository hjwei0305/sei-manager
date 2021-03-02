package com.changhong.sei.config.service;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.config.dao.EnvVariableDao;
import com.changhong.sei.config.entity.EnvVariable;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}