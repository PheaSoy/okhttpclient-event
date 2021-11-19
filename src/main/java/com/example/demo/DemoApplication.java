package com.example.demo;

import com.example.demo.config.BookConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Autowired
	private BookConfig bookConfig;

	@GetMapping("/backend/greeting")
	public String greet(){
		return " book config" + bookConfig.toString();
	}
	@RequestMapping(value = "api/user", method = RequestMethod.PUT)
	public void updateUser( @RequestBody Boolean authorized) {
		System.out.println("OK!!!!!!");
	}
}
