import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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

		List response = given()
			.cookies(cookies)
			.param("id", userId)
		.when()
			.get("/formData/CustomerCRM/")
		.then()
			.statusCode(200)
			.body("success", equalTo(true))
			.body("data.field.accountid", hasItems(userId))
			.body("data.field.accountnumber", hasItems("55889"))
			.log().all()
				.extract().path("data.field.accountid");
	}


	@Test
	public void test_getAccountTransactions() {
		Map<String, String> cookies =  Helpers.login(credentials);
		String userId = Helpers.getUserId(cookies);

		String body = "{" +
				"\"ruleType\":\"BankingCORE\","+
                "\"actions\":[" +
                "\"request\"," +
                "\"getAccountTransactions\"" +
                "]," +
                "\"beans\":[" +
                "{" +
                "\"type\":\"bank.data.actmst\"," +
                "\"fields\":{" +
                "\"accountId\":\"" + getFirstAccountId(userId, cookies) + "\"," +
                "\"count\":10," +
                "\"offset\":0," +
                "\"isWarehouseStatement\":false," +
                "\"isItemsToWorkStatement\":false," +
                "\"escrow\":0" +
                "}" +
                "}" +
                "]" +
                "}";

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

	private String getFirstAccountId(String userId, Map<String, String> cookies) {
		String body = "{" +
				"\"ruleType\":\"BankingCORE\"," +
				"\"actions\":[" +
				"\"request\"," +
				"\"getCustomerAccounts\"" +
				"]," +
				"\"beans\":[" +
				"{" +
				"\"type\":\"bank.data.actmst\"," +
				"\"fields\":{" +
				"\"count\":999," +
				"\"customerId\":\"" + getCustomerId(userId, cookies) + "\"" +
				"}" +
				"}" +
				"]" +
				"}";
		List response = given()
				.cookies(cookies)
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.post("widget._GenericProcess")
				.then()
				.statusCode(200)
				.log().all()
				.extract().path("data.fieldValue.resultList.id");

		return ((ArrayList)response.get(0)).get(0).toString();
	}


	private String getCustomerId(String userId, Map<String, String> cookies) {
		List<String> response =
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
				.log().all()
				.extract().path("data.field.accountid");

		return response.get(0);
	}
}
