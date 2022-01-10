import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class Tests_POST {
	@Test
	public void test_1_post() {
		JSONObject request = new JSONObject();
		request.put("name", "John");
		request.put("job", "blacksmith");
		
		System.out.println(request);
		System.out.println(request.toJSONString());
		
		given()
//			.header("Content-Type", "application/json")
//			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("https://reqres.in/api/users")
		.then()
			.statusCode(201);
			
	}
}
