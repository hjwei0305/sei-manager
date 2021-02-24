package com.changhong.sei.config.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 发布历史(ConfReleaseHistory)实体类
 *
 * @author sei
 * @since 2021-02-22 21:44:09
 */
@Entity
@Table(name = "conf_release_history")
@DynamicInsert
@DynamicUpdate
public class ReleaseHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 941750274919012375L;
    /**
     * 应用服务代码
     */
    @Column(name = "app_code")
    private String appCode;
    /**
     * 环境代码
     */
    @Column(name = "env_code")
    private String envCode;
    /**
     * 配置键
     */
    @Column(name = "key")
    private String key;
    /**
     * 配置值
     */
    @Column(name = "value")
    private String value;
    /**
     * 发布人账号
     */
    @Column(name = "publisher_account")
    private String publisherAccount;
    /**
     * 发布人姓名
     */
    @Column(name = "publisher_name")
    private String publisherName;
    /**
     * 发布时间
     */
    @Column(name = "publish_date")
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