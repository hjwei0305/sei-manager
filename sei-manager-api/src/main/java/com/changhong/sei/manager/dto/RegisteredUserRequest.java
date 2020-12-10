package com.changhong.sei.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-09 13:42
 */
@ApiModel(description = "用户注册")
public class RegisteredUserRequest implements Serializable {
    private static final long serialVersionUID = -8854823318075545788L;

    @NotBlank
    @ApiModelProperty(value = "请求id", required = true)
    private String reqId;
    @NotBlank
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;
    @NotBlank
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "登录页面地址", required = true)
    private String loginUrl;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
