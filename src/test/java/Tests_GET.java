import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class Tests_GET {
	
	@Test
	public void test_1() {
		given()
//			.header("key", "value")
//			.header("Content-Type", "application/json")
//			.param("key", "value")
			.get("https://reqres.in/api/users?page=2")
		.then()
			.statusCode(200)
			.body("data.id[0]", equalTo(7))
			.body("data.first_name", hasItems("Michael", "Lidsay"))
			.log().all();
	}

}
