package com.changhong.sei.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.io.Serializable;

/**
 * 实现功能：es的httpClient连接池配置
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 00:56
 */
@ConfigurationProperties(prefix = "sei.elasticsearch.rest-pool")
@RefreshScope
public class RestClientPoolProperties implements Serializable {
    private static final long serialVersionUID = 8127887133113338096L;
    /**
     * 链接建立超时时间
     */
    private Integer connectTimeOut = 1000;
    /**
     * 等待数据超时时间
     */
    private Integer socketTimeOut = 30000;
    /**
     * 连接池获取连接的超时时间
     */
    private Integer connectionRequestTimeOut = 500;
    /**
     * 最大连接数
     */
    private Integer maxConnectNum = 50;
    /**
     * 最大路由连接数
     */
    private Integer maxConnectPerRoute = 10;

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Integer getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public Integer getConnectionRequestTimeOut() {
        return connectionRequestTimeOut;
    }

    public void setConnectionRequestTimeOut(Integer connectionRequestTimeOut) {
        this.connectionRequestTimeOut = connectionRequestTimeOut;
    }

    public Integer getMaxConnectNum() {
        return maxConnectNum;
    }

    public void setMaxConnectNum(Integer maxConnectNum) {
        this.maxConnectNum = maxConnectNum;
    }

    public Integer getMaxConnectPerRoute() {
        return maxConnectPerRoute;
    }

    public void setMaxConnectPerRoute(Integer maxConnectPerRoute) {
        this.maxConnectPerRoute = maxConnectPerRoute;
    }
}
