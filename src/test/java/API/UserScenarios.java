package API;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.Assert;
import static io.restassured.RestAssured.given;

public class UserScenarios {

    private String path;
    private Response response;

    private String loginCredentials = "login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks";


    @Given("the login endpoint exists")
    public void preReq() {
        RestAssured.baseURI = "http://localhost:5000/";
        path = "/login";
    }

    @When("I send a valid login request")
    public void loginUser() {
        response = 
            given()
                .contentType(ContentType.URLENC)
                .body(loginCredentials)
            .when()
                .post(path)
            .then()
                .extract().response();
    }

    @Then("response status code should be {int}")
    public void checkResponseStatusCode(int code) {
    	Assert.assertEquals(code, response.getStatusCode());
    }

    @And("log in response should be valid")
    public void verifyResponse() {
        Boolean success = response.jsonPath().get("success");

        Assert.assertTrue(success);
    }
}
