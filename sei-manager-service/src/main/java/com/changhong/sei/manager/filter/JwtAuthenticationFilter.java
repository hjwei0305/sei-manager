package com.changhong.sei.manager.filter;

import com.changhong.sei.core.cache.CacheBuilder;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.manager.commom.Constants;
import com.changhong.sei.manager.config.CustomConfig;
import com.changhong.sei.manager.service.UserService;
import com.changhong.sei.util.thread.ThreadLocalUtil;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-10 17:16
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter implements Constants {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomConfig customConfig;
    @Autowired
    private CacheBuilder cacheBuilder;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.nonNull(securityContext.getAuthentication()) && securityContext.getAuthentication().isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = request.getHeader("x-sid");
        if (StringUtils.isBlank(jwt)) {
            jwt = request.getHeader(ContextUtil.HEADER_TOKEN_KEY);
        }

        if (StringUtils.isNotBlank(jwt)) {
            try {
                SessionUser sessionUser = ContextUtil.getSessionUser(jwt);
                String sid = sessionUser.getSessionId();
                if (StringUtils.isNotBlank(sid)) {
                    String token = cacheBuilder.get(REDIS_JWT_KEY_PREFIX + sid);
                    if (StringUtils.equals(jwt, token)) {
                        MDC.put("userId", sessionUser.getUserId());
                        MDC.put("account", sessionUser.getAccount());
                        MDC.put("userName", sessionUser.getUserName());

                        ThreadLocalUtil.setLocalVar(SessionUser.class.getSimpleName(), sessionUser);
                        // 设置token到可传播的线程全局变量中
                        ThreadLocalUtil.setTranVar(ContextUtil.HEADER_TOKEN_KEY, sessionUser.getToken());
                        String username = sessionUser.getAccount();

                        UserDetails userDetails = userService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        securityContext.setAuthentication(authenticationToken);
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        try {
                            response.setHeader("Access-Control-Allow-Origin", "*");
                            response.setHeader("Access-Control-Allow-Methods", "*");
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());

                            response.getWriter().write(JsonUtils.toJson(ResultData.fail("认证失败,会话不合法.")));
                        } catch (IOException ioe) {
                            LogUtil.error("Response写出JSON异常，", ioe);
                        }
                        return;
                    }
                }
            } catch (Exception e) {
                try {
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Access-Control-Allow-Methods", "*");
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());

                    response.getWriter().write(JsonUtils.toJson(ResultData.fail(e.getMessage())));
                } catch (IOException ioe) {
                    LogUtil.error("Response写出JSON异常，", ioe);
                }
                return;
            }
        }
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            response.getWriter().write(JsonUtils.toJson(ResultData.fail("会话不存在!")));
        } catch (IOException e) {
            LogUtil.error("Response写出JSON异常，", e);
        }
    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (Objects.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();
        switch (httpMethod) {
            case GET:
                ignores.add("/**/config/**");
                ignores.add("/**/authWhitelist/get/**");
                ignores.add("/**/user/generate/**");
                ignores.add("/**/user/getMailServer/**");

                // swagger 文档
                ignores.add("/**/v2/api-docs*/**");
                ignores.add("/**/swagger-*/**");

                ignores.add("/**/*.html");
                ignores.add("/**/*.js");
                ignores.add("/**/*.css");
                ignores.add("/**/*.ico");
                ignores.add("/**/*.jpg");
                ignores.add("/**/*.svg");
                ignores.add("/**/*.png");
                ignores.add("/**/*.gif");
                ignores.addAll(customConfig.getIgnores().getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores().getPut());
                break;
            case HEAD:
                ignores.addAll(customConfig.getIgnores().getHead());
                break;
            case POST:
                ignores.add("/**/webhook/**");
                ignores.add("/**/user/check/**");
                ignores.add("/**/user/registVerify/**");
                ignores.add("/**/user/activate/**");
                ignores.add("/**/user/forgetPassword/**");
                ignores.addAll(customConfig.getIgnores().getPost());
                break;
            case PATCH:
                ignores.addAll(customConfig.getIgnores().getPatch());
                break;
            case TRACE:
                ignores.addAll(customConfig.getIgnores().getTrace());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores().getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customConfig.getIgnores().getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores().getPattern());

        // spring boot actuator
        ignores.add("/**/actuator/**");
        ignores.add("/**/instances/**");
        ignores.add("/**/applications/**");
        ignores.add("/**/websocket/**");

        ignores.add("/**/login");

        if (CollectionUtils.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;
    }
}
