package com.changhong.sei.config.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 配置变更集(ConfChangeset)DTO类
 *
 * @author sei
 * @since 2021-02-22 21:44:00
 */
@ApiModel(description = "配置变更集DTO")
public class ChangeSetDto extends BaseEntityDto {
    private static final long serialVersionUID = 555063211162189746L;
    /**
     * 分类 global全局 app应用
     */
    @ApiModelProperty(value = "分类global全局app应用")
    private String category;
    /**
     * 配置id
     */
    @ApiModelProperty(value = "配置id")
    private String configId;
    /**
     * 变更类型 ctreate-新增 modify-编辑 delete-删除
     */
    @ApiModelProperty(value = "变更类型ctreate-新增modify-编辑delete-删除")
    private String changeType;
    /**
     * 配置键
     */
    @ApiModelProperty(value = "配置键")
    private String key;
    /**
     * 变更前
     */
    @ApiModelProperty(value = "变更前")
    private String beforeValue;
    /**
     * 变更后
     */
    @ApiModelProperty(value = "变更后")
    private String afterValue;
    /**
     * 变更人账号
     */
    @ApiModelProperty(value = "变更人账号")
    private String changerAccount;
    /**
     * 变更人姓名
     */
    @ApiModelProperty(value = "变更人姓名")
    private String changerName;
    /**
     * 变更时间
     */
    @ApiModelProperty(value = "变更时间")
    private Date changeDate;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
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

    public String getChangerAccount() {
        return changerAccount;
    }

    public void setChangerAccount(String changerAccount) {
        this.changerAccount = changerAccount;
    }

    public String getChangerName() {
        return changerName;
    }

    public void setChangerName(String changerName) {
        this.changerName = changerName;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

}