package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPStatusCodes;

public class AmadeusOAuth2 extends BaseTest {
	
	String access_token;
	
	@Parameters({"grant_type", "client_id", "client_secret"})
	@BeforeMethod
	public void getAccessTokenSetUp(String grant_type, String client_id, String client_secret) {
		
		rs = new RestClient(prop, baseURI);
		access_token = rs.getAccessTokenUsingOAuth2(AmadeusGetToken_EndPoint, grant_type, client_id, client_secret);
	}
	
	
	@Test
	public void getFlightDetails() {
		
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Authorization", "Bearer "+access_token);
		
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("airlineCode", "IB");
		
		rs.getRequest(AmadeusDetails_EndPoint, headersMap, queryMap, true, false)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPStatusCodes.OK_200.getCode());
		
	}
	
	
	
}
