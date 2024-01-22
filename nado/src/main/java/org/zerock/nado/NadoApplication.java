package org.zerock.nado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class NadoApplication {
	public static void main(String[] args) {
		SpringApplication.run(NadoApplication.class, args);
	}
}