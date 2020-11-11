package com.changhong.sei.manager.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.manager.api.UserApi;
import com.changhong.sei.manager.dto.LoginRequest;
import com.changhong.sei.manager.dto.LoginResponse;
import com.changhong.sei.manager.dto.UserDto;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户表(SecUser)控制类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@RestController
@Api(value = "UserApi", tags = "用户服务")
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseEntityController<User, UserDto> implements UserApi {
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
    private AuthenticationManager authenticationManager;

    /**
     * 登录
     */
    @Override
    public ResultData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtUtil.createJWT(authentication,loginRequest.getRememberMe());
//        return ApiResponse.ofSuccess(new JwtResponse(jwt));

        return ResultData.success();
    }

    @Override
    public ResultData<String> logout(HttpServletRequest request) {
//        try {
//            // 设置JWT过期
//            jwtUtil.invalidateJWT(request);
//        } catch (SecurityException e) {
//            throw new SecurityException(Status.UNAUTHORIZED);
//        }
//        return ApiResponse.ofStatus(Status.LOGOUT);
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
     * @param name 用户名
     */
    @Override
    public ResultData<String> kickoutUser(String name) {
//        if (CollUtil.isEmpty(names)) {
//            throw new SecurityException(Status.PARAM_NOT_NULL);
//        }
//        if (names.contains(SecurityUtil.getCurrentUsername())){
//            throw new SecurityException(Status.KICKOUT_SELF);
//        }
//        monitorService.kickout(names);
//        return ApiResponse.ofSuccess();
        return ResultData.success();
    }
}