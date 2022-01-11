import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class Helpers {

	public static Map<String, String> login(String credentials) {
		// TODO: maybe define this once on the class ??
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";

		Response response = 
				given()
					.contentType(ContentType.URLENC)
					.body(credentials)
				.when()
					.post("/login")
				.then()
					.statusCode(200)
					.body("success", equalTo(true))
					.extract()
					.response();
		
		// TODO: what happens when statusCode is not 200 or success is False?
		return response.cookies();		
	}
	
	public static String getUserId(Map<String, String> cookies) {
		return "208329";
	}
	

	private static String getCustomerId(String userId, Map<String, String> cookies) {
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
	
	public static String getFirstAccountId(String userId, Map<String, String> cookies) {
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
	
	public static String makeAccountTransactionRequest(String accountId) {
		return "{" +
				"\"ruleType\":\"BankingCORE\","+
                "\"actions\":[" +
                "\"request\"," +
                "\"getAccountTransactions\"" +
                "]," +
                "\"beans\":[" +
                "{" +
                "\"type\":\"bank.data.actmst\"," +
                "\"fields\":{" +
                "\"accountId\":\"" + accountId + "\"," +
                "\"count\":10," +
                "\"offset\":0," +
                "\"isWarehouseStatement\":false," +
                "\"isItemsToWorkStatement\":false," +
                "\"escrow\":0" +
                "}" +
                "}" +
                "]" +
                "}";
	}
}
