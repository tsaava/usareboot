package com.usareboot.back.config;

import com.usareboot.back.models.ThreadDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadLocalConfig {

    @Bean(name="threadLocal")
    public ThreadLocal<ThreadDescription> getThreadLocal() {return new InheritableThreadLocal<>();}
}
