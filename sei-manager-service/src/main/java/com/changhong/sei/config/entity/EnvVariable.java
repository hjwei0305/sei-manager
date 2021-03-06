package com.changhong.sei.config.entity;

import com.changhong.sei.common.UseStatus;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 环境变量(ConfEnvVariable)实体类
 *
 * @author sei
 * @since 2021-03-02 14:26:19
 */
@Entity
@Table(name = "conf_env_variable")
@DynamicInsert
@DynamicUpdate
public class EnvVariable extends BaseAuditableEntity implements ICodeUnique, Serializable {
    private static final long serialVersionUID = 938743634139683037L;
    public static final String FIELD_USE_STATUS = "useStatus";
    /**
     * 配置键
     */
    @Column(name = "code", unique = true)
    private String code;
    /**
     * 使用状态：NONE、ENABLE、DISABLE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "use_status")
    private UseStatus useStatus = UseStatus.NONE;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public UseStatus getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(UseStatus useStatus) {
        this.useStatus = useStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}