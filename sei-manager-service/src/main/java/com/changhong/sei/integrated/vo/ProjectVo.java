package com.changhong.sei.integrated.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 14:36
 */
public class ProjectVo implements Serializable {
    private static final long serialVersionUID = -8673090236429416613L;

    private ProjectType type;
    private String code;
    private String name;
    private String nameSpace;
    private String groupId;
    /**
     * gitId
     */
    private String gitId;
    /**
     * git地址
     */
    private String gitHttpUrl;
    private String gitSshUrl;
    private String gitWebUrl;
    private LocalDateTime gitCreateTime;

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
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

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getGitHttpUrl() {
        return gitHttpUrl;
    }

    public void setGitHttpUrl(String gitHttpUrl) {
        this.gitHttpUrl = gitHttpUrl;
    }

    public String getGitSshUrl() {
        return gitSshUrl;
    }

    public void setGitSshUrl(String gitSshUrl) {
        this.gitSshUrl = gitSshUrl;
    }

    public String getGitWebUrl() {
        return gitWebUrl;
    }

    public void setGitWebUrl(String gitWebUrl) {
        this.gitWebUrl = gitWebUrl;
    }

    public LocalDateTime getGitCreateTime() {
        return gitCreateTime;
    }

    public void setGitCreateTime(LocalDateTime gitCreateTime) {
        this.gitCreateTime = gitCreateTime;
    }
}
