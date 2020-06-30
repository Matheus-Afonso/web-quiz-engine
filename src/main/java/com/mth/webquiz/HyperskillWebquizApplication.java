package com.mth.webquiz;

/* 
Ver README.md para mais informações
 * */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HyperskillWebquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyperskillWebquizApplication.class, args);
	}

}
