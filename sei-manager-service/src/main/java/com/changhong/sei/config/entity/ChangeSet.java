package com.changhong.sei.config.entity;

import com.changhong.sei.config.dto.ChangeType;
import com.changhong.sei.config.dto.ConfigCategory;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 配置变更集(ConfChangeset)实体类
 *
 * @author sei
 * @since 2021-02-22 21:43:55
 */
@Entity
@Table(name = "conf_changeset")
@DynamicInsert
@DynamicUpdate
public class ChangeSet extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -27702112857964781L;
    /**
     * 分类 general通用 app应用
     */
    @Column(name = "category")
    private ConfigCategory category;
    /**
     * 配置id
     */
    @Column(name = "config_id")
    private String configId;
    /**
     * 变更类型 create-新增 modify-编辑 delete-删除
     */
    @Column(name = "change_type")
    private ChangeType changeType;
    /**
     * 配置键
     */
    @Column(name = "key_code")
    private String key;
    /**
     * 变更前
     */
    @Column(name = "before_value")
    private String beforeValue;
    /**
     * 变更后
     */
    @Column(name = "after_value")
    private String afterValue;
    /**
     * 变更人账号
     */
    @Column(name = "changer_account")
    private String changerAccount;
    /**
     * 变更人姓名
     */
    @Column(name = "changer_name")
    private String changerName;
    /**
     * 变更时间
     */
    @Column(name = "change_date")
    private Date changeDate;

    public ConfigCategory getCategory() {
        return category;
    }

    public void setCategory(ConfigCategory category) {
        this.category = category;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

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