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
 * 实现功能：流程实例任务节点
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:04
 */
@Entity
@Table(name = "flow_instance_task")
@DynamicInsert
@DynamicUpdate
public class FlowInstanceTask extends BaseEntity implements IRank, Serializable {
    private static final long serialVersionUID = 369771080770875655L;
    public static final String FIELD_INSTANCE_ID = "instanceId";

    /**
     * 流程实例ID
     */
    @Column(name = "instance_id", nullable = false)
    private String instanceId;
    /**
     * 代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 排序
     * 只读.引用code数据,不做更新和写入操作
     */
    @Column(name = "code", insertable = false, updatable = false)
    private Integer rank;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
