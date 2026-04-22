package com.qa.gorest.tests;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.pojo.UserPOJO;
import com.qa.gorest.utils.ExcelUtils;
import com.qa.gorest.utils.HTTPStatusCodes;
import com.qa.gorest.utils.StringUtils;
import static org.hamcrest.Matchers.*;


public class CreateUserTest extends BaseTest {
	
	
	@BeforeMethod
	public void createtUserSetup() {
		
		rs = new RestClient(prop, baseURI);
	}
	
	
	@DataProvider
	public Object[][] getUserTestData() {
		
		Object[][] obj = {{"naveen", "male", "active"}, {"chicken", "female", "inactive"}, {"fish", "female", "active"}};
		
		return obj;
	}
	
	
//	@DataProvider
//	public Object[][] getExcelData() throws EncryptedDocumentException, IOException {
//		
//		Object[][] obj = ExcelUtils.getExcelTestData("Sheet1");
//		
//		return obj;
//	}
	
	
	
	@Test(dataProvider = "getUserTestData")
	public void createUserTest(String name, String gender, String status) {
		
		UserPOJO up = new UserPOJO(name, StringUtils.generateEmailId(), gender, status);
		
		Integer id = rs.postRequest(GoRest_EndPoint, "json", up, null, null, true, true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPStatusCodes.CREATED_201.getCode())
		.extract().path("id");
		
		System.out.println("Id = "+id);
		
		
		rs.getRequest(GoRest_EndPoint+id, null, null, true, false)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPStatusCodes.OK_200.getCode());
		
		
	}
	
	
	
}
