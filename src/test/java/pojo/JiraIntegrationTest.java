package pojo;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import jiraUtils.JiraPolicy;

public class JiraIntegrationTest {

	@JiraPolicy (logTicketReady=true)
	
	@Test

	public void googleAuthDemo() {

//// get authorization code 
//
		String url = "https://www.googleapis.com/oauth2/v4/token?code=4%2F0AY0e-g6UUD1-sMKIpsxzb520ZVflKe7i8xD_oQk6X3t_fjDqhaROnrJncgGX7dEF0jllVg&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&client_secret=erZOWM9g3UtwNRj340YYaK_W&redirect_uri=https://rahulshettyacademy.com/getCourse.php&grant_type=authorization_code#";
		String partialcode = url.split("code=")[1];

		String code = partialcode.split("&scope")[0];

		System.out.println(code);

		// get the access token

		String response =

				given()

						.urlEncodingEnabled(true)

						.queryParams("code", code)

						.queryParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

						.queryParams("grant_type", "authorization_code")

						.queryParams("state", "verifyfjdss")

						.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

						// .queryParam("scope",
						// "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.ph")

						.when()

						.post("https://www.googleapis.com/oauth2/v4/token").then().log().all().extract().response()
						.asString();

		System.out.println("here is the response " + response);

		JsonPath jsonPath = new JsonPath(response);

		String accessToken = jsonPath.getString("access_token");

		System.out.println("API access token is" + accessToken);

		/* Deserialization */

		GetCourse r2 = given().log().all().contentType("application/json").

				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)

				.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

		System.out.println(r2.getLinkedIn());
		Assert.assertEquals(r2.getLinkedIn(), "akshay362Jiratest");

	}
}