package com.artemi.player.application;

import com.artemi.player.infrastructure.persistence.JpaPlayerEntity;
import com.artemi.player.infrastructure.persistence.SpringDataPlayerJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = SpringDataPlayerJpaRepository.class)
@EntityScan(basePackageClasses = JpaPlayerEntity.class)
public class PlayerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }
}
