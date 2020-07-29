package com.mth.webquiz;

/* 
Ver README.md para mais informações
 * */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WebquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebquizApplication.class, args);
	}

}
