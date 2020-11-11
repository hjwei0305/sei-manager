package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.manager.dto.LoginRequest;
import com.changhong.sei.manager.dto.LoginResponse;
import com.changhong.sei.manager.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
     * 登录
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/logout")
    ResultData<String> logout(HttpServletRequest request);

    /**
     * 在线用户列表
     *
     * @param search 分页参数
     */
    @PostMapping(value = "/online", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<UserDto> online(@RequestBody Search search);

    /**
     * 批量踢出在线用户
     *
     * @param name 用户名
     */
    @DeleteMapping(value = "/kickout/{name}")
    ResultData<String> kickoutUser(@PathVariable("name") String name);
}