package com.changhong.sei.ge.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.IFrozen;
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
@Table(name = "ge_runtime_env")
@DynamicInsert
@DynamicUpdate
public class RuntimeEnv extends BaseAuditableEntity implements IRank, ICodeUnique, IFrozen, Serializable {
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
     * 排序
     */
    @Column(name = "rank")
    private Integer rank = 0;
    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 代理服务基地址
     */
    @Column(name = "agent_server")
    private String agentServer;
    /**
     * 网关服务基地址
     */
    @Column(name = "gateway_server")
    private String gatewayServer;
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

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public Boolean getFrozen() {
        return frozen;
    }

    @Override
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getAgentServer() {
        return agentServer;
    }

    public void setAgentServer(String agentServer) {
        this.agentServer = agentServer;
    }

    public String getGatewayServer() {
        return gatewayServer;
    }

    public void setGatewayServer(String gatewayServer) {
        this.gatewayServer = gatewayServer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
