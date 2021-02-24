package com.changhong.sei.config.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 发布历史(ConfReleaseHistory)DTO类
 *
 * @author sei
 * @since 2021-02-22 21:44:13
 */
@ApiModel(description = "发布历史DTO")
public class ReleaseHistoryDto extends BaseEntityDto {
    private static final long serialVersionUID = -75111535518176893L;
    /**
     * 应用服务代码
     */
    @ApiModelProperty(value = "应用服务代码")
    private String appCode;
    /**
     * 环境代码
     */
    @ApiModelProperty(value = "环境代码")
    private String envCode;
    /**
     * 配置键
     */
    @ApiModelProperty(value = "配置键")
    private String key;
    /**
     * 配置值
     */
    @ApiModelProperty(value = "配置值")
    private String value;
    /**
     * 发布人账号
     */
    @ApiModelProperty(value = "发布人账号")
    private String publisherAccount;
    /**
     * 发布人姓名
     */
    @ApiModelProperty(value = "发布人姓名")
    private String publisherName;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date publishDate;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPublisherAccount() {
        return publisherAccount;
    }

    public void setPublisherAccount(String publisherAccount) {
        this.publisherAccount = publisherAccount;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

}