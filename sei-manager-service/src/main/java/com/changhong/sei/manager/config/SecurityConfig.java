package com.changhong.sei.manager.config;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.filter.SessionUserAuthenticationHandler;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.manager.filter.ExtTokenAuthenticationFilter;
import com.changhong.sei.manager.filter.JwtAuthenticationFilter;
import com.changhong.sei.manager.service.RbacAuthorityService;
import com.changhong.sei.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

/**
 * 实现功能：Security 配置
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-10 17:14
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomConfig customConfig;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider();
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors()
                // 关闭 CSRF
                .and().csrf().disable()
                // 登录行为由自己实现，参考 AuthController#login
                .formLogin().disable()
                .httpBasic().disable()

                // 认证请求
                .authorizeRequests()
                // 登录地址
                .antMatchers(HttpMethod.POST, "/**/login", "/**/logout").permitAll()
                // 所有请求都需要登录访问
                .anyRequest()
                .authenticated()
                // RBAC 动态 url 认证
                .anyRequest()
                .access("@rbacAuthorityService.hasPermission(request, authentication)")

                // 登出行为由自己实现，参考 AuthController#logout
                .and().logout().disable()
                // Session 管理
                .sessionManagement()
                // 因为使用了JWT，所以这里不管理Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 异常处理
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        // @formatter:on

        // 添加自定义 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 放行所有不需要登录就可以访问的请求，参见 AuthController
     * 也可以在 {@link #configure(HttpSecurity)} 中配置
     * {@code http.authorizeRequests().antMatchers("/api/auth/**").permitAll()}
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/**/v2/api-docs/**", "/**/swagger-*/**",
                "/webjars/**",
                "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jpeg", "/**/*.png", "/**/*.jpg", "/**/*.gif", "/**/*.ico"
        );

        WebSecurity and = web.ignoring().and();
        // 忽略 GET
        customConfig.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        customConfig.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        customConfig.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        customConfig.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        customConfig.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        customConfig.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        customConfig.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        customConfig.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        customConfig.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));
    }

    /**
     * 替换平台默认的token检查过滤器
     */
    @Bean
    public SessionUserAuthenticationHandler userAuthenticationHandler() {
        return new ExtTokenAuthenticationFilter();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public RbacAuthorityService rbacAuthorityService(RequestMappingHandlerMapping mapping) {
        return new RbacAuthorityService(mapping);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            try {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "*");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(200);

                response.getWriter().write(JsonUtils.toJson(ResultData.fail(accessDeniedException.getMessage())));
            } catch (IOException e) {
                LogUtil.error("Response写出JSON异常，", e);
            }
        };
    }
}
