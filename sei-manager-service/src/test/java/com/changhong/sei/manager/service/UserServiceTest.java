package com.changhong.sei.manager.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.manager.controller.UserController;
import com.changhong.sei.manager.dto.CreateUserRequest;
import com.changhong.sei.manager.dto.LoginRequest;
import com.changhong.sei.manager.dto.LoginResponse;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.util.HashUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-12 09:48
 */
public class UserServiceTest extends BaseUnitTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;

    @Test
    public void updatePassword() {
        ResultData<Void> resultData = userService.updatePassword("1072806377661009920", HashUtil.md5("SX908cOZ"));
        System.out.println(resultData);
    }

    @Test
    public void createUser() {
        CreateUserRequest user = new CreateUserRequest();
        user.setAccount("200452031");
        user.setNickname("马超1");
        user.setEmail("chao2.ma@changhong.com");
        user.setPhone("18608081023");
        ResultData<Void> resultData = userController.createUser(user);
        System.out.println(resultData);
    }

    @Test
    public void save() {
        User user = JsonUtils.fromJson("{\"id\":\"1072806377661009920\",\"nickname\":\"管理员\",\"phone\":\"17300000000\"}", User.class);
        OperateResultWithData<User> resultData = userService.save(user);
        System.out.println(resultData);
    }

    @Test
    public void login() {
        String pwd = HashUtil.md5("SX908cOZ");
        System.out.println("e528a8b3f7e4373400e47310a1ef1a5c".equals(pwd));
        LoginRequest request = new LoginRequest();
        request.setAccount("chao2.ma");
        request.setPassword(pwd);
        ResultData<LoginResponse> resultData = userController.login(request);
        System.out.println(resultData);
    }
}