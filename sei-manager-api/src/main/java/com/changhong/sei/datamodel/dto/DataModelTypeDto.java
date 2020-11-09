package com.changhong.sei.datamodel.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 数据模型分类(DataModelType)DTO类
 *
 * @author sei
 * @since 2020-07-28 23:23:51
 */
@ApiModel(description = "数据模型分类DTO")
public class DataModelTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = 856644997474097915L;
    /**
     * 标识符
     */
    @ApiModelProperty(value = "标识符")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 父节点Id
     */
    @ApiModelProperty(value = "父节点Id")
    private String parentId;
    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Object nodeLevel;
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
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer rank;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;
    /**
     * 冻结
     */
    @ApiModelProperty(value = "冻结")
    private Boolean frozen;
    private List<DataModelTypeDto> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Object getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Object nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public List<DataModelTypeDto> getChildren() {
        return children;
    }

    public void setChildren(List<DataModelTypeDto> children) {
        this.children = children;
    }
}