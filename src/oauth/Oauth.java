package oauth;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import googleapis.ReusableMethods;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Oauth {


	@Test
	public void getAccessToken()
	{
		String url="https://rahulshettyacademy.com/getCourse.php?state=verifyjdss&code=4%2FxgG52GRbToSmPmMVUlfoN9v26l9I0ElK9-UxGl2WoO_sN_xr3Skvp-Ml77ems3am9ZV2-tF-9gCorJFNPGRMaZQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=consent#";
		
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);
		
		String accesstokenResponse = given().relaxedHTTPSValidation().urlEncodingEnabled(false)
		.queryParams("code",code)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.queryParams("state", "verifyfjdss")
		.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
		.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(accesstokenResponse);
		JsonPath js = ReusableMethods.rawToJson(accesstokenResponse);
		String access_token = js.getString("access_token");
		System.out.println(access_token);
		
		String response = given().relaxedHTTPSValidation().queryParam("access_token", access_token)
				.expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
		
		
	}
	
}
