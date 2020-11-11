package com.changhong.sei.manager.config.properties;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 实现功能：忽略配置
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-10 17:05
 */
public class IgnoreConfigProperties {
    /**
     * 需要忽略的 URL 格式，不考虑请求方法
     */
    private List<String> pattern = Lists.newArrayList();

    /**
     * 需要忽略的 GET 请求
     */
    private List<String> get = Lists.newArrayList();

    /**
     * 需要忽略的 POST 请求
     */
    private List<String> post = Lists.newArrayList();

    /**
     * 需要忽略的 DELETE 请求
     */
    private List<String> delete = Lists.newArrayList();

    /**
     * 需要忽略的 PUT 请求
     */
    private List<String> put = Lists.newArrayList();

    /**
     * 需要忽略的 HEAD 请求
     */
    private List<String> head = Lists.newArrayList();

    /**
     * 需要忽略的 PATCH 请求
     */
    private List<String> patch = Lists.newArrayList();

    /**
     * 需要忽略的 OPTIONS 请求
     */
    private List<String> options = Lists.newArrayList();

    /**
     * 需要忽略的 TRACE 请求
     */
    private List<String> trace = Lists.newArrayList();

    public List<String> getPattern() {
        return pattern;
    }

    public void setPattern(List<String> pattern) {
        this.pattern = pattern;
    }

    public List<String> getGet() {
        return get;
    }

    public void setGet(List<String> get) {
        this.get = get;
    }

    public List<String> getPost() {
        return post;
    }

    public void setPost(List<String> post) {
        this.post = post;
    }

    public List<String> getDelete() {
        return delete;
    }

    public void setDelete(List<String> delete) {
        this.delete = delete;
    }

    public List<String> getPut() {
        return put;
    }

    public void setPut(List<String> put) {
        this.put = put;
    }

    public List<String> getHead() {
        return head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

    public List<String> getPatch() {
        return patch;
    }

    public void setPatch(List<String> patch) {
        this.patch = patch;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getTrace() {
        return trace;
    }

    public void setTrace(List<String> trace) {
        this.trace = trace;
    }
}
