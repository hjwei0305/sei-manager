package com.changhong.sei.ge.dto;

import com.changhong.sei.common.ObjectType;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目用户DTO
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "项目用户DTO")
public class ProjectUserDto extends BaseEntityDto {
    private static final long serialVersionUID = 630890453379821715L;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String account;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 对象id
     */
    @ApiModelProperty(value = "对象id")
    private String objectId;
    /**
     * 对象id
     */
    @ApiModelProperty(value = "对象名")
    private String objectName;

    @ApiModelProperty(value = "对象类型: APPLICATION, MODULE")
    private ObjectType type = ObjectType.MODULE;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }
}