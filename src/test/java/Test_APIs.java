import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class Test_APIs {
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
	
	public void test_getUserId() {
		/* TODO: Need user ID so we can request stuff
		 *  https://dev11.nymbus.com/coreweb/controller/context.getContext
		 *  user	Object { id: 588, name: "Elmyra Vaidakaviciene", code: "evaidakaviciene", … }
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
	public void test_CustomerCRM() {
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";
		
		/* TODO: need to add the following cookies which come from the LOGIN request
		 * Cookie: mscore.cacheid=1639045350603; JSESSIONID=0000MQArR-YNwmMQ-NzGQn-uvXY:-1
		 *  {
				"Request Cookies": {
					"JSESSIONID": "0000MQArR-YNwmMQ-NzGQn-uvXY:-1",
					"mscore.cacheid": "1639045350603"
				}
			}
		 */
		
		// TODO: get these from login response
		String sessionId = "";
		String cacheId = "";
		
		given()
			.contentType(ContentType.JSON)
			.contentType(ContentType.TEXT)
			.cookie("JSESSIONID", sessionId)
			.cookie("mscore.cacheid", cacheId)
			.param("id", "208329") // TODO: Where does this ID come from?
		.when()
			.post("/formData/CustomerCRM/")
		.then()
			.statusCode(200)
			.body("success", equalTo(true)) // TODO: Check for more stuff
			.log().all();
	}
}
