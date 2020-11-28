package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.IFrozen;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实现功能：流程任务
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-28 23:03
 */
@Entity
@Table(name = "flow_task")
@DynamicInsert
@DynamicUpdate
public class FlowTask extends BaseAuditableEntity implements IFrozen, Serializable {
    private static final long serialVersionUID = -64497955636689211L;

    /**
     * 模块名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }
}
