package com.qa.gorest.client;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.frameworkExceptions.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	
//	private static final String BASE_URI = "https://gorest.co.in";
	
//	private static final String BEARER_TOKEN = "588112c7b79863b6c9e13964ff3a42cfada3df25e68cf177cdf2f459ae2b15ae";
	
	private static RequestSpecBuilder specBuilder;
	
	private Properties prop;
	private String baseURI;
	
	// initialize the object of RequestSpecBuilder class
	public RestClient(Properties prop, String baseURI) {
		
		this.prop = prop;
		this.baseURI = baseURI;
		specBuilder = new RequestSpecBuilder();
	}
	
	
//******************** Common Re-Useable Methods *******************************************
	
	// Creating Authorization so the we can reuse in tests
	public void addAuthorization() {
		
		specBuilder.addHeader("Authorization", "Bearer "+prop.getProperty("tokenID"));
	}
	
	private static void setRequestContentType(String contentType) {
		
		System.out.println("ContentType = "+contentType);
		
		switch(contentType.toLowerCase().trim()) {
		
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
			
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
			
		case "html":
			specBuilder.setContentType(ContentType.HTML);
			break;		
			
		case "multipart":
			specBuilder.setAccept(ContentType.MULTIPART);
			break;
			
		default:
			System.out.println("This ContentType is not Supported");
			
			throw new APIFrameworkException("===INVALID CONTENT TYPE===");
		}	
	}
	

//***************** Request Specification Methods *****************************
	
	// Setting up the BaseURI & Authorization
	public RequestSpecification createRequestSpec(boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {
			
			addAuthorization();
		}
		return specBuilder.build();
		
	}
	
	
	// Setting BasURI, Authorization, Headers Map
	public RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);

		if(includeAuth) {
			
			addAuthorization();
		}
		
		if(headersMap != null) {
			
			specBuilder.addHeaders(headersMap);
		}
		
		return specBuilder.build();
	}
	
	
	// Setting BasURI, Authorization, Headers Map, Query Parameters
	public RequestSpecification createRequestspec(Map<String, String> headersMap, Map<String, String> queryMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {
			
			addAuthorization();
		}
		
		if(headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		
		if(queryMap != null) {
			specBuilder.addQueryParams(queryMap);
		}
		
		return specBuilder.build();
	}
	
	
	// setting up the method with RequestBody, ContentType for POST request
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {
			
			addAuthorization();
		}
		
		setRequestContentType(contentType);
		
		if(requestBody != null) {
			
			specBuilder.setBody(requestBody);
		}
		
		
		return specBuilder.build();
	}
	
	
	// setting up the method with RequestBody, ContentType for POST request
	// with Headers
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {
			
			addAuthorization();
		}
		
		setRequestContentType(contentType);
		
		if(requestBody != null) {
			
			specBuilder.setBody(requestBody);
		}
		
		if(headersMap != null) {
			
			specBuilder.addHeaders(headersMap);
		}
		
		
		return specBuilder.build();
	}
	
	
	// setting up the method with RequestBody, ContentType for POST request
	// with Headers & Query Parameters
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, Map<String, String> queryMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {
			
			addAuthorization();
		}
		
		setRequestContentType(contentType);
		
		if(requestBody != null) {
			
			specBuilder.setBody(requestBody);
		}
		
		if(headersMap != null) {
			
			specBuilder.addHeaders(headersMap);
		}
		
		if(queryMap != null) {
			
			specBuilder.addQueryParams(queryMap);
		}
		
		return specBuilder.build();
	}
	
	
//****************** HTTP Get Methods using Wrapper Method ****************************
	
	// Use these methods for Get calls passing serviceUrl & boolean value
	// for printing log
	public Response getRequest(String serviceUrl, boolean log, boolean includeAuth) {
		if(log) {
			Response res = RestAssured.given().log().all()
					.spec(createRequestSpec(includeAuth))
					.when()
					.get(serviceUrl);
		
			return res;
		}
		else {
			Response res = RestAssured.given()
					.spec(createRequestSpec(includeAuth))
					.when()
					.get(serviceUrl);
		
			return res;
		}
	}
	
	
	// Use these methods for Get calls passing serviceUrl, boolean value
	// and Headers
	public Response getRequest(String serviceUrl, Map<String, String> headersMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
					.spec(createRequestSpec(headersMap, includeAuth))
					.when()
					.get(serviceUrl);
		
			return res;
		}
		else {
			Response res = RestAssured.given()
					.spec(createRequestSpec(headersMap, includeAuth))
					.when()
					.get(serviceUrl);
		
			return res;
		}
	}
	
	// Use these methods for Get calls passing serviceUrl, boolean value,
	// Headers and query parameters
	public Response getRequest(String serviceUrl, Map<String, String> headersMap, Map<String, String> queryMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestspec(headersMap, queryMap, includeAuth))
							.when()
							.get(serviceUrl);
		
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestspec(headersMap, queryMap, includeAuth))
							.when()
							.get(serviceUrl);
		
			return res;
		}
	}	
	
	
