package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.RelationEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实现功能：流程定义
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:04
 */
@Entity
@Table(name = "flow_definition")
@DynamicInsert
@DynamicUpdate
public class FlowDefinition extends BaseEntity implements IRank, RelationEntity<FlowType, FlowTask>, Serializable {
    private static final long serialVersionUID = 369771080770875655L;

    /**
     * 流程类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private FlowType parent;
    /**
     * 流程任务
     */
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private FlowTask child;
    /**
     * 排序
     */
    @Column(name = "rank")
    private Integer rank = 0;
    /**
     * 处理人账号
     */
    @Column(name = "handle_account")
    private String handleAccount;
    /**
     * 处理人
     */
    @Column(name = "handle_user_name")
    private String handleUserName;
    /**
     * 版本乐观锁
     */
    @Version
    @Column(name = "version_")
    private Long version = 0L;

    @Override
    public FlowType getParent() {
        return parent;
    }

    @Override
    public void setParent(FlowType parent) {
        this.parent = parent;
    }

    @Override
    public FlowTask getChild() {
        return child;
    }

    @Override
    public void setChild(FlowTask child) {
        this.child = child;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getHandleAccount() {
        return handleAccount;
    }

    public void setHandleAccount(String handleAccount) {
        this.handleAccount = handleAccount;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
