package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkExceptions.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream fp;
	private final String path = "src/test/resources/config/config.properties";
	private final String stage_path = "src/test/resources/config/config_stage.properties";
	private final String prod_path = "src/test/resources/config/config_prod.properties";
	
	public Properties initProperties() throws IOException {
		
		
		// Properties file object is created
		prop = new Properties();

		// mvn clean install -Denv="stage"
		
		// we can specify stage / prod
		String envName = System.getProperty("env");

		System.out.println("Env name = " + envName);

		if (envName == null) {
			System.out.println("Env name is null so running test cases in default env file");
			fp = new FileInputStream(path);
			
		} 
			else {
			
			switch(envName.toLowerCase().trim()) {
			case "stage":
				
				System.out.println("Running all the tests in "+envName+" environment");
				fp = new FileInputStream(stage_path);
				break;
				
			case "prod":
				
				System.out.println("Running all the tests in "+envName+" environment");
				fp = new FileInputStream(prod_path);
				break;
				
			default:
				System.out.println("Incorrect Env Name = "+envName);
				throw new APIFrameworkException("Environment Name doesn't exist, select any of these following env = stage, prod");
			
			}
		}

		prop.load(fp);

		return prop;
		
	}

}
