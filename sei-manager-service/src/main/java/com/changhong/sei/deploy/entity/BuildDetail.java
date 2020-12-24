package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.deploy.dto.BuildStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实现功能：构建明细(BuildDetail)实体类
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-04 16:37
 */
@Entity
@Table(name = "build_detail")
@DynamicInsert
@DynamicUpdate
public class BuildDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2444540079790981818L;
    public static final String FIELD_RECORD_ID = "recordId";
    public static final String FIELD_BUILD_NUMBER = "buildNumber";

    /**
     * 发布记录id
     */
    @Column(name = "record_id")
    private String recordId;
    /**
     * 任务名
     */
    @Column(name = "job_name")
    private String jobName;
    /**
     * 构建编号
     */
    @Column(name = "build_number")
    private Integer buildNumber = 0;
    /**
     * 构建结果
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "build_status")
    private BuildStatus buildStatus;
    /**
     * 日志
     */
    @Column(name = "build_log")
    private String buildLog;
    /**
     * 开始时间(ms)
     */
    @Column(name = "start_time")
    private Long startTime;
    /**
     * 构建持续多少时间(ms)
     */
    @Column(name = "duration")
    private Long duration;
    /**
     * Jenkins构建人账号
     */
    @Column(name = "build_account")
    private String buildAccount;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getBuildLog() {
        return buildLog;
    }

    public void setBuildLog(String buildLog) {
        this.buildLog = buildLog;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getBuildAccount() {
        return buildAccount;
    }

    public void setBuildAccount(String buildAccount) {
        this.buildAccount = buildAccount;
    }
}
