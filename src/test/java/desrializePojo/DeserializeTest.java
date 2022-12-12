package desrializePojo;

import io.restassured.RestAssured;
import jiraUtils.JiraPolicy;

import static io.restassured.RestAssured.*;

import java.util.Iterator;
import java.util.List;

import org.testng.annotations.Test;

public class DeserializeTest {

	@Test
	
	public  void getUserDataList() {
	
		
		RestAssured.baseURI="https://reqres.in";
		
		ListUsersApi data=given().relaxedHTTPSValidation().get("/api/users?page=2").as(ListUsersApi.class);
		
		System.out.println("list of data"+ data.getData());
		System.out.println("list of support"+ data.getSupport());
		
		List<DataPojo> list = data.getData();
		
		for (int i =0; i< list.size();i++) {
			System.out.println("This is my "+ i + "element"+list.get(1));
		}
		
		

	}
}
