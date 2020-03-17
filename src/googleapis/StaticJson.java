package googleapis;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.Test;

public class StaticJson {

	@Test
	public void addbook() throws IOException
	{
		RestAssured.baseURI="http://216.10.245.166";
		String res = given().header("Content-Type", "application/json").
		body(generateStringFromResource("C:\\Users\\Ravisha\\Desktop\\addBook.json")).
		
		when().post("/Library/Addbook.php ").then().assertThat().statusCode(200).
		extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	public static String generateStringFromResource(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
}
