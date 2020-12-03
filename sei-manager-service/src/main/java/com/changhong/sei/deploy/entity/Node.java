package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 服务器节点(Node)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Entity
@Table(name = "node")
@DynamicInsert
@DynamicUpdate
public class Node extends BaseAuditableEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = 954549891503436485L;
    public static final String FIELD_ENV_CODE = "envCode";
    /**
     * 阶段名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 环境
     */
    @Column(name = "env_code")
    private String envCode;
    /**
     * 环境
     */
    @Column(name = "env_name")
    private String envName;
    /**
     * 凭证id
     */
    @Column(name = "certificate_id")
    private String certificateId;
    /**
     * 凭证名称
     */
    @Column(name = "certificate_name")
    private String certificateName;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}