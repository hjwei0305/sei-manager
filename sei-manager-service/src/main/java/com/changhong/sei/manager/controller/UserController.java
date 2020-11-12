package com.changhong.sei.manager.controller;

import com.changhong.sei.core.cache.CacheBuilder;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.UserApi;
import com.changhong.sei.manager.commom.Constants;
import com.changhong.sei.manager.dto.LoginRequest;
import com.changhong.sei.manager.dto.LoginResponse;
import com.changhong.sei.manager.dto.UpdatePasswordRequest;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.UserService;
import com.changhong.sei.manager.vo.UserPrincipal;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

/**
 * 用户表(SecUser)控制类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@RestController
@Api(value = "UserApi", tags = "用户服务")
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseEntityController<User, UserDto> implements UserApi, Constants {

    /**
     * 用户表服务对象
     */
    @Autowired
    private UserService service;

    @Override
    public BaseEntityService<User> getService() {
        return service;
    }

    @Autowired
    private CacheBuilder cacheBuilder;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 修改密码
     */
    @Override
    public ResultData<Void> updatePassword(UpdatePasswordRequest request) {
        return service.updatePassword(request.getAccount(), request.getOldPassword(), request.getPassword());
    }

    /**
     * 登录
     */
    @Override
    public ResultData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getAccount(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 设置认证
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse loginResponse = new LoginResponse();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) principal;

            SessionUser user = new SessionUser();
            user.setUserId(userPrincipal.getId());
            user.setAccount(userPrincipal.getUsername());
            user.setUserName(userPrincipal.getNickname());
            user.setLoginAccount(loginRequest.getAccount());
            user.setTenantCode("SEI");
            ContextUtil.generateToken(user);

            String sid = user.getSessionId();
            // 默认10小时
            cacheBuilder.set(REDIS_JWT_KEY_PREFIX + sid, user.getToken(), 36000000);

            loginResponse.setUserId(user.getUserId());
            loginResponse.setAccount(user.getAccount());
            loginResponse.setUserName(user.getUserName());
            loginResponse.setLoginAccount(user.getLoginAccount());
            loginResponse.setSessionId(sid);
            Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
            if (CollectionUtils.isNotEmpty(authorities)) {
                for (GrantedAuthority authority : authorities) {
                    loginResponse.putAuthority(authority.getAuthority());
                }
            }
        }
        return ResultData.success(loginResponse);
    }

    @Override
    public ResultData<String> logout() {
        SessionUser sessionUser = ContextUtil.getSessionUser();
        cacheBuilder.remove(REDIS_JWT_KEY_PREFIX + sessionUser.getSessionId());
        return ResultData.success();
    }

    /**
     * 在线用户列表
     *
     * @param search 分页参数
     */
    @Override
    public ResultData<UserDto> online(Search search) {
        return ResultData.success();
    }

    /**
     * 批量踢出在线用户
     *
     * @param sid 会话id
     */
    @Override
    public ResultData<String> kickoutUser(String sid) {
        cacheBuilder.remove(REDIS_JWT_KEY_PREFIX + sid);
        return ResultData.success();
    }
}