package com.changhong.sei.integrated.service;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.integrated.vo.ProjectVo;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabRelease;
import org.gitlab.api.models.GitlabTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 14:31
 */
@Service
public class GitlabService {
    private static final Logger LOG = LoggerFactory.getLogger(GitlabService.class);
    /**
     * 认证header
     */
    private static final String HEADER_TOKEN_KEY = "PRIVATE-TOKEN";
    /**
     * gitlab服务地址
     */
    @Value("${sei.gitlab.host}")
    private String host;
    /**
     * gitlab用户名
     */
    @Value("${sei.gitlab.username}")
    private String username;
    /**
     * gitlab密码
     */
    @Value("${sei.gitlab.password}")
    private String password;

    @Autowired
    private ApiTemplate restTemplate;

    /**
     * 创建gitlab连接
     */
    private GitlabAPI getGitlabAPI() {
        String gitlabHost = "http://rddgit.changhong.com";
        try {
//            GitlabSession session = GitlabAPI.connect(gitlabHost, username, password);
            GitlabAPI gitlabAPI = GitlabAPI.connect(gitlabHost, "n_DEjjbZxTrSWNf-SKsV");
            return gitlabAPI;
        } catch (Exception e) {
            throw new RuntimeException("获取Gitlab接口异常", e);
        }
    }

    /**
     * 创建git项目
     *
     * @param project 项目
     * @return 返回git项目创建结果
     */
    @SuppressWarnings("unchecked")
    public ResultData<ProjectVo> createProject(ProjectVo project) {
        String url = host.concat("/project/createGitProject");
        ResultData<HashMap<String, Object>> resultData = restTemplate.postByUrl(url, ResultData.class, project);
        if (LOG.isInfoEnabled()) {
            LOG.info("创建git项目: {}", JsonUtils.toJson(resultData));
        }
        if (resultData.successful()) {
            HashMap<String, Object> data = resultData.getData();
            project.setGitId(String.valueOf(data.get("id")));
            project.setGitWebUrl(String.valueOf(data.get("web_url")));
            project.setGitHttpUrl(String.valueOf(data.get("http_url_to_repo")));
            project.setGitSshUrl(String.valueOf(data.get("ssh_url_to_repo")));
            project.setGitCreateTime(LocalDateTime.now());
            return ResultData.success(project);
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 获取项目标签
     *
     * @param gitId git项目id
     * @return 创建结果
     */
    public ResultData<List<GitlabTag>> getProjectTags(String gitId) {
        try {
            GitlabAPI api = this.getGitlabAPI();
            List<GitlabTag> tags = api.getTags(gitId);
            return ResultData.success(tags);
        } catch (Exception e) {
            LOG.error("获取项目标签", e);
            return ResultData.fail("获取项目标签" + e.getMessage());
        }
    }

    /**
     * 创建标签
     *
     * @param gitId   git项目id
     * @param tagName tag名
     * @param branch  branch名
     * @param message 描述
     * @return 创建结果
     */
    public ResultData<GitlabTag> createProjectTag(String gitId, String tagName, String branch, String message) {
        try {
            GitlabTag tag = this.getGitlabAPI().addTag(gitId, tagName, branch, message, null);
            return ResultData.success(tag);
        } catch (IOException e) {
            LOG.error("创建tag异常", e);
            return ResultData.fail("创建tag异常" + e.getMessage());
        }
    }

    /**
     * 删除项目标签
     *
     * @param gitId   git项目id
     * @param tagName tag名
     * @return 创建结果
     */
    public ResultData<Void> deleteProjectRelease(String gitId, String tagName) {
        try {
            GitlabAPI api = this.getGitlabAPI();
            api.deleteTag(gitId, tagName);
            return ResultData.success();
        } catch (IOException e) {
            LOG.error("创建tag异常", e);
            return ResultData.fail("创建tag异常" + e.getMessage());
        }
    }

    /**
     * 创建项目版本
     *
     * @param gitId   git项目id
     * @param tagName tag名
     * @param message 描述
     * @return 创建结果
     */
    public ResultData<GitlabRelease> createProjectRelease(String gitId, String tagName, String message) {
        try {
            GitlabAPI api = this.getGitlabAPI();
            String tailUrl = GitlabProject.URL + "/" + gitId + GitlabTag.URL + "/" + tagName + "/release";
            GitlabRelease release = api.dispatch()
                    .with("description", message)
                    .to(tailUrl, GitlabRelease.class);

            return ResultData.success(release);
        } catch (IOException e) {
            LOG.error("创建tag异常", e);
            return ResultData.fail("创建tag异常" + e.getMessage());
        }
    }
}
