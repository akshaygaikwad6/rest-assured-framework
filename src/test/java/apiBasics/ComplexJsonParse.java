package apiBasics;

import files.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		
		JsonPath js=new JsonPath(PayLoad.CoursePrice());
		//Print No of courses returned by API
		int count=	js.getInt("courses.size()");
		System.out.println("count of courses "+count);
		
		//Print Purchase Amount
		int totalAmount= js.getInt("dashboard.purchaseAmount");
		System.out.println("total purchase amount "+totalAmount);
		
		//print first title of course 
		
		String firstTitle = js.getString("courses[0].title");
		System.out.println("First title of course "+firstTitle);
		
		  //Print All course titles and their respective Prices
		  
		  for(int i=0;i<count;i++)
		  {
			  String courseTitles=js.get("courses["+i+"].title");
			  System.out.println(js.get("courses["+i+"].price").toString());
			  
			  System.out.println(courseTitles);
			  
		  }
		  

		  //Print no of copies sold by Cypress Course
		  
		 System.out.println("Print no of copies sold by Cypress Course");
		 
		 for(int i=0;i<count;i++)
		 {
			  String courseTitles=js.get("courses["+i+"].title");
			  if(courseTitles.equalsIgnoreCase("Cypress"))
			  {
				  int copies=js.get("courses["+i+"].copies");
				  System.out.println(copies);
				  break;
			  }
			
			  
		 }
	}

}
