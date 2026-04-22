package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPStatusCodes;
import com.qa.gorest.utils.XMLPathValidator;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;

public class GetUserTest extends BaseTest {
	
	
	@BeforeMethod
	public void getUserSetup() {
		
		rs = new RestClient(prop, baseURI);
	}
	
	
	@Test(priority = 1, description = "To get all the users details")
	public void getAllUsersTest() {
		
		rs.getRequest(GoRest_EndPoint, true, true)
			.then().log().all()
			.assertThat()
			.statusCode(200);

	}
	
	@Test(priority = 2)
	public void getSingleUserTest() {
		
		rs.getRequest(GoRest_EndPoint+"8439973", true, true)
			.then().log().all()
			.assertThat()
			.statusCode(200)
			.body("id", equalTo(8439973));

	}
	
	@Test(priority = 3)
	public void getAllUsersUsingQueryPara() {
		
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("status", "active");
		queryParam.put("gender", "male");
		
		
		rs.getRequest(GoRest_EndPoint, null, queryParam, true, true)
			.then().log().all()
			.assertThat()
			.statusCode(HTTPStatusCodes.OK_200.getCode());
		
	}
	
	@Test(priority = 4)
	public void getAllUsersTestXml() {
		
		Response res = rs.getRequest(BaseTest.getAllUsersXml_EndPoint, true, false);
		
		int statuscode = res.statusCode();
		Assert.assertEquals(statuscode, HTTPStatusCodes.OK_200.getCode());
		
		XMLPathValidator xmlpath = new XMLPathValidator();
		List<String> names = xmlpath.readXmlList(res, "objects.object.name");
		
		System.out.println("names = "+names);
		
		
		String name = xmlpath.readXml(res, "objects.object[1].name");
		
		System.out.println("2nd name = "+name);
		
//		Assert.assertTrue(names.contains("Miss Gorakhnath Gowda"));
		
		
	}
	
	
}
