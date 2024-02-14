package org.ananichev.minioserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.ananichev.minioserver.service.repository")
@EntityScan("org.ananichev.minioserver.model")
public class MinIoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinIoServerApplication.class, args);
	}

}
