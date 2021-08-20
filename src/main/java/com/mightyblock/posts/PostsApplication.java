package com.mightyblock.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservice that implements all the necessary functions for the post model
 *
 * @author  Dario Pizzio
 * @version 1.0
 * @since   2021-08-19
 */
@SpringBootApplication
public class PostsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}

}
