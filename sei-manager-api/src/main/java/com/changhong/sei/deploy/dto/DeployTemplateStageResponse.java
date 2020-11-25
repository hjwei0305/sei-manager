package com.changhong.sei.deploy.dto;

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
public class DeployTemplateStageResponse extends BaseEntityDto {
    private static final long serialVersionUID = -14319293657195114L;

    /**
     * 阶段名称
     */
    @ApiModelProperty(value = "阶段名称")
    private String name;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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