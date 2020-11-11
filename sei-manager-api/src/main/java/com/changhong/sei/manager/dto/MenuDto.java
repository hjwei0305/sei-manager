package com.changhong.sei.manager.dto;

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
 * 实现功能: 系统菜单DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:57
 */
@ApiModel(description = "系统菜单DTO")
public class MenuDto extends BaseEntityDto implements TreeEntity<MenuDto> {
    private static final long serialVersionUID = 891649039705760108L;
    /**
     * 菜单代码
     */
    @ApiModelProperty(value = "菜单代码(系统给号)")
    private String code;
    /**
     * 菜单名称
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "菜单名称(max = 20)", required = true)
    private String name;
    /**
     * 菜单代码路径
     */
    @ApiModelProperty(value = "菜单代码路径")
    private String codePath;

    /**
     * 菜单名称路径
     */
    @ApiModelProperty(value = "菜单名称路径")
    private String namePath;

    /**
     * 菜单层级
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "菜单层级")
    private Integer nodeLevel = 0;

    /**
     * 排序号
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "排序号")
    private Integer rank=0;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private String parentId;

    /**
     * 功能项组Id
     */
    @ApiModelProperty(value = "功能项组Id")
    private String featureId;

    /**
     * 关联功能项代码
     */
    @ApiModelProperty(value = "关联功能项代码")
    private String featureCode;

    /**
     * 关联功能项名称
     */
    @ApiModelProperty(value = "关联功能项名称")
    private String featureName;

    /**
     * 图标样式名称
     */
    @ApiModelProperty(value = "图标样式名称")
    private String iconCls;

    /**
     * 图标文件数据
     */
    @ApiModelProperty(value = "图标文件数据")
    private String iconFileData;

    /**
     * 子节点列表
     */
    @ApiModelProperty(value = "子节点列表")
    private List<MenuDto> children;

    /**
     * 菜单项资源
     */
    @ApiModelProperty(value = "菜单项资源")
    private String menuUrl;

    /**
     * 已收藏的菜单项
     */
    @ApiModelProperty(value = "已收藏的菜单项")
    private Boolean favorite = Boolean.FALSE;

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

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getIconFileData() {
        return iconFileData;
    }

    public void setIconFileData(String iconFileData) {
        this.iconFileData = iconFileData;
    }

    @Override
    public List<MenuDto> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
