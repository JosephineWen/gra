package com.it.shop.englishShoping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EnglishShopingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnglishShopingApplication.class, args);
	}

}
