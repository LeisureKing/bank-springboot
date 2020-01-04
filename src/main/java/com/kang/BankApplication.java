package com.kang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kang.dao")
public class BankApplication {

	public static void main(String[] args) {

		SpringApplication.run(BankApplication.class, args);
	}

}
