package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPStatusCodes;

public class HTTPBinOrgsTest extends BaseTest {
	
	
	@BeforeMethod
	public void getRequestHTTPBinOrgSetup() {
		
		rs = new RestClient(prop, baseURI);
	}
	
	
	@Test
	public void getRequest() {
		
		rs.getRequest(HTTP_EndPoint, true, false)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPStatusCodes.OK_200.getCode());
		
	}
	
	
}
