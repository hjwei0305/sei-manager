package com.changhong.sei.manager.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
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

    @Test
    public void updatePassword() {
        ResultData<Void> resultData = userService.updatePassword("1072806377661009920", HashUtil.md5("123456"));
        System.out.println(resultData);
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("20045203");
        user.setNickname("马超");
        user.setEmail("chao2.ma@changhong.com");
        user.setPhone("18608081023");
        ResultData<Void> resultData = userService.createUser(user);
        System.out.println(resultData);
    }
}