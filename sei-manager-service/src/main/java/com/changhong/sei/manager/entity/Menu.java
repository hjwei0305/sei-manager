package com.changhong.sei.manager.entity;

import com.changhong.sei.core.dto.TreeEntity;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 实现功能：系统菜单实体
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "sec_menu")
@DynamicInsert
@DynamicUpdate
public class Menu extends BaseAuditableEntity implements TreeEntity<Menu> {
    private static final long serialVersionUID = 5921201068332944127L;
    /**
     * 菜单代码
     */
    @Column(name = "code", unique = true, length = 20, nullable = false)
    private String code;
    /**
     * 菜单名称
     */
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    /**
     * 菜单代码路径
     */
    @Column(name = "code_path", length = 500, nullable = false)
    private String codePath;

    /**
     * 菜单名称路径
     */
    @Column(name = "name_path", length = 500, nullable = false)
    private String namePath;

    /**
     * 菜单层级
     */
    @Column(name = "node_level", nullable = false)
    private Integer nodeLevel;

    /**
     * 排序号
     */
    @Column(name = "rank", nullable = false)
    private Integer rank = 0;

    /**
     * 父节点id
     */
    @Column(name = "parent_id", length = 36)
    private String parentId;

    /**
     * 功能项组Id
     */
    @Column(name = "feature_id", length = 36)
    private String featureId;

    /**
     * 关联功能项id
     */
    @ManyToOne
    @JoinColumn(name = "feature_id", insertable = false, updatable = false)
    private Feature feature;

    /**
     * 图标样式名称
     */
    @Column(name = "icon_cls", length = 30)
    private String iconCls;

    /**
     * 图标文件数据
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "icon_file_data")
    private String iconFileData;

    /**
     * 子节点列表
     */
    @Transient
    private List<Menu> children;

    /**
     * 菜单项资源
     */
    @Transient
    private String menuUrl;

    /**
     * 已收藏的菜单项
     */
    @Transient
    private Boolean favorite = Boolean.FALSE;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
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
    public List<Menu> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<Menu> children) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Menu menu = (Menu) o;

        return id != null ? id.equals(menu.id) : menu.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
