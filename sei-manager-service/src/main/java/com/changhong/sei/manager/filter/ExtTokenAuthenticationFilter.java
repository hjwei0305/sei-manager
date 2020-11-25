package com.changhong.sei.manager.filter;

import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.filter.SessionUserAuthenticationHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-11 10:22
 */
public class ExtTokenAuthenticationFilter implements SessionUserAuthenticationHandler {


    @Override
    public ResultData<SessionUser> handler(HttpServletRequest request) {
        return ResultData.success();
    }
}
