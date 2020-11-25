package com.changhong.sei.filter;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.filter.SessionUserAuthenticationHandler;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-11 10:22
 */
public class JwtAuthenticationFilter implements SessionUserAuthenticationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public ResultData<SessionUser> handler(HttpServletRequest request) {
        if (checkIgnores(request)) {
            return ResultData.success();
        }

        String jwt = request.getHeader("x-sid");
        if (StringUtils.isBlank(jwt)) {
            jwt = request.getHeader(ContextUtil.HEADER_TOKEN_KEY);
        }

        if (StringUtils.isNotBlank(jwt)) {
            try {
                SessionUser sessionUser = ContextUtil.getSessionUser(jwt);

                return ResultData.success(sessionUser);
            } catch (Exception e) {
                LOG.error("认证失败,token不合法[" + jwt + "]", e);
                return ResultData.fail("认证失败,会话不合法.");
            }
        } else {
            LOG.error("认证失败,会话不存在.");
            return ResultData.fail("认证失败,会话不存在.");
        }
    }


    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        Set<String> ignores = Sets.newHashSet();
        // swagger 文档
        ignores.add("/**/v2/api-docs*/**");
        ignores.add("/**/swagger-*/**");

        // spring boot actuator
        ignores.add("/**/actuator/**");
        ignores.add("/**/instances/**");
        ignores.add("/**/applications/**");

        ignores.add("/**/*.html");
        ignores.add("/**/*.js");
        ignores.add("/**/*.css");
        ignores.add("/**/*.ico");
        ignores.add("/**/*.jpg");
        ignores.add("/**/*.svg");
        ignores.add("/**/*.png");
        ignores.add("/**/*.gif");
        ignores.add("/**/login");

        AntPathMatcher matcher = new AntPathMatcher();
        if (CollectionUtils.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                if (matcher.match(ignore, request.getServletPath())) {
                    return true;
                }
            }
        }
        return false;
    }
}
