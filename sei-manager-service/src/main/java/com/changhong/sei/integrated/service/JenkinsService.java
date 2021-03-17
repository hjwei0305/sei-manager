package com.changhong.sei.integrated.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.cicd.dto.BuildStatus;
import com.changhong.sei.util.EnumUtils;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.ConsoleLog;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
    public JenkinsServer getJenkinsServer() {
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
     * 检查Jenkins任务是否存在
     *
     * @param jobName 任务名
     * @return 返回Jenkins任务
     */
    public boolean checkJobExist(String jobName) {
        try {
            JobWithDetails job = getJob(jobName);

            return Objects.nonNull(job);
        } catch (Exception e) {
            throw new RuntimeException("检查Jenkins任务是否存在异常: " + ExceptionUtils.getRootCauseMessage(e), e);
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
            if (Objects.isNull(details)) {
                return ResultData.fail("Jenkins任务[" + jobName + "]不存在.");
            }
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
                } else {
                    return ResultData.fail("构建任务[" + jobName + "]还未开始构建.");
                }
            } else {
                return ResultData.fail("构建任务[" + jobName + "]不存在");
            }
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

            ConsoleLog log = getConsoleOutputText(build, 0);
            System.out.println(log.getConsoleLog());

            return ResultData.success(build);
        } catch (IOException e) {
            LOG.error("获取Jenkins任务异常", e);
            return ResultData.fail("构建Jenkins的[" + jobName + "]任务异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 获取最后一次构建的状态
     *
     * @param jobName 任务名
     * @return 返回结果
     */
    public ResultData<BuildStatus> getLastBuildStatus(String jobName) {
        try (JenkinsServer server = getJenkinsServer()) {
            JobWithDetails details = server.getJob(jobName);
            BuildWithDetails build = details.getLastBuild().details();

            BuildStatus status = EnumUtils.getEnum(BuildStatus.class, build.getResult().name());

            return ResultData.success(status);
        } catch (IOException e) {
            LOG.error("获取Jenkins的[" + jobName + "]任务最后一次构建的状态异常", e);
            return ResultData.fail("获取Jenkins的[" + jobName + "]任务状态异常: " + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    public ConsoleLog getConsoleOutputText(BuildWithDetails build, int bufferOffset) throws IOException {
        List<NameValuePair> formData = new ArrayList<>();
        formData.add(new BasicNameValuePair("start", Integer.toString(bufferOffset)));
        String path = build.getUrl() + "logText/progressiveText";
        HttpResponse httpResponse = build.getClient().post_form_with_result(path, formData, true);

        Header moreDataHeader = httpResponse.getFirstHeader(BuildWithDetails.MORE_DATA_HEADER);
        Header textSizeHeader = httpResponse.getFirstHeader(BuildWithDetails.TEXT_SIZE_HEADER);
        String response = EntityUtils.toString(httpResponse.getEntity());
        boolean hasMoreData = false;
        if (moreDataHeader != null) {
            hasMoreData = Boolean.TRUE.toString().equals(moreDataHeader.getValue());
        }
        int currentBufferSize = bufferOffset;
        if (textSizeHeader != null) {
            try {
                currentBufferSize = Integer.parseInt(textSizeHeader.getValue());
            } catch (NumberFormatException e) {
                LOG.warn("Cannot parse buffer size for job {} build {}. Using current offset!", build.getDisplayName(), build.getNumber());
            }
        }
        return new ConsoleLog(response, hasMoreData, currentBufferSize);
    }

}
