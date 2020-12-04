package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.ReleaseVersionDao;
import com.changhong.sei.deploy.dto.BuildStatus;
import com.changhong.sei.deploy.entity.ReleaseRecord;
import com.changhong.sei.deploy.entity.ReleaseVersion;
import com.changhong.sei.integrated.service.GitlabService;
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
            ResultData<Release> resultData = gitlabService.createProjectRelease(releaseRecord.getGitId(), releaseRecord.getName(), releaseRecord.getTagName(), releaseRecord.getName());
            if (resultData.successful()) {
                Release gitlabRelease = resultData.getData();
                ReleaseVersion version = new ReleaseVersion();
                version.setAppId(releaseRecord.getAppId());
                version.setAppName(releaseRecord.getAppName());
                version.setGitId(releaseRecord.getGitId());
                version.setModuleName(releaseRecord.getModuleName());
                version.setName(releaseRecord.getName());
                version.setCommitId(gitlabRelease.getCommit().getId());
                // 约定镜像命名规范
                version.setImageName(releaseRecord.getModuleName() + "/" + gitlabRelease.getTagName());
                version.setVersion(gitlabRelease.getTagName());
                version.setRemark(gitlabRelease.getDescription());
                version.setCreateTime(LocalDateTime.now());
                this.save(version);
            }
            return ResultData.success();
        } else {
            return ResultData.fail("" + releaseRecord.getBuildStatus().name());
        }
    }
}