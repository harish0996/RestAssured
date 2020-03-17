package googleapis;

import org.testng.Assert;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GAddPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//add place api
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().relaxedHTTPSValidation().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").
		body(PayLoad.getAddPlace()).when().post("/maps/api/place/add/json").
				then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)")
				.extract().response().asString();
		
		//JsonPath js=new JsonPath(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);
		
		//Update Api
		String newAddress="70 Summer walk, Africa";
		
		 String response1 = given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body("{\r\n" + 
				"\"place_id\":\""+place_id+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
				.when().put("/maps/api/place/update/json").
				then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
		System.out.println(response1);
		
		//Get Api
		String response2 = given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("/maps/api/place/get/json").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		//JsonPath js1=new JsonPath(response2);
		JsonPath js1 = ReusableMethods.rawToJson(response2);
		String actualAddress = js1.getString("address");
		Assert.assertEquals(actualAddress, newAddress);
		
	}

}
