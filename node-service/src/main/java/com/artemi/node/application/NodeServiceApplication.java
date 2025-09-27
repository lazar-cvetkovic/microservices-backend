package com.artemi.node.application;

import com.artemi.node.infrastructure.persistence.JpaNodeEntity;
import com.artemi.node.infrastructure.persistence.SpringDataNodeJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = SpringDataNodeJpaRepository.class)
@EntityScan(basePackageClasses = JpaNodeEntity.class)
public class NodeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NodeServiceApplication.class, args);
    }
}
