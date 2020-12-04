package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.deploy.common.Constants;
import com.changhong.sei.deploy.dao.DeployStageDao;
import com.changhong.sei.deploy.dto.DeployStageParamDto;
import com.changhong.sei.deploy.entity.DeployStage;
import com.changhong.sei.deploy.entity.DeployTemplateStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 部署阶段(DeployStage)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:01
 */
@Service("deployStageService")
public class DeployStageService extends BaseEntityService<DeployStage> {
    @Autowired
    private DeployStageDao dao;
    @Autowired
    private DeployTemplateStageService deployTemplateStageService;

    @Override
    protected BaseEntityDao<DeployStage> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查状态控制删除
        DeployStage app = this.findOne(id);
        if (Objects.isNull(app)) {
            return OperateResult.operationFailure("[" + id + "]阶段不存在,删除失败!");
        }
        if (deployTemplateStageService.isExistsByProperty(DeployTemplateStage.FIELD_STAGE_ID, id)) {
            return OperateResult.operationFailure("[" + id + "]阶段已被应用于模版,不允许删除!");
        }
        return super.preDelete(id);
    }

    /**
     * 获取部署阶段参数
     */
    public List<DeployStageParamDto> getStageParameters(String stageId) {
        return Constants.DEFAULT_STAGE_PARAMS;
    }
}