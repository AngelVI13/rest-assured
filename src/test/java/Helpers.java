import static io.restassured.RestAssured.baseURI;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;

import org.json.simple.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.runner.Request;

import static org.hamcrest.Matchers.*;

public class Helpers {

	public static Map<String, String> login(String credentials) throws IOException {
		// TODO: maybe define this once on the class ??
		baseURI = "https://dev11.nymbus.com/coreweb/controller/";

		URL url = new URL(baseURI+ "login");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);

		Map<String,String> arguments = new HashMap<>();
		arguments.put("login", "evaidakaviciene");
		arguments.put("password", "tfHL9tEEc5KTmkJJV5ks"); // This is a fake password obviously
		StringJoiner sj = new StringJoiner("&");
		for(Map.Entry<String,String> entry : arguments.entrySet())
			sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(entry.getValue(), "UTF-8"));
		byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		http.connect();
		try(OutputStream os = http.getOutputStream()) {
			os.write(out);
		}

		System.out.println(http.getInputStream());

		Map<String, String> params = new HashMap<String,String>();
		params.put("body", credentials);
//
//		Request request = RestAssured..request()
		
		Response response = RestAssured.post("https://dev11.nymbus.com/coreweb/controller/login", credentials);
		assert response.statusCode() == 200;
//		assert response.body("success", equalTo(true));
		
		return response.getCookies();		
	}
}
