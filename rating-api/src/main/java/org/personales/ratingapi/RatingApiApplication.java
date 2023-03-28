package org.personales.ratingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@RibbonClient(name = "msvc-rating-api")
@EnableEurekaClient
@SpringBootApplication
public class RatingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingApiApplication.class, args);
	}

}