//*************************** HTTP Post Methods ***************************************
	
	
	// Use these methods for Post calls passing serviceUrl, boolean value
	// contentType and Body 
	public Response postRequest(String serviceUrl, String contentType, Object requestBody, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, includeAuth))
							.when()
							.post(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, includeAuth))
							.when()
							.post(serviceUrl);
	
			return res;
		}
	}
	
	
	// Use these methods for Post calls passing serviceUrl, boolean value
	// contentType, headers and Body 
	public Response postRequest(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
							.post(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
							.post(serviceUrl);
	
			return res;
		}
	}
	
	
	// Use these methods for Post calls passing serviceUrl, boolean value
	// contentType, headers, query Parameters and Body 
	public Response postRequest(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, Map<String, String> queryMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							.when()
							.post(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							.when()
							.post(serviceUrl);
	
			return res;
		}
	}
	
	
//*************************** HTTP Put Methods ***************************************	
	
	// Use these methods for Put calls passing serviceUrl, boolean value
	// contentType and Body
	public Response putRequest(String serviceUrl, String contentType, Object requestBody, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType,  includeAuth))
							.when()
							.put(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, includeAuth))
							.when()
							.put(serviceUrl);
	
			return res;
		}
	}
	
	
	// Use these methods for Put calls passing serviceUrl, boolean value
	// contentType, headers and Body 
	public Response putRequest(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
							.put(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
							.put(serviceUrl);
	
			return res;
		}
	}
	
	
	// Use these methods for Put calls passing serviceUrl, boolean value
	// contentType, headers, query Parameters and Body 
	public Response putRequest(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, Map<String, String> queryMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							.when()
							.put(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							.when()
							.put(serviceUrl);
	
			return res;
		}
	}	
	
	
	
//*************************** HTTP Patch Methods ***************************************	
	
	
	// Use these methods for Patch calls passing serviceUrl, boolean value
	// contentType and Body
	public Response patchRequest(String serviceUrl, String contentType, Object requestBody, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, includeAuth))
							.when()
							.patch(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, includeAuth))
							.when()
							.patch(serviceUrl);
	
			return res;
		}
	}
	
	
	// Use these methods for Patch calls passing serviceUrl, boolean value
	// contentType, headers and Body 
	public Response patchRequest(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
							.patch(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
							.patch(serviceUrl);
	
			return res;
		}
	}
	
	
	// Use these methods for Patch calls passing serviceUrl, boolean value
	// contentType, headers, query Parameters and Body 
	public Response patchRequest(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, Map<String, String> queryMap, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							.when()
							.patch(serviceUrl);
			
			return res;
		}
		else {
			Response res = RestAssured.given()
							.spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							.when()
							.patch(serviceUrl);
	
			return res;
		}
	}	
	
	
//*************************** HTTP Delete Methods ***************************************	
	
	
	// Use this method for Delete calls passing serviceUrl, boolean value
	// for printing log
	public Response deleteRequest(String serviceUrl, boolean log, boolean includeAuth) {
		
		if(log) {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(includeAuth))
							.when()
							.delete(serviceUrl);
			
			return res;
		} 
		else {
			Response res = RestAssured.given().log().all()
							.spec(createRequestSpec(includeAuth))
							.when()
							.delete(serviceUrl);
			
			return res;
		}
	}
	
	
//*************************OAuth 2.0 Work Flow Token Generation***********************
	
	
	public String getAccessTokenUsingOAuth2(String serviceUrl, String grant_type, String client_id, String client_secret) {
		
		RestAssured.baseURI = "https://test.api.amadeus.com";
		
		String access_token = RestAssured.given().log().all()
				.contentType(ContentType.URLENC)
				.formParam("grant_type", grant_type)
				.formParam("client_id", client_id)
				.formParam("client_secret", client_secret)
				.when()
				.post(serviceUrl)
				.then().log().all()
				.assertThat()
				.statusCode(200)
				.extract().path("access_token");
		
		System.out.println("Access Token = "+access_token);
		
		return access_token;
		
	}
	
	
	
}
