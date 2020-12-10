package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.ReleaseVersionDao;
import com.changhong.sei.deploy.dto.BuildStatus;
import com.changhong.sei.deploy.entity.ReleaseRecord;
import com.changhong.sei.deploy.entity.ReleaseVersion;
import com.changhong.sei.integrated.service.GitlabService;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 版本发布记录(ReleaseVersion)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("releaseVersionService")
public class ReleaseVersionService extends BaseEntityService<ReleaseVersion> {
    @Autowired
    private ReleaseVersionDao dao;

    @Autowired
    private AppModuleService moduleService;

    @Autowired
    private GitlabService gitlabService;

    @Override
    protected BaseEntityDao<ReleaseVersion> getDao() {
        return dao;
    }

    /**
     * 发布版本
     * Jenkins构建成功,调用gitlab创建版本
     *
     * @param releaseRecord 构建记录
     */
    @Transactional
    public ResultData<Void> releaseVersion(ReleaseRecord releaseRecord) {
        if (Objects.isNull(releaseRecord)) {
            return ResultData.fail("发布记录不能为空.");
        }
        if (BuildStatus.SUCCESS == releaseRecord.getBuildStatus()) {
            String gitId = releaseRecord.getGitId();
            String tag = releaseRecord.getTagName() + "-Release";
            String refTag = releaseRecord.getTagName();
            String versionName = releaseRecord.getName();
            if (StringUtils.isBlank(versionName)) {
                versionName = tag;
            }
            String remark = releaseRecord.getRemark();
            if (StringUtils.isBlank(remark)) {
                remark = versionName;
            }
//            LogUtil.bizLog("创建gitlab版本, gitId:{}, versionName:{}, tag:{}, refTag:{}, remark:{}, ", gitId, versionName, tag, refTag, remark);
            ResultData<Release> resultData = gitlabService.createProjectRelease(gitId, versionName, tag, refTag, remark);
            if (resultData.successful()) {
                Release gitlabRelease = resultData.getData();
                ReleaseVersion version = new ReleaseVersion();
                version.setAppId(releaseRecord.getAppId());
                version.setAppName(releaseRecord.getAppName());
                version.setGitId(releaseRecord.getGitId());
                version.setModuleName(releaseRecord.getModuleName());
                version.setName(versionName);
                version.setCommitId(gitlabRelease.getCommit().getId());
                // 约定镜像命名规范
                version.setImageName(releaseRecord.getModuleName() + "/" + refTag);
                version.setVersion(tag);
                version.setRemark(remark);
                version.setCreateTime(LocalDateTime.now());
                this.save(version);
                LogUtil.bizLog(releaseRecord.getJobName() + "发版成功[" + releaseRecord.getTagName() + "].");

                moduleService.updateVersion(releaseRecord.getModuleCode(), releaseRecord.getTagName());

                return ResultData.success();
            } else {
                return ResultData.fail(releaseRecord.getJobName() + "发版失败: " + resultData.getMessage());
            }
        } else {
            return ResultData.fail(releaseRecord.getJobName() + "发版失败,当前构建状态:" + releaseRecord.getBuildStatus().name());
        }
    }
}