package com.changhong.sei.config.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 配置比较DTO类
 *
 * @author sei
 * @since 2021-02-22 21:44:00
 */
@ApiModel(description = "配置比较DTO")
public class ConfigCompareResponse implements Serializable {
    private static final long serialVersionUID = 555063211162189746L;

    /**
     * 变更类型 create-新增 modify-编辑 delete-删除
     */
    @ApiModelProperty(value = "变更类型:create-新增 modify-编辑 delete-删除")
    private ChangeType changeType;
    /**
     * 配置键
     */
    @ApiModelProperty(value = "配置键")
    private String key;
    /**
     * 变更前
     */
    @ApiModelProperty(value = "发布前")
    private String beforeValue;
    /**
     * 变更后
     */
    @ApiModelProperty(value = "发布后")
    private String afterValue;
    /**
     * 变更人账号
     */
    @ApiModelProperty(value = "发布账号")
    private String publisherAccount;
    /**
     * 变更人姓名
     */
    @ApiModelProperty(value = "发布姓名")
    private String publisherName;
    /**
     * 变更时间
     */
    @ApiModelProperty(value = "发布时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
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

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }
}