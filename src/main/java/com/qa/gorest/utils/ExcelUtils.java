package com.qa.gorest.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {
	
	public final static String path = "src/test/resources/excel/UsersData.xlsx";
	public static Workbook wb;
	public static Sheet sheet;
	
	public static Object[][] getExcelTestData(String sheetname) throws EncryptedDocumentException, IOException {
		
		Object[][] data = null;
		
		FileInputStream fis = new FileInputStream(path);
		
		wb = WorkbookFactory.create(fis);
		sheet = wb.getSheet(sheetname);
		
		System.out.println("Total row = "+sheet.getLastRowNum()+" Columns = "+sheet.getRow(0).getLastCellNum());
		
		data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for(int i = 0; i < sheet.getLastRowNum(); i++) {
			
			for(int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		
		return data;
	}
	
	
	
	
	
	
	
	
	
	
}
