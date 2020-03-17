package googleapis;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class LibraryApi {

	
	@Test
	public void addBookApi()
	{
		//add book
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
		.body(PayLoad.addBook("abab", "1212"))
		.when().post("/Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String bookid = js.get("ID");
		System.out.println(bookid);
		
		//get book by book_id
		String response1 = given().relaxedHTTPSValidation().queryParam("ID", bookid).
		when().get("/Library/GetBook.php").then().assertThat().log().all().statusCode(200).
		extract().response().asString();
		System.out.println(response1);
		
		//delete book my book_id
		given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
		.body("{\r\n" + 
				" \r\n" + 
				"\"ID\" : \""+bookid+"\"\r\n" + 
				"\r\n" + 
				"} \r\n" + 
				"").when().post("/Library/DeleteBook.php").then().assertThat()
				.log().all().statusCode(200);
		
	}
	
	
}
