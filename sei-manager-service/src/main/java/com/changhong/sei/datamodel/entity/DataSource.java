package com.changhong.sei.datamodel.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.datamodel.dto.DBType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据源(DataSource)实体类
 *
 * @author sei
 * @since 2020-07-28 17:31:44
 */
@Entity
@Table(name = "data_source")
@DynamicInsert
@DynamicUpdate
public class DataSource extends BaseAuditableEntity implements Serializable, ICodeUnique {
    private static final long serialVersionUID = -55714157101308549L;
    /**
     * 数据库名
     */
    @Column(name = "code")
    private String code;
    /**
     * 描述说明
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 数据库类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "db_type")
    private DBType dbType;
    /**
     * 主机地址
     */
    @Column(name = "host")
    private String host = "127.0.0.1";
    /**
     * 端口
     */
    @Column(name = "port")
    private Integer port;
    /**
     * url
     * 根据类型拼接
     */
    @Column(name = "url")
    private String url;
    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;
    /**
     * 密码
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "password")
    private byte[] password;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

}