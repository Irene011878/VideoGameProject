package testCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_VideoGameAPI {
	
	
	//FIRST REQUEST
	@Test
	public void Test_GET_AllVideoGames() {
		
		given()
		
		.when()
		  .get("http://localhost:8080/app/videogames")
		  
		
		.then()
		  .statusCode(200);
		  
	}
	@Test(priority=2)
	public void test_addNewVideoGame() {
		
		HashMap data=new HashMap();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2019-09-20t08:55:58.510Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res=
		given()
		  .contentType("application/json")
		  .body(data)
		
		.when()
		  .post("http://localhost:8080/app/videogames")
		
		.then()
		  .statusCode(200)
		  .log().body()
		  .extract().response();
		
	String jsonString=res.asString();
	AssertJUnit.assertEquals(jsonString.contains("Record Added Succesfully"), true);
		
		
	}
	
	@Test(priority=3)
	public void test_getVideoGame() {
		
		given()
		
		.when()
		  .get("http://localhost:8080/app/videogames/100")
			
		.then()
		  .statusCode(200)
		  .log().body()
		  .body("videoGame.id", equalTo("100"))
		  .body("videoGame.name", equalTo("Spider-Man"));
			
		
	}
	
	@Test(priority=4)
	public void test_UpdateVideoGame() {
		
		HashMap data=new HashMap();
		data.put("id", "100");
		data.put("name", "Pacman");
		data.put("releaseDate", "2019-09-20t08:57:58.510Z");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
		  .contentType("application/json")
		  .body(data)
		.when()
		  .put("http://localhost:8080/app/videogames/100")
		
		.then()
		  .statusCode(200)
		  .log().body()
		  .body("videoGame", equalTo("100"))
		  .body("videoGame", equalTo("Pacman"));
	}
	
	@Test(priority=5)
	public void test_DeleteVideoGame() {
		
		Response res=
				
		given()
		.when()
		  .delete("http://localhost:8080/app/videogames/100")
		.then()
		  .statusCode(200)
		  .log().body()
		  .extract().response();
		
		String jsonString=res.asString();
		AssertJUnit.assertEquals(jsonString.contains("Record Deleted Successfully"), true);  
	}

}
