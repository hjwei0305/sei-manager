package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户表(SecUser)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Entity
@Table(name = "sec_user")
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -57676660477087546L;
    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;
    /**
     * 手机
     */
    @Column(name = "phone")
    private String phone;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 状态，启用-1，禁用-0
     */
    @Column(name = "status")
    private Boolean status = Boolean.TRUE;
    /**
     * 是否是管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin = Boolean.FALSE;
    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    private Long createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Long updateTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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