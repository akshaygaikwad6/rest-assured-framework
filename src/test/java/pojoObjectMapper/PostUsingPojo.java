package pojoObjectMapper;

import static io.restassured.RestAssured.given;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.juneau.json.JsonParser;
import org.apache.juneau.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import files.Specification;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojoJsonserializer.PojoClass;



public class PostUsingPojo {

	public static void main(String[] args) throws IOException, ParseException {

//		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Serialization

		AddPlace add = new AddPlace(); 
		add.setAccuracy(50);
		add.setAddress("PUNE");
		add.setLanguage("Marathi");
		add.setPhone_number("(+91) 1231231234");
		add.setWebsite("https://akshayGaikwad.com");
		add.setName("Akshay");

		List<String> list = new ArrayList<String>();
		list.add("webdriverIo");
		list.add("selenium");
		add.setTypes(list);

		Location loc = new Location();
		loc.setLat(94);
		loc.setLng(322);
		loc.setPng(76);
		Location loc1 = new Location();
		loc1.setAbc(18);
		loc1.setDfsdf(89);
		loc1.setSdfsdf(67);
		
		

		add.setLocation(loc);
		add.setLocation(loc1);
		/******************SERIALIZATION **************************/
		ObjectMapper mapper = new ObjectMapper();
		String mapJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(add);
		System.out.println("serialization"+mapJson);
		
		/******************DESERIALIZATION OPTION -1 **************************/
		AddPlace addmapper = mapper.readValue(mapJson, AddPlace.class);
		System.out.println("Deserialization of accuracy "+addmapper.getAccuracy());
		System.out.println("Deserialization of types "+addmapper.getTypes());
		
		/******************DESERIALIZATION OPTION -2 **************************/
		JsonParser jsonParser = JsonParser.DEFAULT;
		AddPlace pro = jsonParser.parse(mapJson, AddPlace.class);
		System.out.println("Deserialization of Languages  ="+ pro.getLanguage());
		
		
		String response = given().log().all().spec(Specification.request()).body(mapJson).when()
				.post("/maps/api/place/add/json").then().log().all().spec(Specification.response()).extract().response()
				.asString();

		System.out.println("Responce=" + response);
		JsonPath js = new JsonPath(response);
		String PlaceID = js.getString("place_id");
		String status = js.getString("status");
		System.out.println("place id="+PlaceID);
		System.out.println("status of API="+status);
		
		
	}

}
