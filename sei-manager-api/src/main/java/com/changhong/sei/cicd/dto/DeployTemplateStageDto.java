package com.changhong.sei.cicd.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.RelationEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部署模板阶段关系表(DeployTemplateStage)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:08
 */
@ApiModel(description = "部署模板阶段关系表DTO")
public class DeployTemplateStageDto extends BaseEntityDto implements RelationEntityDto<DeployTemplateDto, DeployStageDto> {
    private static final long serialVersionUID = -14319293657195114L;

    /**
     * 用户组DTO
     */
    @ApiModelProperty(value = "模板DTO", required = true)
    private DeployTemplateDto parent;
    /**
     * 用户DTO
     */
    @ApiModelProperty(value = "阶段DTO", required = true)
    private DeployStageDto child;
    /**
     * 脚本
     */
    @ApiModelProperty(value = "脚本")
    private String playscript;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer rank = 0;

    @Override
    public DeployTemplateDto getParent() {
        return parent;
    }

    @Override
    public void setParent(DeployTemplateDto parent) {
        this.parent = parent;
    }

    @Override
    public DeployStageDto getChild() {
        return child;
    }

    @Override
    public void setChild(DeployStageDto child) {
        this.child = child;
    }

    public String getPlayscript() {
        return playscript;
    }

    public void setPlayscript(String playscript) {
        this.playscript = playscript;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}