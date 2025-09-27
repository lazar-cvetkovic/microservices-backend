package com.artemi.node.infrastructure.config;

import com.artemi.node.application.service.NodeApplicationService;
import com.artemi.node.domain.repository.NodeRepository;
import org.springframework.context.annotation.*;

@Configuration
public class BeanConfig {
    @Bean
    NodeApplicationService nodeApplicationService(NodeRepository repo) {
        return new NodeApplicationService(repo);
    }
}
