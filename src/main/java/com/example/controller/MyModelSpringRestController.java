package com.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MyModel;

@RestController
public class MyModelSpringRestController {

	@GetMapping("/mymodelmvc")
	public MyModel getMyModel() {
		MyModel myModel = new MyModel();
		myModel.setAge(33);
		myModel.setDateOfBirth(LocalDate.now());
		myModel.setMembershipTime(LocalDateTime.now());
		myModel.setName("Ravi");
		myModel.setSeniorCitizen(false);
		return myModel;
	}
	
}
