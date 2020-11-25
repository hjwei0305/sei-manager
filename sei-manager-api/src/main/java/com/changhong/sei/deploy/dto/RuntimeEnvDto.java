package com.changhong.sei.deploy.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务器节点(Node)DTO类
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@ApiModel(description = "运行环境DTO")
public class RuntimeEnvDto extends BaseEntityDto {
    private static final long serialVersionUID = 630890453379821715L;
    /**
     * 环境名称
     */
    @ApiModelProperty(value = "环境名称")
    private String name;
    /**
     * 环境代码
     */
    @ApiModelProperty(value = "环境代码")
    private String code;
    /**
     * 是否冻结
     */
    @ApiModelProperty(value = "是否冻结")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 代理服务基地址
     */
    @ApiModelProperty(value = "代理服务基地址")
    private String agentServer;
    /**
     * 描述说明
     */
    @ApiModelProperty(value = "描述说明")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
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