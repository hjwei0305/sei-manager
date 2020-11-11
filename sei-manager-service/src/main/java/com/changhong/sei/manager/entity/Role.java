package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色表(SecRole)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:30
 */
@Entity
@Table(name = "sec_role")
@DynamicInsert
@DynamicUpdate
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -60933176202135691L;
    /**
     * 角色名
     */
    @Column(name = "name")
    private String name;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Long updateTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

}