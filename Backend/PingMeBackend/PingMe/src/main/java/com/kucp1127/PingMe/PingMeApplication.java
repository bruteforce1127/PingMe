package com.kucp1127.PingMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PingMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingMeApplication.class, args);
	}

}
