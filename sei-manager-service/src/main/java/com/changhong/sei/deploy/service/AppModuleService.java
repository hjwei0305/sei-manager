package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.deploy.dao.AppModuleDao;
import com.changhong.sei.deploy.dao.AppModuleRequisitionDao;
import com.changhong.sei.deploy.dto.AppModuleRequisitionDto;
import com.changhong.sei.deploy.dto.ApplyType;
import com.changhong.sei.deploy.dto.ApprovalStatus;
import com.changhong.sei.deploy.entity.AppModule;
import com.changhong.sei.deploy.entity.AppModuleRequisition;
import com.changhong.sei.deploy.entity.Application;
import com.changhong.sei.deploy.entity.RequisitionOrder;
import com.changhong.sei.integrated.service.GitlabService;
import com.changhong.sei.integrated.vo.ProjectType;
import com.changhong.sei.integrated.vo.ProjectVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;


/**
 * 应用模块(AppModule)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-26 14:45:21
 */
@Service("appModuleService")
public class AppModuleService extends BaseEntityService<AppModule> {
    @Autowired
    private AppModuleDao dao;
    @Autowired
    private AppModuleRequisitionDao appModuleRequisitionDao;
    @Autowired
    private RequisitionOrderService requisitionOrderService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private GitlabService gitlabService;

    /**
     * 开发运维平台的服务端地址(若有代理,配置代理后的地址)
     */
    @Value("${sei.server.host}")
    private String serverHost;
    @Value("${sei.build.hook}")
    private Boolean hook;

    @Override
    protected BaseEntityDao<AppModule> getDao() {
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
        AppModule module = this.findOne(id);
        if (Objects.isNull(module)) {
            return OperateResult.operationFailure("[" + id + "]应用模块不存在,删除失败!");
        }
        if (!module.getFrozen()) {
            return OperateResult.operationFailure("[" + id + "]应用模块已审核确认,不允许删除!");
        }
//        if (appModuleService.isExistsByProperty(AppModule.FIELD_APP_ID, id)) {
//            return OperateResult.operationFailure("[" + id + "]应用存在应用模块,不允许删除!");
//        }
        return super.preDelete(id);
    }

