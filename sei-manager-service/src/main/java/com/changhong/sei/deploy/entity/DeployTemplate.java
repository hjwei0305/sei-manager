package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.deploy.dto.TemplateType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部署模板(DeployTemplate)实体类
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 */
@Entity
@Table(name = "deploy_template")
@DynamicInsert
@DynamicUpdate
public class DeployTemplate extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 520497168789427262L;
    public static final String FIELD_TYPE = "tyep";
    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 模版类型
     * 部署类模版允许存在多个,前端只允许新增部署类模版
     * 发版类模版仅允许一个(前端一个,后端一个)
     */
//    @Enumerated(EnumType.STRING)
    @Column(name = "tyep", updatable = false)
    private String tyep = TemplateType.DEPLOY.name();
    /**
     * 模板全局参数
     */
    @Column(name = "global_param")
    private String globalParam;
    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;
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

    public String getTyep() {
        return tyep;
    }

    public void setTyep(String tyep) {
        this.tyep = tyep;
    }

    public String getGlobalParam() {
        return globalParam;
    }

    public void setGlobalParam(String globalParam) {
        this.globalParam = globalParam;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}