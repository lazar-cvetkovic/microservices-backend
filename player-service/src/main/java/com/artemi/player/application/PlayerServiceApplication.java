package com.artemi.player.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.artemi.player")
public class PlayerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }
}
