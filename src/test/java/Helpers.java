import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;

public class Helpers {

	public static JSONObject login() {
		// TODO: maybe define this once on the class ??
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
		
		/* TODO: Access and return cookies so that they can be used for future requests
		 * Set-Cookie			mscore.cacheid=1639045350603; Path=/
		 * Set-Cookie			JSESSIONID=0000MQArR-YNwmMQ-NzGQn-uvXY:-1; Path=/; HttpOnly
		 */
		
		/* TODO:
		 * .body() or .param() for the login credentials ??
		 * request payload is only listed as: login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks
		 * If they work as params ?
		 	.param("login", "evaidakaviciene") // TODO: Obfuscate these parameters ?? or take them from somewhere else
			.param("password", "tfHL9tEEc5KTmkJJV5ks") 
		   If it works as body, does it need to be JSON encoded?
		 */

		/* TODO: Another approach to providing credentials
			JSONObject request = new JSONObject();
			request.put("login", "evaidakaviciene");
			request.put("password", "tfHL9tEEc5KTmkJJV5ks");
			
			System.out.println(request);
			System.out.println(request.toJSONString());
			
			.body(request.toJSONString())
		*/
		
		
		// TODO: Cookies exist even on this request? Should we ignore them or ???
		given()
			.contentType(ContentType.JSON)
			.contentType(ContentType.TEXT)
			.body("login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks")
		.when()
			.post("/login")
		.then()
			.statusCode(200)
			.body("success", equalTo(true))
			.log().all();
	}
}
