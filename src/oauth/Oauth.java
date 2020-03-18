package oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import googleapis.ReusableMethods;

import org.testng.Assert;
import org.testng.annotations.Test;

import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
import static io.restassured.RestAssured.*;

public class Oauth {

	@Test
	public void getAccessToken() {
		String[] courseTitle={"Selenium Webdriver Java","Cypress","Protractor"};
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyjdss&code=4%2FxgGO-I1kzVOJhwKCPNVisWNz5mSLFXn0eR0vXv6EFdx_ipKc_DEaSS6eqikl_6EfqpS5t4JIS37p8sWim7b6P-0&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none#";

		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);

		String accesstokenResponse = given()
				.relaxedHTTPSValidation()
				.urlEncodingEnabled(false)
				.queryParams("code", code)
				.queryParams("client_id",
						"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri",
						"https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code")
				.queryParams("state", "verifyfjdss")
				.queryParams("session_state",
						"ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
				.when().post("https://www.googleapis.com/oauth2/v4/token")
				.asString();
		System.out.println(accesstokenResponse);
		JsonPath js = ReusableMethods.rawToJson(accesstokenResponse);
		String access_token = js.getString("access_token");
		System.out.println(access_token);

		GetCourse gc = given().relaxedHTTPSValidation()
				.queryParam("access_token", access_token).expect()
				.defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCourse.class);

		System.out.println(gc.getLinkedIn());

//		List<Api> apicourses = gc.getCourses().getApi();
//
//		for (Api api : apicourses) {
//
//			System.out.println(api.getPrice());
//			System.out.println(api.getCourseTitle());
//			if(api.getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java"))
//			{
//				System.out.println("present");
//			}
//		}
		List<String> actual = Arrays.asList(courseTitle);
		List<String> expected=new ArrayList<String>();
		
		
		List<WebAutomation> wbcourses = gc.getCourses().getWebAutomation();
		for(WebAutomation wb:wbcourses)
		{
			expected.add(wb.getCourseTitle());
		}
		
		Assert.assertTrue(actual.equals(expected));
	}

}
