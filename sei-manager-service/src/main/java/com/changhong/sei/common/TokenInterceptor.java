package com.changhong.sei.common;

import com.changhong.sei.core.context.ContextUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2022-02-24 14:17
 */
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //获取令牌
        String token = ContextUtil.getToken();
        //将令牌放入请求header中
        // request.getHeaders().add("x-sid", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFvMi5tYSIsImxvZ2luQWNjb3VudCI6ImNoYW8yLm1hIiwiaXAiOiJVbmtub3duIiwidXNlck5hbWUiOiLpqazotoUiLCJsb2NhbGUiOiJ6aF9DTiIsInVzZXJJZCI6IkRDQTE2MzRGLTQ0MTgtMTFFQi05QjZCLTAyNDJDMEE4NDMxRiIsInJhbmRvbUtleSI6Ijk2RTBEMzUwLTk1MEUtMTFFQy1BQUFGLTAyNDJDMEE4NDMwNSIsImF1dGhvcml0eVBvbGljeSI6Ikdsb2JhbEFkbWluIiwidXNlclR5cGUiOiJFbXBsb3llZSIsImV4cCI6MTY0NTc1MTQ0MywiaWF0IjoxNjQ1NjY1MDQzLCJ0ZW5hbnQiOiJTRUkiLCJhY2NvdW50IjoiY2hhbzIubWEifQ.IUKaAVOm8yWPn-H0y-K4Y7b4ce6_Vo9aVXkmu5lhq8ouo2SGSFSbu9dCuClcc4Z5eU8uj86qrxeORtBuRCKtYw");
        request.getHeaders().add("x-sid", token);
        return execution.execute(request, body);
    }
}
