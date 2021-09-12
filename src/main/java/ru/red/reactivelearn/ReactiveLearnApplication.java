package ru.red.reactivelearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "ru.red.reactivelearn")
@EnableConfigurationProperties
public class ReactiveLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveLearnApplication.class, args);
    }

}
