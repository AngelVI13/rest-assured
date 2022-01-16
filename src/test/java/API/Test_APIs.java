package API;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static API.Helpers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

import java.util.Map;


// TODO: Add BDD feature files ????
// TODO: Add TestRails integration (example from provided code)
// TODO: Figure out how to provided test parameters externally
@Epic("APIs")
@Owner("Angel")
public class Test_APIs {
	String credentials = "login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks";

	@BeforeClass
	public void setup() {
		// Setting BaseURI once
//		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
		baseURI = "http://localhost:5000/";
	}
	
	@Feature("Log In")
	@Test(dataProvider = "existingUserCredentials", dataProviderClass = Helpers.class)
	public void test_login(String loginCredentials) {		
		given()
			.contentType(ContentType.URLENC)
			.body(loginCredentials)
		.when()
			.post("/login")
		.then()
			.statusCode(200)
			.body("success", equalTo(true))
			.log().all();
	}
	
	@Feature("Log In")
	@Test(dataProvider = "LoginCredentials", dataProviderClass = Helpers.class)
	public void test_loginMultiple(String loginCredentials, Boolean isSuccessful) {		
		given()
			.contentType(ContentType.URLENC)
			.body(loginCredentials)
		.when()
			.post("/login")
		.then()
			.statusCode(200)
			.body("success", equalTo(isSuccessful))
			.log().all();
	}

	@Feature("Get User Info")
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

	@Feature("Get Account Transactions")
	@Test
	public void test_getAccountTransactions() {
		Map<String, String> cookies = login(credentials);
		String accountId = getAccountId(cookies);
		String body = makeAccountTransactionRequest(accountId);

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
