package com.changhong.sei.integrated.service;

import com.changhong.sei.core.dto.ResultData;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

/**
 * 实现功能：
 * https://blog.csdn.net/qq_32641153/article/details/94230465
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 14:31
 */
@Service
public class JenkinsService {
    private static final Logger LOG = LoggerFactory.getLogger(JenkinsService.class);
    /**
     * 认证header
     */
    private static final String HEADER_TOKEN_KEY = "PRIVATE-TOKEN";
    /**
     * jenkins服务地址
     */
    @Value("${sei.jenkins.host}")
    private String host;
    /**
     * jenkins用户名
     */
    @Value("${sei.jenkins.username}")
    private String username;
    /**
     * jenkins密码
     */
    @Value("${sei.jenkins.password}")
    private String password;

    /**
     * 注入jenkinsHttpClient对象
     */
    private JenkinsHttpClient getJenkinsHttpClient() throws URISyntaxException {
        return new JenkinsHttpClient(new URI(host), username, password);
    }

    /**
     * 注入jenkinsServer对象
     */
    private JenkinsServer getJenkinsServer() {
        try {
            JenkinsHttpClient jenkinsHttpClient = getJenkinsHttpClient();
            return new JenkinsServer(jenkinsHttpClient);
        } catch (URISyntaxException e) {
            throw new RuntimeException("创建JenkinsServer异常: " + ExceptionUtils.getRootCauseMessage(e), e);
        }
    }

    /**
     * 创建Jenkins任务
     *
     * @param jobName 任务名
     * @param jobXml  任务xml配置
     * @return 返回Jenkins任务
     */
    public ResultData<Void> createJob(String jobName, String jobXml) {
        try (JenkinsServer server = getJenkinsServer()) {
            server.createJob(jobName, jobXml, Boolean.TRUE);
            return ResultData.success();
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("创建Jenkins任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 修改Jenkins任务
     *
     * @param jobName 任务名
     * @param jobXml  任务xml配置
     * @return 返回Jenkins任务
     */
    public ResultData<Void> updateJob(String jobName, String jobXml) {
        try (JenkinsServer server = getJenkinsServer()) {
            server.updateJob(jobName, jobXml, Boolean.TRUE);
            return ResultData.success();
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("创建Jenkins任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 删除Jenkins任务
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    public ResultData<Void> deleteJob(String jobName) {
        try (JenkinsServer server = getJenkinsServer()) {
            server.deleteJob(jobName, Boolean.TRUE);
            return ResultData.success();
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("创建Jenkins任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 获取Jenkins任务
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    private JobWithDetails getJob(String jobName) {
        try (JenkinsServer server = getJenkinsServer()) {
            return server.getJob(jobName);
        } catch (IOException e) {
            throw new RuntimeException("获取Jenkins任务异常: " + ExceptionUtils.getRootCauseMessage(e), e);
        }
    }

    /**
     * 构建指定的无参数Jenkins任务
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    public ResultData<Integer> buildJob(String jobName) {
        try (JenkinsServer server = getJenkinsServer()) {
            JobWithDetails details = server.getJob(jobName);
            // 获取构建任务号
            int buildNumber = details.getNextBuildNumber();
            // 构建
            details.build(Boolean.TRUE);

            return ResultData.success(buildNumber);
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("构建Jenkins的[" + jobName + "]任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 构建指定的带参数Jenkins任务
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    public ResultData<Integer> buildJob(String jobName, Map<String, String> params) {
        try (JenkinsServer server = getJenkinsServer()) {
            JobWithDetails details = server.getJob(jobName);
            // 获取构建任务号
            int buildNumber = details.getNextBuildNumber();
            // 构建
            details.build(params, Boolean.TRUE);

            return ResultData.success(buildNumber);
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("构建Jenkins的[" + jobName + "]任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 根据构建号指定的Jenkins任务
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    public ResultData<BuildWithDetails> getBuildDetails(String jobName, int buildNumber) {
        try (JenkinsServer server = getJenkinsServer()) {
            JobWithDetails jobDetails = server.getJob(jobName);
            if (Objects.nonNull(jobDetails)) {
                Build build = jobDetails.getBuildByNumber(buildNumber);
                if (Objects.nonNull(build)) {
                    BuildWithDetails buildDetails = build.details();
                    return ResultData.success(buildDetails);
                }
            }
            return ResultData.fail("构建任务[" + jobName + "]不存在或存在错误.");
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("构建Jenkins的[" + jobName + "]任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 根据构建号指定的Jenkins任务
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    public ResultData<BuildWithDetails> getBuildActiveLog(String jobName) {
        try (JenkinsServer server = getJenkinsServer()) {
            JobWithDetails details = server.getJob(jobName);
            BuildWithDetails build = details.getLastBuild().details();

            return ResultData.success(build);
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("构建Jenkins的[" + jobName + "]任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

}
