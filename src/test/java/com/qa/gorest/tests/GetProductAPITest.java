package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPStatusCodes;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;


public class GetProductAPITest extends BaseTest{
	
	
	@BeforeMethod
	public void getProductApiSetUp() {
		
		rs = new RestClient(prop, baseURI);
	}
	
	
	@Test
	public void getAllProductsInfoTest() {
		
		Response res = rs.getRequest(GetProducts_EndPoint, true, false);
		
		int status_code = res.statusCode();
		Assert.assertEquals(status_code, HTTPStatusCodes.OK_200.getCode());
		
		JsonPathValidator js = new JsonPathValidator();
		List<Double> rate = js.readJsonList(res, "$..[?(@.rate>3)].rate");
		
		System.out.println("Ratings > 3 = "+rate);
		
		List<String> title = js.readJson(res, "$..[?(@.price>900)].title");
		System.out.println("title = "+title);
		
		
	}
	
	
	
	
	
}
