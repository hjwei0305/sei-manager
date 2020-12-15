package com.changhong.sei.deploy.websocket;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.deploy.dto.TemplateType;
import com.changhong.sei.deploy.entity.DeployTemplate;
import com.changhong.sei.deploy.entity.ReleaseRecord;
import com.changhong.sei.deploy.service.DeployTemplateService;
import com.changhong.sei.deploy.service.ReleaseRecordService;
import com.changhong.sei.deploy.websocket.config.MyEndpointConfigure;
import com.changhong.sei.integrated.service.JenkinsService;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.ConsoleLog;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-03-08 17:18
 */
@ServerEndpoint(value = "/websocket/buildLog/{id}", configurator = MyEndpointConfigure.class)
public class WebsocketServer {
    private final static Logger LOG = LoggerFactory.getLogger(WebsocketServer.class);

    /**
     * 连接集合
     */
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();
    @Autowired
    private ReleaseRecordService releaseRecordService;
    @Autowired
    private DeployTemplateService templateService;
    @Autowired
    private JenkinsService jenkinsService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        LOG.debug("发布记录id[{}]", id);

        //添加到集合中
        SESSION_MAP.put(session.getId(), session);

        ReleaseRecord releaseRecord = releaseRecordService.findOne(id);
        if (Objects.isNull(releaseRecord)) {
            LOG.error("发布记录id[{}]不存在", id);
            send(session, "发布记录id[" + id + "]不存在.");
            return;
        }

        String jobName;
        if (StringUtils.equals(TemplateType.DEPLOY.name(), releaseRecord.getType())) {
            jobName = releaseRecord.getJobName();
        } else {
            // 检查
            ResultData<DeployTemplate> resultData = templateService.getPublishTemplate(releaseRecord.getType());
            if (resultData.failed()) {
                LOG.error("发布记录id[{}]对应的JobName不存在, Type[{}]", id, releaseRecord.getType());
                send(session, "发布记录id[" + id + "]对应的JobName不存在, Type[" + releaseRecord.getType() + "].");
                return;
            }
            // @see templateService#syncJenkinsJob
            DeployTemplate deployTemplate = resultData.getData();
            jobName = deployTemplate.getName();
        }
        int buildNumber = releaseRecord.getBuildNumber();
        try (JenkinsServer jenkinsServer = jenkinsService.getJenkinsServer()) {
            JobWithDetails details = jenkinsServer.getJob(jobName);
            if (Objects.isNull(details)) {
                LOG.debug("{}任务不存在,开始循环检查任务.", jobName);
                boolean isContinue = true;
                while (isContinue) {
                    // 睡眠10s
                    Thread.sleep(10000);
                    details = jenkinsServer.getJob(jobName);
                    if (Objects.nonNull(details)) {
                        isContinue = false;
                    }
                }
            }

            LOG.debug("{}任务是否在队列中:{}", jobName, details.isInQueue());
            Build build = details.getBuildByNumber(buildNumber);
            while (Objects.isNull(build)) {
                //noinspection BusyWait
                Thread.sleep(10000);
                details = jenkinsServer.getJob(jobName);
                build = details.getBuildByNumber(buildNumber);
            }
            LOG.debug("{}任务开始构建...", jobName);
            BuildWithDetails withDetails = build.details();

            // 当前日志
            ConsoleLog currentLog = jenkinsService.getConsoleOutputText(withDetails, 0);

            if (LOG.isDebugEnabled()) {
                LOG.debug("{}任务是否存在更多日志: {}", jobName, currentLog.getHasMoreData());
            }
            // 输出当前获取日志信息
            send(session, currentLog.getConsoleLog());
            // 检测是否还有更多日志,如果是则继续循环获取
            while (currentLog.getHasMoreData()) {
                // 获取最新日志信息
                currentLog = jenkinsService.getConsoleOutputText(withDetails, currentLog.getCurrentBufferSize());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{}任务是否存在更多日志: {}", jobName, currentLog.getHasMoreData());
                }
                // 输出最新日志
                send(session, currentLog.getConsoleLog());
                // 睡眠3s
                //noinspection BusyWait
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            LOG.error("websocket获取构建实时日志异常:" + ExceptionUtils.getRootCauseMessage(e), e);
            // 输出最新日志
            send(session, "websocket获取构建实时日志异常:" + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //从集合中删除
        SESSION_MAP.remove(session.getId());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {

    }

    /**
     * 封装一个send方法，发送消息到前端
     */
    private void send(Session session, String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("发送消息: {}", message);
        }
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
