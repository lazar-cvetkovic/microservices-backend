package com.artemi.player.infrastructure.config;

import com.artemi.player.application.service.*;
import com.artemi.player.domain.repository.PlayerRepository;
import org.springframework.context.annotation.*;

@Configuration
public class BeanConfig {
    @Bean
    ExperienceDomainService experienceDomainService() {
        return new ExperienceDomainService();
    }

    @Bean
    PlayerApplicationService playerApplicationService(PlayerRepository repo, ExperienceDomainService xp) {
        return new PlayerApplicationService(repo, xp);
    }
}
