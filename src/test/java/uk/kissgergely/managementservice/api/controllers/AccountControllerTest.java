package uk.kissgergely.managementservice.api.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import uk.kissgergely.managementservice.api.resources.ControllerConstants;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

class AccountControllerTest {

    @Test
    void test() {
	//get(ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH + "/").then().statusCode(404);
    }

}
