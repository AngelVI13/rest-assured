import static io.restassured.RestAssured.baseURI;

import java.util.Map;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class Helpers {

	public static Map<String, String> login(String credentials) {
		// TODO: maybe define this once on the class ??
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";

		Response response = 
				given()
					.body(credentials)
				.when()
					.post("/lotto")
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
}
