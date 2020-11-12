package com.changhong.sei.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-12 10:28
 */
@ApiModel(description = "密码更新请求")
public class UpdatePasswordRequest implements Serializable {
    private static final long serialVersionUID = -8480252755367258291L;

    /**
     * 用户账号\邮箱\手机号
     */
    @ApiModelProperty(notes = "用户名或邮箱或手机号")
    @NotBlank(message = "用户名不能为空")
    private String account;
    /**
     * 旧密码
     */
    @ApiModelProperty(notes = "旧密码")
    @NotBlank(message = "旧密码不能为空(MD5散列).")
    private String oldPassword;
    /**
     * 新密码
     */
    @ApiModelProperty(notes = "新密码")
    @NotBlank(message = "新密码不能为空(MD5散列).")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
