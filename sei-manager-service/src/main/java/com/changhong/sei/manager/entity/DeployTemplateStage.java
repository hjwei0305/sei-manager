package com.changhong.sei.manager.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
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
@Table(name = "deploy_template_stage")
@DynamicInsert
@DynamicUpdate
public class DeployTemplateStage extends BaseEntity implements RelationEntity<DeployTemplate, DeployStage>, Serializable {
    private static final long serialVersionUID = 369771080770875655L;

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
     * 排序
     */
    @Column(name = "rank")
    private Long rank;

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

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

}