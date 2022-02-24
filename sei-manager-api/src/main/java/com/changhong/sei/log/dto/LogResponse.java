package com.changhong.sei.log.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-09-22 00:15
 */
@ApiModel(description = "日志")
public class LogResponse implements Serializable {
    private static final long serialVersionUID = -7357129504573889779L;
    public static final String SEARCH_TRACE_ID = "traceId";
    public static final String SEARCH_TIMESTAMP = "timestamp";
    public static final String SEARCH_ENV = "env";
    public static final String SEARCH_LEVEL = "level";
    public static final String SEARCH_MESSAGE = "message";

    /**
     * 时间戳
     */
    @ApiModelProperty(notes = "时间戳")
    private String timestamp;

    /**
     * 主键标识
     */
    @ApiModelProperty(notes = "主键标识")
    private String id;

    /**
     * 跟踪id
     */
    @ApiModelProperty(notes = "跟踪id")
    private String traceId;

    /**
     * 服务调用链
     */
    @ApiModelProperty(notes = "服务调用链")
    private String tracePath;

    /**
     * 环境
     */
    @ApiModelProperty(notes = "环境")
    private String env;
    /**
     * 应用代码
     */
    @ApiModelProperty(notes = "应用代码")
    private String serviceName;
    /**
     * 日志类
     */
    @ApiModelProperty(notes = "日志类")
    private String logger;
    /**
     * 日志等级
     */
    @ApiModelProperty(notes = "日志等级")
    private String level;
    /**
     * 日志消息
     */
    @ApiModelProperty(notes = "日志消息")
    private String message;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTracePath() {
        return tracePath;
    }

    public void setTracePath(String tracePath) {
        this.tracePath = tracePath;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
