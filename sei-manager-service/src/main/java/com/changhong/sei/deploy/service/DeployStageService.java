package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.common.Constants;
import com.changhong.sei.deploy.dao.DeployStageDao;
import com.changhong.sei.deploy.dto.DeployStageParamDto;
import com.changhong.sei.deploy.entity.DeployStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;


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

    @Override
    protected BaseEntityDao<DeployStage> getDao() {
        return dao;
    }

    /**
     * 获取部署阶段参数
     */
    public List<DeployStageParamDto> getStageParameters(String stageId) {
        return Constants.DEFAULT_STAGE_PARAMS;
    }
}