package com.example.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PostRequest {

    @NotNull(message = "College is empty")
    @Pattern(regexp = "[a-zA-z]*", message = "College is not valid")
    private String college;
    
    @NotNull(message = "Age is empty")
    @Pattern(regexp = "^[0-9]*$", message = "Age is not valid")
    private String age;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
