package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程类型版本(FlowDeTypeVersion)DTO类
 *
 * @author sei
 * @since 2020-12-16 10:53:49
 */
@ApiModel(description = "流程类型版本DTO")
public class FlowTypeVersionDto implements Serializable {
    private static final long serialVersionUID = -13224391892073412L;
    /**
     * 流程类型id
     */
    @ApiModelProperty(value = "流程类型id")
    private String typeId;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;
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
//    /**
//     * 名称
//     */
//    @ApiModelProperty(value = "名称")
//    private String name;
//    /**
//     * 描述说明
//     */
//    @ApiModelProperty(value = "描述说明")
//    private String remark;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }

}