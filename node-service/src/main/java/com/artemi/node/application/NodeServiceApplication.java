package com.artemi.node.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(scanBasePackages = "com.artemi.node")
@EnableJpaRepositories(basePackages = "com.artemi.node.infrastructure.persistence")
@EntityScan(basePackages = "com.artemi.node.infrastructure.persistence")
public class NodeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NodeServiceApplication.class, args);
    }
}
