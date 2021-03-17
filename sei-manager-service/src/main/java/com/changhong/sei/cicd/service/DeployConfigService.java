package com.changhong.sei.cicd.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.cicd.dao.DeployConfigDao;
import com.changhong.sei.cicd.entity.DeployConfig;
import com.changhong.sei.integrated.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;

/**
 * 部署配置(DeployConfig)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("deployConfigService")
public class DeployConfigService extends BaseEntityService<DeployConfig> {
    @Autowired
    private DeployConfigDao dao;
    @Autowired
    private DeployTemplateService deployTemplateService;
    @Autowired
    private JenkinsService jenkinsService;

    @Override
    protected BaseEntityDao<DeployConfig> getDao() {
        return dao;
    }

    /**
     * 数据保存操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResultWithData<DeployConfig> save(DeployConfig entity) {
        // 获取是否是新增
        boolean isNew = isNew(entity);
        if (isNew) {
            // 新增检查配置是否存在
            ResultData<DeployConfig> resultData = getDeployConfig(entity.getEnvCode(), entity.getAppId(), entity.getModuleCode());
            if (resultData.successful()) {
                DeployConfig deployConfig = resultData.getData();
                return OperateResultWithData.operationFailure("已存在ENV[" + deployConfig.getEnvCode() + "]ModuleCode[" + deployConfig.getModuleCode() + "]的部署配置");
            }
        }
        OperateResultWithData<DeployConfig> result = super.save(entity);
        if (result.successful()) {
            // 同步Jenkins任务
            // 任务名
            String jobName = entity.getJobName();
            String jobXml;
            ResultData<String> xmlResult = deployTemplateService.generateJobXml(entity.getTempId());
            if (xmlResult.successful()) {
                // 创建Jenkins任务
                jobXml = xmlResult.getData();
            } else {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return OperateResultWithData.operationFailure(xmlResult.getMessage());
            }

            ResultData<Void> resultData;
            if (isNew) {
                // 创建Jenkins任务
                resultData = jenkinsService.createJob(jobName, jobXml);
            } else {
                // 修改Jenkins任务
                resultData = jenkinsService.updateJob(jobName, jobXml);
            }
            if (resultData.failed()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return OperateResultWithData.operationFailure(resultData.getMessage());
            }
        }
        return result;
    }

    /**
     * 主键删除
     *
     * @param id 主键
     * @return 返回操作结果对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResult delete(String id) {
        DeployConfig config = this.findOne(id);
        if (Objects.isNull(config)) {
            return OperateResult.operationFailure("部署配置不存在.");
        }
        OperateResult result = super.delete(id);
        if (result.successful()) {
            // 任务名
            String jobName = config.getJobName();
            // 删除Jenkins任务
            ResultData<Void> resultData = jenkinsService.deleteJob(jobName);

            if (resultData.failed()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return OperateResult.operationFailure(resultData.getMessage());
            }
        }
        return result;
    }

    /**
     * 获取部署配置
     *
     * @param envCode    环境代码
     * @param moduleCode 模块代码
     * @return 返回部署配置
     */
    public ResultData<DeployConfig> getDeployConfig(String envCode, String appId, String moduleCode) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(DeployConfig.FIELD_ENV_CODE, envCode));
        search.addFilter(new SearchFilter(DeployConfig.FIELD_APP_ID, appId));
        search.addFilter(new SearchFilter(DeployConfig.FIELD_MODULE_CODE, moduleCode));
        DeployConfig config = dao.findFirstByFilters(search);
        if (Objects.isNull(config)) {
            return ResultData.fail("不存在ENV[" + envCode + "]ModuleCode[" + moduleCode + "]的部署配置");
        } else {
            return ResultData.success(config);
        }
    }
}