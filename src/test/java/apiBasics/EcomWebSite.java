package apiBasics;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import pojoForEcom.LoginRequest;
import pojoForEcom.LoginResponse;
import pojoForEcom.OrderDetail;
import pojoForEcom.Orders;
import utils.PropertyFile;

import static io.restassured.RestAssured.given;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import files.Specification;

public class EcomWebSite {

	public static String token;
	public static String userId;
	public static String productId;

	@Test(priority = 1)

	public void getTokenAndUserId() {

		/*****
		 * VIMP =For SSL certification use this = given().relaxedHTTPSValidation()
		 ********/

//		new PropertyFile();
		LoginRequest login = new LoginRequest();
		login.setUserEmail(PropertyFile.getValue("userEmail"));
		login.setUserPassword(PropertyFile.getValue("userPassword"));

		RequestSpecification reqLogin = given().spec(Specification.loginRequest()).body(login);
		LoginResponse loginRes = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response()
				.as(LoginResponse.class);
		token = loginRes.getToken();
		userId = loginRes.getUserId();

	}

	// Add product
	@Test(priority = 2)

	public void addProduct() {
		RequestSpecification addProductBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBase).param("productName", "Laptop")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500")
				.param("productDescription", "Lenova").param("productFor", "men")
				.multiPart("productImage", new File(System.getProperty("user.dir")+"/src/test/java/data/sampll.jpg"));

		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		productId = js.get("productId");

	}
	// Create Order
	@Test(priority = 3 )
	public void createOrder() {
		
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);

		List<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
		orderDetailsList.add(orderDetail);

		Orders order = new Orders();
		order.setOrders(orderDetailsList);

		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(order);

		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();
		System.out.println(responseAddOrder);
		JsonPath js1 = new JsonPath(responseAddOrder);
		Assert.assertEquals("Order Placed Successfully", js1.get("message"));

	}

	@AfterClass
	public void deleteProduct() {
		// Delete Product

		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();

		RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId",
				productId);

		String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(deleteProductResponse);

		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));

	}
}
