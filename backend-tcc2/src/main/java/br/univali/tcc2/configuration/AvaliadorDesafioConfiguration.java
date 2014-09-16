package br.univali.tcc2.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "br.univali.tcc2")
public class AvaliadorDesafioConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(AvaliadorDesafioConfiguration.class, args);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

}
