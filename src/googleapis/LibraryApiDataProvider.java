package googleapis;

import static io.restassured.RestAssured.given;
import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LibraryApiDataProvider {

	@Test(dataProvider="bookdetails")
	public void dataProvider(String isbn,String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().relaxedHTTPSValidation().log().all()
				.header("Content-Type", "application/json")
				.body(PayLoad.addBook(isbn,aisle)).when()
				.post("/Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().response().asString();
		//System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String bookid = js.get("ID");
		System.out.println(bookid);
	}
	@DataProvider(name="bookdetails")
	public Object[][] getBookDetails() {
		return new Object[][]{{"acac","1133"},{"adad","1144"},{"aeae","1155"}};
	}
	
	

}
