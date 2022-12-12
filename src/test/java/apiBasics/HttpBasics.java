package apiBasics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.Assert;

import files.PayLoad;
import files.ReUsableMethods;

public class HttpBasics {

	public static void main(String[] args) throws IOException {

		// Validate if add place API is working or not

		// Given - all input details (query Param ,header , body )
		// When - Submit the API (Resource and HTTP methods)
		// Then - Validate the response(asserthat , statuscode ,header ,body )

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(ReUsableMethods.GenerateStringFromResource("D:\\RestApiDemoProject\\src\\files\\addPlace.json")).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.header("server", "Apache/2.4.41 (Ubuntu)").body("scope", equalTo("APP")).extract().response()
				.asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);// for parsing json
		String placeId = js.getString("place_id");
		System.out.println("place id of the field is " + placeId);

		// Update Place
		String newAddress = "Summer Walk, Aurangabad";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// get place

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();
		
//		JsonPath js1 = new JsonPath(getPlaceResponse);
		JsonPath js1 =ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress =js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, "Summer Walk, Aurangabad");

	}
}
