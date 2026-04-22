package com.qa.gorest.base;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;


public class BaseTest {
	
	// End points details
	public final static String GoRest_EndPoint = "/public/v2/users/";
	public final static String HTTP_EndPoint = "/get";
	public final static String AmadeusGetToken_EndPoint = "/v1/security/oauth2/token";
	public final static String AmadeusDetails_EndPoint = "/v2/reference-data/urls/checkin-links";
	public final static String GetProducts_EndPoint = "/products";
	public final static String getAllUsersXml_EndPoint = "/public/v2/users.xml";
	
	public ConfigurationManager cm;
	public Properties prop;
	public RestClient rs;
	public String baseURI;
	
	@Parameters({"baseURI"})
	@BeforeTest
	public void setUp(String baseURI) throws IOException {
//	public void setUp() throws IOException {

		
		// To generate the Allure Reports
		RestAssured.filters(new AllureRestAssured());
		
		cm = new ConfigurationManager();
		prop = cm.initProperties();
		
		this.baseURI = baseURI;
		
//		this.baseURI = prop.getProperty(baseURI);
		
//		String baseURI = (String) prop.getProperty("baseURI");
		
//		rs = new RestClient(prop, baseURI);
		
		
		
		
	}
	
}
