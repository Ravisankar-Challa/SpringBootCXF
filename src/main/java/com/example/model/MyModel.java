package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder(value = {"user_name", "date_of_birth", "age", "membership_date"})
public class MyModel {

	@JsonbProperty("date_of_birth")
	private LocalDate dateOfBirth;
	
	@JsonbDateFormat(value = "hh:mm")
	@JsonbProperty("membership_time")
	private LocalDateTime membershipTime;
	
	@JsonbProperty("user_name")
	private String name;
	
	@JsonbProperty("age")
	private Integer age;
	
	@JsonbProperty("is_senior_citizen")
	private boolean seniorCitizen;

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public LocalDateTime getMembershipTime() {
		return membershipTime;
	}

	public void setMembershipTime(LocalDateTime membershipTime) {
		this.membershipTime = membershipTime;
	}

	public boolean isSeniorCitizen() {
		return seniorCitizen;
	}

	public void setSeniorCitizen(boolean seniorCitizen) {
		this.seniorCitizen = seniorCitizen;
	}
	
	
	
}
