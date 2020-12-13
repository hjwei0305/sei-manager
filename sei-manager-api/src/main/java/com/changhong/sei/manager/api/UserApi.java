package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.manager.dto.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * 用户表(SecUser)API
 *
 * @author sei
 * @since 2020-11-10 16:51:25
 */
@Valid
@FeignClient(name = "sei-manager", path = "user")
public interface UserApi extends BaseEntityApi<UserDto>, FindByPageApi<UserDto> {

    /**
     * 获取支持的邮箱服务
     *
     * @return 返回验证码
     */
    @GetMapping(path = "getMailServer")
    @ApiOperation(value = "获取支持的邮箱服务", notes = "获取支持的邮箱服务")
    ResultData<String[]> getMailServer();

    /**
     * 验证码
     *
     * @param reqId 请求id
     * @return 返回验证码
     */
    @GetMapping(path = "generate")
    @ApiOperation(value = "验证码", notes = "验证码5分钟有效期")
    ResultData<String> generate(@RequestParam("reqId") @NotBlank String reqId);

    /**
     * 验证码
     *
     * @param reqId 请求id
     * @param code  校验值
     * @return 返回验证码
     */
    @PostMapping(path = "check")
    @ApiOperation(value = "校验验证码", notes = "校验验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reqId", value = "请求id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "校验值", required = true, paramType = "query")
    })
    ResultData<Void> check(@RequestParam("reqId") @NotBlank String reqId, @RequestParam("code") @NotBlank String code);

    /**
     * 注册用户
     *
     * @param request 注册请求
     * @return 返回注册结果
     */
    @PostMapping(path = "registered", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "注册用户", notes = "注册用户")
    ResultData<Void> registered(@Valid @RequestBody RegisteredUserRequest request);

    /**
     * 账号注册申请激活
     *
     * @param sign 签名值
     * @return 返回结果
     */
    @GetMapping(path = "activate/{sign}")
    @ApiOperation(value = "账号注册申请激活", notes = "账号注册申请激活")
    void activate(@PathVariable("sign") String sign, HttpServletResponse response) throws IOException;

    /**
     * 创建用户
     */
    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "创建用户", notes = "创建用户")
    ResultData<Void> createUser(@Valid @RequestBody CreateUserRequest user);

    /**
     * 忘记密码流程检查用户
     */
    @PostMapping(value = "forgetPassword/checkUser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "忘记密码流程检查用户", notes = "忘记密码流程检查用户")
    ResultData<ForgetPasswordResponse> checkUser(@Valid @RequestBody ForgetPasswordRequest user);

    /**
     * 忘记密码
     */
    @PostMapping(value = "forgetPassword/{sign}")
    @ApiOperation(value = "忘记密码流程重置密码", notes = "忘记密码流程重置密码")
    ResultData<Void> forgetPassword(@PathVariable("sign") String sign);

    /**
     * 修改密码
     */
    @PostMapping(value = "updatePassword", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    ResultData<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest);

    /**
     * 登录
     */
    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "登录", notes = "登录")
    ResultData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    /**
     * 退出登录
     */
    @PostMapping("logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    ResultData<String> logout();

    /**
     * 获取用户有权限的菜单和功能项
     *
     * @return 获取用户有权限的菜单和功能项
     */
    @GetMapping("getUserAuthorizedData")
    @ApiOperation(value = "获取用户有权限的菜单和功能项", notes = "获取用户有权限的菜单和功能项")
    ResultData<UserAuthorizedResponse> getUserAuthorizedData();

    /**
     * 在线用户列表
     *
     * @param search 分页参数
     */
    @PostMapping(value = "online", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "在线用户列表", notes = "在线用户列表")
    ResultData<UserDto> online(@RequestBody Search search);

    /**
     * 踢出在线用户
     *
     * @param sid 会话id
     */
    @DeleteMapping(value = "kickout/{sid}")
    @ApiOperation(value = "踢出在线用户", notes = "踢出在线用户")
    ResultData<String> kickoutUser(@PathVariable("sid") String sid);
}