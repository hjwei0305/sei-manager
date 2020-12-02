package com.changhong.sei.integrated.service;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.integrated.vo.ProjectVo;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.ReleasesApi;
import org.gitlab4j.api.TagsApi;
import org.gitlab4j.api.models.Release;
import org.gitlab4j.api.models.ReleaseParams;
import org.gitlab4j.api.models.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private GitLabApi getGitLabApi() {
        String gitlabHost = "http://rddgit.changhong.com";
        try {
//            GitlabSession session = GitlabAPI.connect(gitlabHost, username, password);
//            GitLabApi gitlabAPI = GitLabApi.oauth2Login(gitlabHost, username, password);
            GitLabApi gitlabAPI = new GitLabApi(gitlabHost, "n_DEjjbZxTrSWNf-SKsV");
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
    public ResultData<List<Tag>> getProjectTags(String gitId) {
        try {
            TagsApi api = this.getGitLabApi().getTagsApi();
            List<Tag> tags = api.getTags(gitId);
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
    public ResultData<Tag> createProjectTag(String gitId, String tagName, String branch, String message) {
        try {
            TagsApi api = this.getGitLabApi().getTagsApi();
            Tag tag = api.createTag(gitId, tagName, branch, message, "");
            return ResultData.success(tag);
        } catch (Exception e) {
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
    public ResultData<Void> deleteProjectTag(String gitId, String tagName) {
        try {
            TagsApi api = this.getGitLabApi().getTagsApi();
            api.deleteTag(gitId, tagName);
            return ResultData.success();
        } catch (Exception e) {
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
    public ResultData<Release> createProjectRelease(String gitId, String name, String tagName, String message) {
        try {
            ReleasesApi api = this.getGitLabApi().getReleasesApi();
            ReleaseParams params = new ReleaseParams();
            params.setName(name);
            params.setTagName(tagName);
            params.setDescription(message);
            Release release = api.createRelease(gitId, params);

            return ResultData.success(release);
        } catch (Exception e) {
            LOG.error("创建tag异常", e);
            return ResultData.fail("创建tag异常" + e.getMessage());
        }
    }
}
