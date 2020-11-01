package com.changhong.sei.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-09-22 00:15
 */
@ApiModel(description = "日志明细")
public class LogDetailResponse extends LogResponse implements Serializable {
    private static final long serialVersionUID = -7357129504573889779L;
    /**
     * 主机
     */
    @ApiModelProperty(notes = "主机")
    private String host;
    /**
     * 版本
     */
    @ApiModelProperty(notes = "版本")
    private String version;
    /**
     * 用户id
     */
    @ApiModelProperty(notes = "用户id")
    private String userId;
    /**
     * 用户账号
     */
    @ApiModelProperty(notes = "用户账号")
    private String account;

    /**
     * 用户名
     */
    @ApiModelProperty(notes = "用户名")
    private String userName;
    /**
     * 堆栈信息
     */
    @ApiModelProperty(notes = "堆栈信息")
    private String stackTrace;

    @ApiModelProperty(notes = "java类")
    private String className;
    @ApiModelProperty(notes = "方法")
    private String methodName;
    @ApiModelProperty(notes = "参数")
    private String args;
    @ApiModelProperty(notes = "URL")
    private String requestURI;
    @ApiModelProperty(notes = "用户代理")
    private String userAgent;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
