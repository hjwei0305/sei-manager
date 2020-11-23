package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
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
public class Node extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 954549891503436485L;
    /**
     * 阶段名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 环境
     */
    @Column(name = "env")
    private String env;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}