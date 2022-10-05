package com.eta.houzezbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HouzezBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouzezBackendApplication.class, args);
    }

}
