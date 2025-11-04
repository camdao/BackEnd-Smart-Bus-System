package com.SmartBus.global.config.properties;

import com.SmartBus.global.infra.config.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        JwtProperties.class,
})
@Configuration
public class PropertiesConfig {}
