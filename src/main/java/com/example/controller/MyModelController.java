package com.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.example.model.MyModel;

@Path("/")
@Component
public class MyModelController {

	@GET
	@Path("mymodel")
	@Produces(MediaType.APPLICATION_JSON)
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
