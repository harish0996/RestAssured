package googleapis;

import org.testng.Assert;

import files.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(PayLoad.getComplexJson());
		// course size
		int courseSize = js.get("courses.size()");
		System.out.println(courseSize);

		// purchase amount
		int pAmount = js.get("dashboard.purchaseAmount");
		System.out.println(pAmount);

		// first course title
		String cTitle = js.get("courses[0].title");
		System.out.println(cTitle);
		
		//course title and price
		for(int i=0;i<courseSize;i++)
		{
			System.out.println(js.get("courses["+i+"].title"));
			System.out.println(js.get("courses["+i+"].price"));
		}
		
		//no of copies sold by RPA
		int rCopies = js.get("courses[2].copies");
		System.out.println(rCopies);
		int sum=0;
		for(int i=0;i<courseSize;i++)
		{
			 int cCopies=js.get("courses["+i+"].copies");
			int cPrice=js.get("courses["+i+"].price");
			int aAmount = cCopies * cPrice;
			sum= sum + aAmount;
			
		}
		System.out.println(sum);
		Assert.assertEquals(sum, pAmount);
		
	}

}
