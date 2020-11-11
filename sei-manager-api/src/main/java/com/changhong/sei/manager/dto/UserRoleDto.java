package com.changhong.sei.manager.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 用户分配的功能角色DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:22
 */
@ApiModel(description = "用户分配的功能角色DTO")
public class UserRoleDto extends BaseEntityDto implements RelationEntityDto<UserDto, RoleDto> {
    private static final long serialVersionUID = -4680935937139022614L;
    /**
     * 用户DTO
     */
    @ApiModelProperty(value = "用户DTO", required = true)
    private UserDto parent;
    /**
     * 角色DTO
     */
    @ApiModelProperty(value = "角色DTO", required = true)
    private RoleDto child;

    @Override
    public UserDto getParent() {
        return parent;
    }

    @Override
    public void setParent(UserDto parent) {
        this.parent = parent;
    }

    @Override
    public RoleDto getChild() {
        return child;
    }

    @Override
    public void setChild(RoleDto child) {
        this.child = child;
    }
}
