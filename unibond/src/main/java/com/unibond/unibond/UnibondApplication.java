package com.unibond.unibond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UnibondApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnibondApplication.class, args);
	}

}
