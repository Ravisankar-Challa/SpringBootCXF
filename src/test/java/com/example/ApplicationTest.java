package com.example;

import static io.restassured.RestAssured.given;
import static io.restassured.config.MatcherConfig.ErrorDescriptionType.HAMCREST;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.util.Constants.APPLICATION_ERROR_JSON;
import static com.example.util.Constants.ERROR_CODE;
import static com.example.util.Constants.ERROR_MESSAGE;

import com.example.util.Constants;
import io.restassured.RestAssured;
import io.restassured.config.MatcherConfig;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
 
    @LocalServerPort
    private int port;
    
    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssured.config().matcherConfig(new MatcherConfig(HAMCREST));
    }

	@Test
	public void test_hello_world() {
	    given().
	           accept(MediaType.APPLICATION_JSON).
	    when().
	           get("/api/hello").
	    then().
	           log().all().
	           statusCode(200).
	           contentType(MediaType.APPLICATION_JSON).
	           body("hai", equalTo("Hello World!!! Hello Hai"));
	}
	
	@Test
    public void should_return_400_for_get_request_with_invalid_name() {
        given().
               accept(MediaType.TEXT_PLAIN).
        when().
               get("/api/get/validate/86").
        then().
               log().all().
               statusCode(400).
               contentType(APPLICATION_ERROR_JSON).
               body(ERROR_CODE, equalTo(400)).
               body(ERROR_MESSAGE, allOf(containsString("Question Missing"),
                                         containsString("Version Missing"),
                                         containsString("Name format is incorrect")));
    }
	
	@Test
    public void should_return_400_for_get_request_with_invalid_version() {
        given().
               accept(MediaType.TEXT_PLAIN).
               header("version", "ABC").
        when().
               get("/api/get/validate/RAVI").
        then().
               log().all().
               statusCode(400).
               contentType(APPLICATION_ERROR_JSON).
               body(ERROR_CODE, equalTo(400)).
               body(ERROR_MESSAGE, allOf(containsString("Question Missing"),
                                         containsString("Version incorrect format")));
    }
	
	@Test
    public void should_return_400_for_get_request_with_invalid_question() {
        given().
               accept(MediaType.TEXT_PLAIN).
               header("version", "1.0").
               queryParam("question", "1111").
        when().
               get("/api/get/validate/RAVI").
        then().
               log().all().
               statusCode(400).
               contentType(APPLICATION_ERROR_JSON).
               body(ERROR_CODE, equalTo(400)).
               body(ERROR_MESSAGE, equalTo("Question format is incorrect"));
    }
	
	@Test
    public void should_return_200_for_valid_get_request() {
	    Response response = 
        given().
               accept(MediaType.TEXT_PLAIN).
               header("version", "1.0").
               queryParam("question", "Who is bill gates").
        when().
               get("/api/get/validate/RAVI").
        then().
               log().all().
               statusCode(200).
               contentType(MediaType.TEXT_PLAIN).
               extract().response();
	    
	    assertThat(response.getBody().asString(), equalTo("1.0 RAVI Who is bill gates"));
    }
	
	@Test
    public void should_return_406_for_post_request_with_out_content_type_header() {
        given().
               accept(MediaType.TEXT_PLAIN).
               contentType(MediaType.APPLICATION_JSON).
               body("{" +
                      "\"college\": \"Anna University Campus CEG\"," +
                      "\"age\": 20 " +
                    "}").
        when().
               post("/api/post/validate/RAVI").
        then().
               log().all().
               statusCode(406);
    }
	
	@Test
    public void should_return_415_for_post_request_with_invalid_content_type_header() {
        given().
               contentType(MediaType.TEXT_PLAIN).
        when().
               post("/api/post/validate/86").
        then().
               log().all().
               statusCode(415);
    }
	
	@Test
    public void should_return_400_for_post_request_with_invalid_name() {
        given().
               contentType(MediaType.APPLICATION_JSON).
        when().
               post("/api/post/validate/86").
        then().
               log().all().
               statusCode(400).
               contentType(APPLICATION_ERROR_JSON).
               body(ERROR_CODE, equalTo(400)).
               body(ERROR_MESSAGE, equalTo("Name format is incorrect"));
    }
	
	@Test
    public void should_return_400_for_post_request_with_invalid_college() {
        given().
               contentType(MediaType.APPLICATION_JSON).
               body("{" +
                       "\"college\": \"456\", " +
                       "\"age\": 20 " +
                     "}").
        when().
               post("/api/post/validate/RAVI").
        then().
               log().all().
               statusCode(400).
               contentType(APPLICATION_ERROR_JSON).
               body(ERROR_CODE, equalTo(400)).
               body(ERROR_MESSAGE, equalTo("College is not valid"));
    }

	@Test
    public void should_return_400_for_post_request_with_invalid_age() {
        given().
               contentType(MediaType.APPLICATION_JSON).
               body("{" +
                       "\"college\": \"Anna University Campus CEG\"," +
                       "\"age\": -1" +
                     "}").
        when().
               post("/api/post/validate/RAVI").
        then().
               log().all().
               statusCode(400).
               contentType(APPLICATION_ERROR_JSON).
               body(ERROR_CODE, equalTo(400)).
               body(ERROR_MESSAGE, equalTo("Age is not valid"));
    }
	
	@Test
    public void should_return_200_for_valid_post_request() {
        given().
               contentType(MediaType.APPLICATION_JSON).
               body("{" +
                       "\"college\": \"Anna University Campus CEG\"," +
                       "\"age\": 21" +
                     "}").
        when().
               post("/api/post/validate/RAVI").
        then().
               log().all().
               statusCode(200).
               contentType(MediaType.APPLICATION_JSON).
               body("college", equalTo("Anna University Campus CEG")).
               body("age", equalTo(21));
    }
	
	@Test
    public void should_return_401_for_invalid_user_name() {
	    given().
                contentType(MediaType.APPLICATION_JSON).
                header("user", "INVALID_USER").
         when().
                get("/api/hello/async").
         then().
                log().all().
                statusCode(401).
                contentType(APPLICATION_ERROR_JSON).
                body(ERROR_CODE, equalTo(401)).
                body(ERROR_MESSAGE, equalTo("Not authorized"));
    }
	
	@Test
    public void test_async_response() {
	    given().
                accept(MediaType.APPLICATION_JSON).
                header("user", "RAVI").
         when().
                get("/api/hello").
         then().
                log().all().
                statusCode(200).
                contentType(MediaType.APPLICATION_JSON).
                body("hai", equalTo("Hello World!!! Hello Hai"));
    }
	
	@Test
    public void should_return_version1() {
        given().
                accept(Constants.SERVICE_V1).
         when().
                get("/api/service").
         then().
                log().all().
                statusCode(200).
                contentType(Constants.SERVICE_V1).
                body("version", equalTo(1));
    }
	
	@Test
    public void should_return_version2() {
        given().
                accept(Constants.SERVICE_V2).
         when().
                get("/api/service").
         then().
                log().all().
                statusCode(200).
                contentType(Constants.SERVICE_V2).
                body("version", equalTo(2));
    }
}
