package com.ilim.app.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class UriConfiguration {

    private String httpbin = "http://localhost:8080";
}