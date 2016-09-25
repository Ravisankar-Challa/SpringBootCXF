package com.example;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.MatcherConfig.ErrorDescriptionType.HAMCREST;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.MatcherConfig;
import com.jayway.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    
    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_MESSAGE = "errorMessage";
    
    @LocalServerPort
    private int port;
    
    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssured.config().matcherConfig(new MatcherConfig(HAMCREST));
    }

	@Test
	public void test_hello_world() {
	    Response response = 
	    given().
	           accept(MediaType.TEXT_PLAIN).
	    when().
	           get("/api/hello").
	    then().
	           log().all().
	           statusCode(200).
	           contentType(MediaType.TEXT_PLAIN).
	           extract().
	           response();
	    
	    assertThat(response.getBody().asString(), equalTo("Hello World!!! Hello Hai"));
	}

}
