package com.changhong.sei.integrated.service;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.integrated.vo.ProjectTagVo;
import com.changhong.sei.integrated.vo.ProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

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
     * 创建标签
     *
     * @param projectTag tag
     * @return 创建结果
     */
    @SuppressWarnings("unchecked")
    public ResultData<ProjectTagVo> createProjectTag(ProjectTagVo projectTag) {
        String url = host.concat("/project/createTag");
        ResultData<HashMap<String, Object>> resultData = restTemplate.postByUrl(url, ResultData.class, projectTag);
        if (LOG.isInfoEnabled()) {
            LOG.info("创建git标签: {}", JsonUtils.toJson(resultData));
        }
        if (resultData.successful()) {
            HashMap<String, Object> data = resultData.getData();
            projectTag.setTarget(String.valueOf(data.get("target")));
            HashMap<String, Object> commitData = (HashMap<String, Object>) data.get("commit");
            projectTag.setCommitId(String.valueOf(commitData.get("id")));
            return ResultData.success(projectTag);
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }
}
