package com.example.ClientConnectivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ClientConnectivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientConnectivityApplication.class, args);
	}

	@GetMapping("/")
	public String hello(){
		return "Hi there!";
	}
}
