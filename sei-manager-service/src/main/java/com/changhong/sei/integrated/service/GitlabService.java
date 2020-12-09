package com.changhong.sei.integrated.service;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.integrated.vo.ProjectVo;
import org.gitlab4j.api.*;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
     * gitlab服务地址
     */
    @Value("${sei.gitlab.host}")
    private String host;
    /**
     * gitlab token
     */
    @Value("${sei.gitlab.token}")
    private String token;
    /**
     * nodejs服务
     */
    @Value("${sei.node-server}")
    private String nodeServer;

    @Autowired
    private ApiTemplate restTemplate;

    /**
     * 创建gitlab连接
     */
    private GitLabApi getGitLabApi() {
        try {
//            GitlabSession session = GitlabAPI.connect(gitlabHost, username, password);
//            GitLabApi gitlabAPI = GitLabApi.oauth2Login(gitlabHost, username, password);
            GitLabApi gitlabAPI = new GitLabApi(host, token);
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
        String url = nodeServer.concat("/project/createGitProject");
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
     * 添加项目Push Hook
     *
     * @param gitId git项目id
     * @return 创建结果
     */
    public ResultData<Void> addProjectHook(String gitId, String url) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ProjectApi hooksApi = gitLabApi.getProjectApi();
            hooksApi.addHook(gitId, url, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
            return ResultData.success();
        } catch (Exception e) {
            LOG.error("获取项目标签", e);
            return ResultData.fail("获取项目标签" + e.getMessage());
        }
    }

    /**
     * 获取项目标签
     *
     * @param gitId git项目id
     * @return 创建结果
     */
    public ResultData<List<Tag>> getProjectTags(String gitId) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            TagsApi api = gitLabApi.getTagsApi();
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
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            TagsApi api = gitLabApi.getTagsApi();
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
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            TagsApi api = gitLabApi.getTagsApi();
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
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ReleasesApi api = gitLabApi.getReleasesApi();
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

    /**
     * 获取gitlab可用用户
     *
     * @return 返回gitlab可用用户
     */
    public ResultData<List<User>> getUser() {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            UserApi userApi = gitLabApi.getUserApi();
            List<User> users = userApi.getActiveUsers();
            return ResultData.success(users);
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab用户异常", e);
            return ResultData.fail("获取gitlab用户异常:" + e.getMessage());
        }
    }

    /**
     * 获取gitlab可用用户
     *
     * @return 返回gitlab可用用户
     */
    public ResultData<User> getOptionalUserByEmail(String email) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            UserApi userApi = gitLabApi.getUserApi();
            Optional<User> optionalUser = userApi.getOptionalUserByEmail(email);
            return optionalUser.map(ResultData::success).orElseGet(() -> ResultData.fail("用户不存在."));
        } catch (Exception e) {
            LOG.error("获取gitlab用户异常", e);
            return ResultData.fail("获取gitlab用户异常:" + e.getMessage());
        }
    }

    /**
     * 获取gitlab可用用户
     *
     * @return 返回gitlab可用用户
     */
    public ResultData<User> createUser(String id, String account, String name, String email) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            UserApi userApi = gitLabApi.getUserApi();
            User user = new User();
            user.setName(name);
            user.setUsername(account);
            user.setEmail(email);
            user.setExternal(Boolean.TRUE);
            user.setExternUid(id);
            user.setProvider("SEI");
            user = userApi.createUser(user, "123456", true);
            return ResultData.success(user);
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab用户异常", e);
            return ResultData.fail("获取gitlab用户异常:" + e.getMessage());
        }
    }

    /**
     * 创建gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<Group> createGroup(String name, String remark) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();
            Group group = new Group();
            group.setName(name);
            group.setPath(name.toLowerCase());
            group.setDescription(remark);
            group = groupApi.addGroup(group);
            return ResultData.success(group);
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab用户异常", e);
            return ResultData.fail("获取gitlab用户异常:" + e.getMessage());
        }
    }

    /**
     * 创建gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<Group> updateGroup(String groupId, String remark) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();
            ResultData<Group> resultData = getGroup(groupId);
            if (resultData.successful()) {
                Group group = resultData.getData();
                group.setDescription(remark);
                group = groupApi.updateGroup(group);
                return ResultData.success(group);
            } else {
                return resultData;
            }
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab用户异常", e);
            return ResultData.fail("获取gitlab用户异常:" + e.getMessage());
        }
    }

    /**
     * 创建gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<Void> deleteGroup(String groupId) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();

            groupApi.deleteGroup(groupId);
            return ResultData.success();
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab用户异常", e);
            return ResultData.fail("获取gitlab用户异常:" + e.getMessage());
        }
    }

    /**
     * 创建gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<Group> getGroup(String groupId) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();

            Group group = groupApi.getGroup(groupId);
            return ResultData.success(group);
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab群组异常", e);
            return ResultData.fail("获取gitlab群组异常:" + e.getMessage());
        }
    }
}
