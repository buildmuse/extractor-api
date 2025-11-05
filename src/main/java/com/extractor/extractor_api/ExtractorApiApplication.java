package com.extractor.extractor_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.extractor.extractor_api")
@EnableAsync
public class ExtractorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtractorApiApplication.class, args);
	}

}
