import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import io.restassured.config.EncoderConfig;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test_APIs {
	String credentials = "login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks";

	@BeforeClass
	public void setup() {
		// Setting BaseURI once
//		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
		baseURI = "http://localhost:5000/";
	}

	@Test
	public void test_login() {
		given()
			.contentType(ContentType.URLENC)
			.body(credentials)
		.when()
			.post("/login")
		.then()
			.statusCode(200)
			.body("success", equalTo(true))
			.log().all();
	}

	@Test
	public void test_CustomerCRM() {
		Map<String, String> cookies =  Helpers.login(credentials);
		String userId = Helpers.getUserId(cookies);

		given()
			.cookies(cookies)
			.param("id", userId)
		.when()
			.get("/formData/CustomerCRM/")
		.then()
			.statusCode(200)
			.body("success", equalTo(true))
			.body("data.field.accountid", hasItems(userId))
			.body("data.field.accountnumber", hasItems("55889"))
			.log().all();
	}


	@Test
	public void test_getAccountTransactions() {
		Map<String, String> cookies =  Helpers.login(credentials);
		String userId = Helpers.getUserId(cookies);
		String accountId = Helpers.getFirstAccountId(userId, cookies);
		String body = Helpers.makeAccountTransactionRequest(accountId);

		given()
			.cookies(cookies)
			.contentType(ContentType.JSON)
			.body(body)
		.when()
			.post("widget._GenericProcess")
		.then()
			.statusCode(200)
			.log().all();
	}

	

}
