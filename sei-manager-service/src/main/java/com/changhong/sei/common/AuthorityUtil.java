package com.changhong.sei.common;

import com.changhong.sei.core.context.ApplicationContextHolder;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.ge.service.ProjectUserService;
import com.changhong.sei.manager.vo.UserPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
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
    public static Set<String> getAuthorizedAppIds() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getAuthentication().getPrincipal();
        if (userPrincipal.getIsAdmin()) {
            return null;
        } else {
            ProjectUserService service = ApplicationContextHolder.getBean(ProjectUserService.class);
            return service.getAssignedAppIds(ContextUtil.getUserAccount());
        }
    }

    /**
     * 获取当前用户有权限的对象
     */
    public static Set<String> getAuthorizedModuleIds() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getAuthentication().getPrincipal();
        if (userPrincipal.getIsAdmin()) {
            return null;
        } else {
            ProjectUserService service = ApplicationContextHolder.getBean(ProjectUserService.class);
            return service.getAssignedModuleIds(ContextUtil.getUserAccount());
        }
    }
}
