package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-10 16:49
 */
@ApiModel(description = "用户DTO")
public class UserDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5634252166364145856L;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "账号")
    private String account;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 状态，启用-true，禁用-false
     */
    @ApiModelProperty(value = "状态，启用-true，禁用-false")
    private Boolean status = Boolean.TRUE;
    /**
     * 是否是管理员
     */
    @ApiModelProperty(value = "是否是管理员")
    private Boolean isAdmin = Boolean.FALSE;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
