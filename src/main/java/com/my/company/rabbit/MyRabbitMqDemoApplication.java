package com.my.company.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages="com.my.company.rabbit")
public class MyRabbitMqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRabbitMqDemoApplication.class, args);
	}
}
