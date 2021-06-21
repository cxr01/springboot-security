package com.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing 开启审计功能
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BossAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BossAdminApplication.class, args);
    }

}
