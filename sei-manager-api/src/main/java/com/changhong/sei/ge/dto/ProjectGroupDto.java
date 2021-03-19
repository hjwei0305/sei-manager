package com.changhong.sei.ge.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 实现功能: 项目组DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:57
 */
@ApiModel(description = "项目组DTO")
public class ProjectGroupDto extends BaseEntityDto implements TreeEntity<ProjectGroupDto> {
    private static final long serialVersionUID = 891649039705760108L;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * 代码路径
     */
    @ApiModelProperty(value = "代码路径")
    private String codePath;

    /**
     * 名称路径
     */
    @ApiModelProperty(value = "名称路径")
    private String namePath;

    /**
     * 层级
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "层级")
    private Integer nodeLevel = 0;

    /**
     * 排序号
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "排序号")
    private Integer rank = 0;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "父节点代码")
    private String parentCode;
    @ApiModelProperty(value = "描述说明")
    private String remark;

    /**
     * 子节点列表
     */
    @ApiModelProperty(value = "子节点列表")
    private List<ProjectGroupDto> children;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCodePath() {
        return codePath;
    }

    @Override
    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    @Override
    public String getNamePath() {
        return namePath;
    }

    @Override
    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    @Override
    public Integer getNodeLevel() {
        return nodeLevel;
    }

    @Override
    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public List<ProjectGroupDto> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<ProjectGroupDto> children) {
        this.children = children;
    }
}
