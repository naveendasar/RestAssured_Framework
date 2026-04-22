package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLPathValidator {
	
	private String getXmlResponseAsString(Response res) {
		 
		return res.getBody().asString();
	}

	public <T> T readXml(Response res, String path) {

		String response = getXmlResponseAsString(res);
		
		XmlPath xmlpath = new XmlPath(response);
		
		return  xmlpath.get(path);
	}

	public <T> List<T> readXmlList(Response res, String path) {

		String response = getXmlResponseAsString(res);
		
		XmlPath xmlpath = new XmlPath(response);
		
		return xmlpath.getList(path);
	}
	
	
}
