import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import java.io.IOException;
import java.util.Map;

public class Test_APIs {
	String credentials = "login=evaidakaviciene&password=tfHL9tEEc5KTmkJJV5ks";
	/* 
	 * TODO: separate this out to a helper function that can be used as `Test Setup` routine
	 * It needs to return the cookies needed for requesting data from other endpoints 
	 */	
	@Test
	public void test_login() {
		// TODO: maybe define this once on the class ??
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
		
		/* TODO: Access and return cookies so that they can be used for future requests
		 * Set-Cookie			mscore.cacheid=1639045350603; Path=/
		 * Set-Cookie			JSESSIONID=0000MQArR-YNwmMQ-NzGQn-uvXY:-1; Path=/; HttpOnly
		 */

		
		// TODO: Cookies exist even on this request? Should we ignore them or ???
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
	
	public void test_getUserId() {
		/* TODO: Need user ID so we can request stuff
		 *  https://dev11.nymbus.com/coreweb/controller/context.getContext
		 *  user	Object { id: 588, name: "Elmyra Vaidakaviciene", code: "evaidakaviciene", � }
		 */
		
		/* TODO: Maybe this is better for username
		 * https://dev11.nymbus.com/coreweb/controller/widget.Query?name=username
		 * field	Object { USERFNAME: "Elmyra", USERID: "588", USERLNAME: "Vaidakaviciene" }
		 */
		
		/* TODO: Where does this ID come from 208329??? -> This seems to be an account ID
		 * https://dev11.nymbus.com/coreweb/controller/formData/CustomerCRM/?id=208329
		 */
	}
	
	// TODO: This test should check account numbers only 
	@Test
	public void test_CustomerCRM() throws IOException {
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
		Map<String, String> cookies =  Helpers.login(credentials);
		// TODO: get these from login response
		String sessionId = "0000-k8xZ7FoCk2XsrzS3ZRM4WD:-1";
		String cacheId = "1639045350603";
		
		given()
//			.cookie("JSESSIONID", sessionId)
//			.cookie("mscore.cacheid", cacheId)
				.cookies(cookies)
			.param("id", "208329") // TODO: Where does this ID come from?
		.when()
			.get("/formData/CustomerCRM/")
		.then()
			.statusCode(200)
			.body("success", equalTo(true)) // TODO: Check for more stuff
			.log().all();
	}
}
