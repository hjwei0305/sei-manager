package com.changhong.sei.manager.dto;

import com.changhong.sei.enums.UserAuthorityPolicy;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-10 16:48
 */
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1948851446236640144L;

    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 用户id，平台唯一
     */
    private String userId;
    /**
     * 用户主账号
     */
    private String account;
    /**
     * 当前登录账号
     */
    private String loginAccount;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户权限策略
     */
    private UserAuthorityPolicy authorityPolicy = UserAuthorityPolicy.NormalUser;

    /**
     * 用户权限列表
     */
    private Collection<String> authorities;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserAuthorityPolicy getAuthorityPolicy() {
        return authorityPolicy;
    }

    public void setAuthorityPolicy(UserAuthorityPolicy authorityPolicy) {
        this.authorityPolicy = authorityPolicy;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }

    public void putAuthority(String authority) {
        if (Objects.isNull(authorities)) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
    }
}
