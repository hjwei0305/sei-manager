package com.changhong.sei.cicd.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.RelationEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部署模板阶段关系表(DeployTemplateStage)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:07
 */
@Entity
@Table(name = "cicd_deploy_template_stage")
@DynamicInsert
@DynamicUpdate
public class DeployTemplateStage extends BaseEntity implements IRank, RelationEntity<DeployTemplate, DeployStage>, Serializable {
    private static final long serialVersionUID = 369771080770875655L;
    public static final String FIELD_STAGE_ID = "child.id";

    /**
     * 用户组
     */
    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private DeployTemplate parent;
    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    private DeployStage child;
    /**
     * 脚本
     */
    @Column(name = "playscript")
    private String playscript;
    /**
     * 排序
     */
    @Column(name = "rank")
    private Integer rank = 0;

    @Override
    public DeployTemplate getParent() {
        return parent;
    }

    @Override
    public void setParent(DeployTemplate parent) {
        this.parent = parent;
    }

    @Override
    public DeployStage getChild() {
        return child;
    }

    @Override
    public void setChild(DeployStage child) {
        this.child = child;
    }

    public String getPlayscript() {
        return playscript;
    }

    public void setPlayscript(String playscript) {
        this.playscript = playscript;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}