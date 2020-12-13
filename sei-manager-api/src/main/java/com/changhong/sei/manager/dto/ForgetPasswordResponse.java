package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.annotation.Desensitization;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-18 16:44
 */
@ApiModel(description = "忘记密码响应")
public class ForgetPasswordResponse implements Serializable {

    private static final long serialVersionUID = 3614613734469635633L;

    /**
     * 找回密码签名
     */
    @ApiModelProperty(value = "找回密码签名")
    private String sign;
    @Desensitization(Desensitization.DesensitizationType.EMAIL)
    @ApiModelProperty(value = "邮箱(脱敏)")
    private String email;

    public ForgetPasswordResponse() {

    }
    public ForgetPasswordResponse(String sign, String email) {
        this.sign = sign;
        this.email = email;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
