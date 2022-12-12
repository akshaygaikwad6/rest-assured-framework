package apiBasics;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.PayLoad;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "bookdata")

	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(PayLoad.addBook(isbn, aisle)).when().post("/Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(response);
		String idOfLib = js.get("ID");
		System.out.println(idOfLib);
		
	} 

	@DataProvider(name = "bookdata")
	//array=collection of elements

	//multidimensional array= collection of arrays
	public Object[][] getData() {
		return new Object[][] { { "jht", "6990" }, { "gssasds", "sd064pf" }, { "hdy7dye", "867" } };
	}
}
