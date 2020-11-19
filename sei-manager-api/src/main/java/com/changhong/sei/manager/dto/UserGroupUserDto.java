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
@ApiModel(description = "用户组分配用户DTO")
public class UserGroupUserDto extends BaseEntityDto implements RelationEntityDto<UserGroupDto, UserDto> {
    private static final long serialVersionUID = -4680935937139022614L;
    /**
     * 用户组DTO
     */
    @ApiModelProperty(value = "用户组DTO", required = true)
    private UserGroupDto parent;
    /**
     * 用户DTO
     */
    @ApiModelProperty(value = "用户DTO", required = true)
    private UserDto child;

    @Override
    public UserGroupDto getParent() {
        return parent;
    }

    @Override
    public void setParent(UserGroupDto parent) {
        this.parent = parent;
    }

    @Override
    public UserDto getChild() {
        return child;
    }

    @Override
    public void setChild(UserDto child) {
        this.child = child;
    }
}
