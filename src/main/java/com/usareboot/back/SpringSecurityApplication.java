package com.usareboot.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Collections;

@SpringBootApplication
@EnableFeignClients
public class SpringSecurityApplication implements CommandLineRunner {
    @Autowired
    private YAMLConfig myConfig;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringSecurityApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", 9000));
        app.run();
    }
    public void run(String... args) throws Exception {
//        System.out.println("using environment: " + myConfig.getEnvironment());
//        System.out.println("name: " + myConfig.getName());
    }
}
