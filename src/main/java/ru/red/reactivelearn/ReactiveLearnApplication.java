package ru.red.reactivelearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ReactiveLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveLearnApplication.class, args);
	}

}
