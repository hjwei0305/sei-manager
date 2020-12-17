package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实现功能：流程类型节点
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:04
 */
@Entity
@Table(name = "flow_de_type_node")
@DynamicInsert
@DynamicUpdate
public class FlowTypeNode extends BaseAuditableEntity implements IRank, Serializable {
    private static final long serialVersionUID = 369771080770875655L;
    public static final String FIELD_TYPE_ID = "typeId";
    /**
     * 流程类型
     */
    @Column(name = "type_id", nullable = false)
    private String typeId;
    /**
     * 代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 排序
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
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
