package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.manager.dto.LoginRequest;
import com.changhong.sei.manager.dto.LoginResponse;
import com.changhong.sei.manager.dto.UpdatePasswordRequest;
import com.changhong.sei.manager.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 用户表(SecUser)API
 *
 * @author sei
 * @since 2020-11-10 16:51:25
 */
@Valid
@FeignClient(name = "sei-manager", path = "user")
public interface UserApi extends BaseEntityApi<UserDto> {

    /**
     * 修改密码
     */
    @PostMapping(value = "/updatePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    ResultData<Void> updatePassword(UpdatePasswordRequest updatePasswordRequest);

    /**
     * 登录
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "登录", notes = "登录")
    ResultData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    ResultData<String> logout();

    /**
     * 在线用户列表
     *
     * @param search 分页参数
     */
    @PostMapping(value = "/online", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "在线用户列表", notes = "在线用户列表")
    ResultData<UserDto> online(@RequestBody Search search);

    /**
     * 踢出在线用户
     *
     * @param sid 会话id
     */
    @DeleteMapping(value = "/kickout/{sid}")
    @ApiOperation(value = "踢出在线用户", notes = "踢出在线用户")
    ResultData<String> kickoutUser(@PathVariable("sid") String sid);
}