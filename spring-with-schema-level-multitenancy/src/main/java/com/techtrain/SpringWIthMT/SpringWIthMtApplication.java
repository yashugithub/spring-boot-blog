package com.techtrain.SpringWIthMT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.techtrain.*")
public class SpringWIthMtApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringWIthMtApplication.class, args);
	}

}
