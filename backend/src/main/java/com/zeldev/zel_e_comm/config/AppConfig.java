package com.zeldev.zel_e_comm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ConfigurationProperties(prefix = "project.path")
@EnableJpaAuditing
@Getter
@Setter
public class AppConfig {
    private String images;
}

