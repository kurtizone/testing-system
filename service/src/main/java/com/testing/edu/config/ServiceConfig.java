package com.testing.edu.config;

import com.softserve.edu.config.JPAConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JPAConfig.class)
@ComponentScan("com.testing.edu")
public class ServiceConfig {}
