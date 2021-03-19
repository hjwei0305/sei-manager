package com.changhong.sei.common;

import com.changhong.sei.core.context.ApplicationContextHolder;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.ge.service.ProjectUserService;

import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-19 16:03
 */
public final class AuthorityUtil {

    /**
     * 获取当前用户有权限的对象
     */
    public static Set<String> getAuthorizedData() {
        ProjectUserService service = ApplicationContextHolder.getBean(ProjectUserService.class);
        return service.getAssignedObjects(ContextUtil.getUserAccount());
    }
}
