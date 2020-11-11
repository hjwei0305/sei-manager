package com.changhong.sei.log.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientBuilderCustomizer;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;

/**
 * 实现功能： es自动配置
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 00:55
 */
@EnableConfigurationProperties(RestClientPoolProperties.class)
public class SearchAutoConfigure {

    @Bean
    public RestClientBuilderCustomizer restClientBuilderCustomizer(RestClientPoolProperties poolProperties,
                                                                   RestClientProperties restProperties) {
        return (builder) -> {
            setRequestConfig(builder, poolProperties);

            setHttpClientConfig(builder, poolProperties, restProperties);
        };
    }

    /**
     * 异步httpclient连接延时配置
     */
    private void setRequestConfig(RestClientBuilder builder, RestClientPoolProperties poolProperties) {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(poolProperties.getConnectTimeOut())
                    .setSocketTimeout(poolProperties.getSocketTimeOut())
                    .setConnectionRequestTimeout(poolProperties.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });
    }

    /**
     * 异步httpclient连接数配置
     */
    private void setHttpClientConfig(RestClientBuilder builder, RestClientPoolProperties poolProperties,
                                     RestClientProperties restProperties) {
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(poolProperties.getMaxConnectNum())
                    .setMaxConnPerRoute(poolProperties.getMaxConnectPerRoute());
            /*
            上面采用异步机制实现抢先认证，这个功能也可以禁用，这意味着每个请求都将在没有授权标头的情况下发送，然后查看它是否被接受，
            并且在收到HTTP 401响应后，它再使用基本认证头重新发送完全相同的请求，这个可能是基于安全、性能的考虑
            */
            // 禁用抢先认证的方式
            // httpClientBuilder.disableAuthCaching();

            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(restProperties::getUsername).to(username -> {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(username, restProperties.getPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
            return httpClientBuilder;
        });
    }

    @Bean(name = "restHighLevelClient", destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }
}
