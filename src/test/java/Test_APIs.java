import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import java.util.Map;

public class Test_APIs {
	String credentials = "login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks";
	
	@BeforeClass
	public void setup() {
		// Setting BaseURI once
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
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
			.log().everything();
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
}
