package com.changhong.sei.deploy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-08 09:16
 */
public class GitlabPushHookRequest implements Serializable {
    private static final long serialVersionUID = -8325972318026013814L;
    @JsonProperty()
    private String userId;
}
