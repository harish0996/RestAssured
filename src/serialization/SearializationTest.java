package serialization;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class SearializationTest {

	public static void main(String[] args) {
		GAddPlace ap = new GAddPlace();
		ap.setAccuracy(50);
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setAddress("29, side layout, cohen 09");
		ap.setWebsite("http://google.com");
		ap.setLanguage("French-IN");
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		ap.setTypes(mylist);
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ap.setLocation(l);

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123")
				.body(ap).when().post("/maps/api/place/add/json").then()
				.assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);

	}

}
