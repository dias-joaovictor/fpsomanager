package com.modec.fpsomanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FPSOManagerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FPSOManagerApplication.class, args);
	}

}
