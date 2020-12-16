package com.changhong.sei.deploy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程类型实例(FlowInstance)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:49
 */
@ApiModel(description = "流程类型实例DTO")
public class FlowInstanceDto implements Serializable {
    private static final long serialVersionUID = -13224391892073412L;
    /**
     * 流程类型代码
     */
    @ApiModelProperty(value = "流程类型代码")
    private String code;
    /**
     * 流程类型名称
     */
    @ApiModelProperty(value = "流程类型名称")
    private String name;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;
    /**
     * 关联值
     */
    @ApiModelProperty(value = "关联值")
    private String relation;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 是否发布
     */
    @ApiModelProperty(value = "是否发布")
    private Boolean published;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间", example = "2020-01-14 22:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedTime;
    /**
     * 发布人账号
     */
    @ApiModelProperty(value = "发布人账号")
    private String publishedAccount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDateTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalDateTime publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getPublishedAccount() {
        return publishedAccount;
    }

    public void setPublishedAccount(String publishedAccount) {
        this.publishedAccount = publishedAccount;
    }
}