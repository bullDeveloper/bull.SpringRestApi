package com.bull.springboot.application;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({ "com.bull.springboot.application", "com.bull.springboot.repository" })
@EnableSwagger2
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.bull.springboot"))
				.build()
				.apiInfo(apiInfoDetails());
	}
	
	private ApiInfo apiInfoDetails() {
		return new ApiInfo(
				"Java Code Challenge Mendel", 
				"Servicio RestFul de Transacciones", 
				"1.0", 
				"Free to Use", 
				new springfox.documentation.service.Contact("Bull Labat", "", "jlabat738@gmail"), 
				"API License", 
				"",
				Collections.emptyList());
	}
}
