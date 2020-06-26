package com.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@ConfigurationProperties("wechat")
@Data
public class WeChatConfig {
    private String appId;
    private String appSecret;
    private String token;
}