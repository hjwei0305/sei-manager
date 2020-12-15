package com.changhong.sei.integrated.service;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.integrated.vo.ProjectVo;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import java.util.Objects;
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
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            String groupId = project.getGroupId();
            GroupApi groupApi = gitLabApi.getGroupApi();
            Group group = groupApi.getGroup(groupId);
            if (Objects.isNull(group)) {
                return ResultData.fail("在gitlab上找不到群组[" + groupId + "]");
            }
            ProjectApi projectApi = gitLabApi.getProjectApi();
            Project project1 = projectApi.getProject(group.getPath(), project.getCode());
            if (Objects.nonNull(project1)) {
                project.setGitId(String.valueOf(project1.getId()));
                project.setGitWebUrl(project1.getWebUrl());
                project.setGitHttpUrl(project1.getHttpUrlToRepo());
                project.setGitSshUrl(project1.getSshUrlToRepo());
                project.setGitCreateTime(DateUtils.date2LocalDateTime(project1.getCreatedAt()));
                return ResultData.success(project);
            }
        } catch (Exception e) {
            LOG.warn("获取git项目异常: {}", ExceptionUtils.getRootCauseMessage(e));
        }

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
     * 获取git项目
     *
     * @param projectIdOrPath 项目id
     * @return 返回git项目
     */
    public ResultData<ProjectVo> getProject(String projectIdOrPath) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ProjectApi projectApi = gitLabApi.getProjectApi();
            Project project1 = projectApi.getProject(projectIdOrPath);
            if (Objects.nonNull(project1)) {
                ProjectVo project = new ProjectVo();
                project.setGitId(String.valueOf(project1.getId()));
                project.setGitWebUrl(project1.getWebUrl());
                project.setGitHttpUrl(project1.getHttpUrlToRepo());
                project.setGitSshUrl(project1.getSshUrlToRepo());
                project.setGitCreateTime(LocalDateTime.now());
                return ResultData.success(project);
            } else {
                return ResultData.fail("项目[" + projectIdOrPath + "]不存在.");
            }
        } catch (Exception e) {
            LOG.error("获取git项目异常", e);
            return ResultData.fail("获取git项目异常:" + e.getMessage());
        }
    }

    /**
     * 删除git项目
     *
     * @param projectIdOrPath 项目id
     * @return 返回git项目
     */
    public ResultData<Void> deleteProject(String projectIdOrPath) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ProjectApi projectApi = gitLabApi.getProjectApi();
            projectApi.deleteProject(projectIdOrPath);
            return ResultData.success();
        } catch (Exception e) {
            LOG.error("删除git项目异常", e);
            return ResultData.fail("删除git项目异常:" + e.getMessage());
        }
    }

    /**
     * 添加项目Push Hook
     *
     * @param projectIdOrPath git项目id
     * @return 创建结果
     */
    public ResultData<Void> addProjectHook(String projectIdOrPath, String url) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ProjectApi hooksApi = gitLabApi.getProjectApi();
            hooksApi.addHook(projectIdOrPath, url, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
            return ResultData.success();
        } catch (Exception e) {
            LOG.error("添加项目Push Hook异常", e);
            return ResultData.fail("添加项目Push Hook异常:" + e.getMessage());
        }
    }

    /**
     * 获取项目标签
     *
     * @param projectIdOrPath git项目id
     * @return 创建结果
     */
    public ResultData<List<Tag>> getProjectTags(String projectIdOrPath) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            TagsApi api = gitLabApi.getTagsApi();
            List<Tag> tags = api.getTags(projectIdOrPath);
            return ResultData.success(tags);
        } catch (Exception e) {
            LOG.error("获取项目标签", e);
            return ResultData.fail("获取项目标签" + e.getMessage());
        }
    }

    /**
     * 获取项目用户
     *
     * @param projectIdOrPath git项目id
     * @return 创建结果
     */
    public ResultData<List<ProjectUser>> getProjectUser(String projectIdOrPath) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ProjectApi api = gitLabApi.getProjectApi();
            List<ProjectUser> projectUsers = api.getProjectUsers(projectIdOrPath);
            return ResultData.success(projectUsers);
        } catch (Exception e) {
            LOG.error("获取项目用户异常", e);
            return ResultData.fail("获取项目用户异常:" + e.getMessage());
        }
    }

    /**
     * 移除项目用户
     *
     * @param projectIdOrPath git项目id
     * @param gitUserIds      用户id
     * @return 操作结果
     */
    public ResultData<Void> removeProjectUser(String projectIdOrPath, Integer... gitUserIds) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            for (Integer gitUserId : gitUserIds) {
                ProjectApi api = gitLabApi.getProjectApi();
                api.removeMember(projectIdOrPath, gitUserId);
            }
            return ResultData.success();
        } catch (Exception e) {
            LOG.error("移除项目用户异常", e);
            return ResultData.fail("移除项目用户异常:" + e.getMessage());
        }
    }

    /**
     * 添加项目用户
     *
     * @param projectIdOrPath git项目id
     * @param accounts        用户账号
     * @return 操作结果
     */
    public ResultData<Integer> addProjectUser(String projectIdOrPath, String... accounts) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            UserApi userApi = gitLabApi.getUserApi();
            for (String account : accounts) {
                Optional<User> optionalUser = userApi.getOptionalUser(account);
                if (optionalUser.isPresent()) {
                    int userId = optionalUser.get().getId();
                    ProjectApi api = gitLabApi.getProjectApi();
                    Member member = api.addMember(projectIdOrPath, userId, AccessLevel.DEVELOPER);
                    if (Objects.isNull(member)) {
                        return ResultData.fail("添加项目用户失败");
                    }
                } else {
                    return ResultData.fail("用户[" + accounts + "]不在gitlab中");
                }
            }
            return ResultData.success();
        } catch (Exception e) {
            LOG.error("添加项目用户异常", e);
            return ResultData.fail("添加项目用户异常:" + e.getMessage());
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
     * 获取指定tag
     *
     * @param gitId   git项目id
     * @param tagName tag名
     * @return tag
     */
    public ResultData<Tag> getProjectTag(String gitId, String tagName) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            TagsApi api = gitLabApi.getTagsApi();
            Tag tag = api.getTag(gitId, tagName);
            if (Objects.nonNull(tag)) {
                return ResultData.success(tag);
            } else {
                return ResultData.fail("不存在标签[" + tagName + "]");
            }
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
    public ResultData<Release> createProjectRelease(String gitId, String name, String tagName, String refTag, String message) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ReleasesApi api = gitLabApi.getReleasesApi();
            ReleaseParams params = new ReleaseParams();
            params.setName(name);
            params.setTagName(tagName);
            params.setRef(refTag);
            params.setDescription(message);
            Release release = api.createRelease(gitId, params);

            return ResultData.success(release);
        } catch (Exception e) {
            LOG.error("创建tag异常", e);
            return ResultData.fail("创建tag异常" + e.getMessage());
        }
    }

    /**
     * 删除项目版本
     *
     * @param projectIdOrPath git项目id
     * @param tagName         tag名
     * @return 创建结果
     */
    public ResultData<Void> deleteProjectRelease(String projectIdOrPath, String tagName) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            ReleasesApi api = gitLabApi.getReleasesApi();
            api.deleteRelease(projectIdOrPath, tagName);
            return ResultData.success();
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
    public ResultData<String> createGroup(String name, String path, String remark) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();
            Group group = new Group();
            group.setName(name);
            group.setPath(path.toLowerCase());
            group.setDescription(remark);
            group = groupApi.addGroup(group);
            if (Objects.nonNull(group)) {
                return ResultData.success(String.valueOf(group.getId()));
            } else {
                return ResultData.fail("创建gitlab 组失败");
            }
        } catch (GitLabApiException e) {
            LOG.error("创建gitlab 组异常", e);
            return ResultData.fail("创建gitlab 组异常:" + e.getMessage());
        }
    }

    /**
     * 修改gitlab 组
     *
     * @return 返回修改的gitlab的组实例
     */
    public ResultData<Void> updateGroup(String groupIdOrPath, String remark) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();
            ResultData<Group> resultData = getGroup(groupIdOrPath);
            if (resultData.successful()) {
                Group group = resultData.getData();
                group.setDescription(remark);
                group = groupApi.updateGroup(group);
                return ResultData.success();
            } else {
                return ResultData.fail(resultData.getMessage());
            }
        } catch (GitLabApiException e) {
            LOG.error("修改gitlab 组异常", e);
            return ResultData.fail("修改gitlab 组异常:" + e.getMessage());
        }
    }

    /**
     * 删除gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<Void> deleteGroup(String groupIdOrPath) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();

            groupApi.deleteGroup(groupIdOrPath);
            return ResultData.success();
        } catch (GitLabApiException e) {
            LOG.error("删除gitlab 组异常", e);
            return ResultData.fail("删除gitlab 组异常:" + e.getMessage());
        }
    }

    /**
     * 获取gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<Group> getGroup(String groupIdOrPath) {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();

            Group group = groupApi.getGroup(groupIdOrPath);
            return ResultData.success(group);
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab群组异常", e);
            return ResultData.fail("获取gitlab群组异常:" + e.getMessage());
        }
    }

    /**
     * 获取所有gitlab 组
     *
     * @return 返回创建的gitlab的组实例
     */
    public ResultData<List<Group>> getGroups() {
        try (GitLabApi gitLabApi = this.getGitLabApi()) {
            GroupApi groupApi = gitLabApi.getGroupApi();
            return ResultData.success(groupApi.getGroups());
        } catch (GitLabApiException e) {
            LOG.error("获取gitlab群组异常", e);
            return ResultData.fail("获取gitlab群组异常:" + e.getMessage());
        }
    }
}