    /**
     * 分页查询应用模块申请单
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    public PageResult<AppModuleRequisition> findRequisitionByPage(Search search) {
        return appModuleRequisitionDao.findByPage(search);
    }

    /**
     * 创建应用模块申请单
     *
     * @param module 模块
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<AppModuleRequisitionDto> createRequisition(AppModule module) {
        // 申请是设置为冻结状态,带申请审核确认后再值为可用状态
        module.setFrozen(Boolean.TRUE);
        // 保存应用模块
        OperateResultWithData<AppModule> resultWithData = this.save(module);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = new RequisitionOrder();
            // 申请类型:应用模块申请
            requisitionOrder.setApplicationType(ApplyType.MODULE);
            // 应用模块id
            requisitionOrder.setRelationId(module.getId());
            // 申请摘要
            requisitionOrder.setSummary(module.getName().concat("[").concat(module.getCode()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.createRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                AppModuleRequisitionDto dto = new AppModuleRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplicationType());
                dto.setApprovalStatus(requisition.getApprovalStatus());
                dto.setRelationId(module.getId());
                dto.setAppId(module.getAppId());
                dto.setCode(module.getCode());
                dto.setName(module.getName());
                dto.setNameSpace(module.getNameSpace());
                dto.setVersion(module.getVersion());
                dto.setRemark(module.getRemark());
                return ResultData.success(dto);
            } else {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail(result.getMessage());
            }
        } else {
            return ResultData.fail(resultWithData.getMessage());
        }
    }

    /**
     * 编辑修改应用模块申请单
     *
     * @param appModule 应用模块
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<AppModuleRequisitionDto> modifyRequisition(AppModule appModule) {
        AppModule module = this.findOne(appModule.getId());
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块不存在!");
        }
        // 检查应用审核状态
        if (!module.getFrozen()) {
            return ResultData.fail("应用模块已审核,不允许编辑!");
        }

        module.setCode(appModule.getCode());
        module.setName(appModule.getName());
        module.setVersion(appModule.getVersion());
        module.setNameSpace(appModule.getNameSpace());
        module.setRemark(appModule.getRemark());

        // 保存应用模块
        OperateResultWithData<AppModule> resultWithData = this.save(module);
        if (resultWithData.successful()) {
            RequisitionOrder requisitionOrder = requisitionOrderService.getByRelationId(module.getId());
            if (Objects.isNull(requisitionOrder)) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail("申请单不存在!");
            }
            // 检查申请单是否已审核
            if (ApprovalStatus.INITIAL != requisitionOrder.getApprovalStatus()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail("申请单不存在!");
            }

            // 申请类型:应用模块申请
            requisitionOrder.setApplicationType(ApplyType.MODULE);
            // 应用模块id
            requisitionOrder.setRelationId(module.getId());
            // 申请摘要
            requisitionOrder.setSummary(module.getName().concat("[").concat(module.getCode()).concat("]"));

            ResultData<RequisitionOrder> result = requisitionOrderService.modifyRequisition(requisitionOrder);
            if (result.successful()) {
                RequisitionOrder requisition = result.getData();
                AppModuleRequisitionDto dto = new AppModuleRequisitionDto();
                dto.setId(requisition.getId());
                dto.setApplicantAccount(requisition.getApplicantAccount());
                dto.setApplicantUserName(requisition.getApplicantUserName());
                dto.setApplicationTime(requisition.getApplicationTime());
                dto.setApplyType(requisition.getApplicationType());
                dto.setApprovalStatus(requisition.getApprovalStatus());
                dto.setRelationId(module.getId());
                dto.setAppId(module.getAppId());
                dto.setCode(module.getCode());
                dto.setName(module.getName());
                dto.setNameSpace(module.getNameSpace());
                dto.setVersion(module.getVersion());
                dto.setRemark(module.getRemark());
                return ResultData.success(dto);
            } else {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.fail(result.getMessage());
            }
        } else {
            return ResultData.fail(resultWithData.getMessage());
        }
    }

    /**
     * 删除应用模块申请单
     *
     * @param id@return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> deleteRequisition(String id) {
        AppModule module = this.findOne(id);
        if (Objects.nonNull(module)) {
            if (module.getFrozen()) {
                // 删除应用模块
                this.delete(id);

                // 删除申请单
                ResultData<Void> resultData = requisitionOrderService.deleteRequisition(id);
                if (resultData.successful()) {
                    return ResultData.success();
                } else {
                    // 事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultData.fail(resultData.getMessage());
                }
            } else {
                return ResultData.fail("应用模块已审核,禁止删除!");
            }
        }
        return ResultData.fail("应用模块不存在!");
    }

    /**
     * 流程审核完成,更新冻结状态为:启用
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> updateFrozen(String id) {
        AppModule module = this.findOne(id);
        Application application = applicationService.findOne(module.getAppId());

        // 创建git项目
        ProjectVo project = new ProjectVo();
        project.setCode(module.getCode());
        project.setName(module.getName().concat(module.getRemark()));
        project.setGroupId(application.getGroupCode());
        if (StringUtils.isBlank(module.getNameSpace())) {
            project.setType(ProjectType.WEB);
        } else {
            project.setType(ProjectType.JAVA);
            project.setNameSpace(module.getNameSpace());
        }
        // 创建gitlab项目
        ResultData<ProjectVo> resultData = gitlabService.createProject(project);
        if (resultData.successful()) {
            // 流程审核完成,更新冻结状态为:启用
            module.setFrozen(Boolean.FALSE);

            // 回写gitlab数据
            ProjectVo gitProject = resultData.getData();
            module.setGitId(gitProject.getGitId());
            module.setGitHttpUrl(gitProject.getGitHttpUrl());
            module.setGitSshUrl(gitProject.getGitSshUrl());
            module.setGitWebUrl(gitProject.getGitWebUrl());
            module.setGitCreateTime(gitProject.getGitCreateTime());
            OperateResultWithData<AppModule> result1 = this.save(module);
            if (result1.notSuccessful()) {
                return ResultData.fail(result1.getMessage());
            } else {
                // 开关控制是否添加hook
                if (Objects.nonNull(hook) && hook) {
                    // @see ReleaseRecordApi#webhook
                    gitlabService.addProjectHook(gitProject.getGitId(), serverHost.concat("/releaseRecord/webhook"));
                }
                return ResultData.success();
            }
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 根据代码获取应用模块
     *
     * @param code 应用模块代码
     * @return 返回应用模块
     */
    public AppModule getAppModule(String code) {
        return dao.findByProperty(AppModule.CODE_FIELD, code);
    }
}