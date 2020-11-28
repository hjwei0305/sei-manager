package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实现功能：流程实例
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:11
 */
@Entity
@Table(name = "flow_task_instance")
@DynamicInsert
@DynamicUpdate
public class FlowTaskInstance extends BaseEntity implements IRank, Serializable {
    private static final long serialVersionUID = -64497955636689211L;
    public static final String FIELD_TYPE_ID = "typeId";
    public static final String FIELD_VERSION = "version";
    /**
     * 流程定义版本
     */
    @Column(name = "version_")
    private Long version = 0L;
    /**
     * 流程类型
     */
    @Column(name = "type_id", nullable = false)
    private String typeId;
    /**
     * 流程类型
     */
    @Column(name = "type_name", nullable = false)
    private String typeName;
    /**
     * 流程任务
     */
    @Column(name = "task_id", nullable = false)
    private String taskId;
    /**
     * 流程任务
     */
    @Column(name = "task_name", nullable = false)
    private String taskName;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
}
