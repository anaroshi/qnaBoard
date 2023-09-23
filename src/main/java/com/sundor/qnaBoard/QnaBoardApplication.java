package com.sundor.qnaBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QnaBoardApplication {

	public static void main(String[] args) {
		System.out.println(".... success....");
//		System.setProperty("spring.devtools.restart.enabled", "false");

		SpringApplication.run(QnaBoardApplication.class, args);
	}
}
