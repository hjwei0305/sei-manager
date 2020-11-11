package com.changhong.sei.manager.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 实现功能:授权树形VO
 *
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @date 2019/03/19 11:18
 */
public class AuthTreeDto implements Serializable {

    private static final long serialVersionUID = -6216180335819756170L;
    /**
     * id
     */
    private String id;

    /**
     * 功能项名称
     */
    private String name;

    /**
     * 资源
     */
    private String url;


    /**
     * 功能项类型：1-页面,2-操作
     */
    private Integer type;

    /**
     * 树层级
     */
    private Integer nodeLevel;

    /**
     * 是否已勾选
     */
    private Boolean assigned = Boolean.FALSE;
    /**
     * 子节点列表
     */
    private List<AuthTreeDto> children;

    /**
     * 构造函数
     *
     * @param feature 功能项
     */
    public AuthTreeDto(FeatureDto feature) {
        this.id = feature.getId();
        this.name = feature.getName();
        this.url = feature.getUrl();
        this.type = feature.getType();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public List<AuthTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<AuthTreeDto> children) {
        this.children = children;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
