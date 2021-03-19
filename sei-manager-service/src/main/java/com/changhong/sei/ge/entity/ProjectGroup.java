package com.changhong.sei.ge.entity;

import com.changhong.sei.core.dto.TreeEntity;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 项目组(ProjectGroup)实体类
 *
 * @author sei
 * @since 2020-11-10 16:24:30
 */
@Entity
@Table(name = "ge_project_group")
@DynamicInsert
@DynamicUpdate
public class ProjectGroup extends BaseAuditableEntity implements TreeEntity<ProjectGroup>, Serializable {
    private static final long serialVersionUID = -60933176202135691L;
    /**
     * 代码
     */
    @Column(name = "code", unique = true, length = 20, nullable = false)
    private String code;
    /**
     * 名称
     */
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    /**
     * 代码路径
     */
    @Column(name = "code_path", length = 500, nullable = false)
    private String codePath;

    /**
     * 名称路径
     */
    @Column(name = "name_path", length = 500, nullable = false)
    private String namePath;

    /**
     * 层级
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
    @Column(name = "parent_code", length = 20)
    private String parentCode;
    @Column(name = "group_path")
    private String groupPath;
    @Column(name = "remark")
    private String remark;

    /**
     * 子节点列表
     */
    @Transient
    private List<ProjectGroup> children;

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

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public List<ProjectGroup> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<ProjectGroup> children) {
        this.children = children;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectGroup menu = (ProjectGroup) o;

        return id != null ? id.equals(menu.id) : menu.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}