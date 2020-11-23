package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-24 01:39
 */
@Entity
@Table(name = "runtime_env")
@DynamicInsert
@DynamicUpdate
public class RuntimeEnv extends BaseAuditableEntity implements ICodeUnique, Serializable {
    private static final long serialVersionUID = 556644951408263122L;
    /**
     * 环境名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 环境代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 代理服务
     */
    @Column(name = "agent_server")
    private String agentServer;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getAgentServer() {
        return agentServer;
    }

    public void setAgentServer(String agentServer) {
        this.agentServer = agentServer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
