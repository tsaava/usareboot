package com.usareboot.back;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.imageio.ImageIO;
import java.util.Collections;

@SpringBootApplication
@EnableFeignClients
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringSecurityApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", 9000));
        app.run();
    }
    @PostConstruct
    public void init() {
        // Регистрация WebP плагина для ImageIO
        ImageIO.scanForPlugins();
    }
}
