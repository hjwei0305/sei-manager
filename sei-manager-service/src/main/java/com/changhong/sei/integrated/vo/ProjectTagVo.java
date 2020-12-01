package com.changhong.sei.integrated.vo;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 16:06
 */
public class ProjectTagVo implements Serializable {
    private static final long serialVersionUID = 6653470253717761566L;
    /**
     * git项目id
     */
    private String projectId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 参考分支
     */
    private String ref = "master";
    /**
     * 标签描述
     */
    private String message;
    private String target;
    private String commitId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }
}
