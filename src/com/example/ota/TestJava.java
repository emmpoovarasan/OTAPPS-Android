package com.example.ota;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;


public class TestJava {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws IOException, BiffException {
		// TODO Auto-generated method stub
		File fp = new File("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS1.xls");
		//WritableWorkbook wb = Workbook.createWorkbook(fp);
		WorkbookSettings wbSettings = new WorkbookSettings();
		
		wbSettings.setLocale(new Locale("en", "EN"));
		
		Workbook wb = Workbook.getWorkbook(fp);
		//wb.getNumberOfSheets();
		System.out.println("Reports  =====> "+ wb.getNumberOfSheets());
		//wb.createSheet("Reports", wb.getNumberOfSheets()+1);
		//System.out.println("Reports  =====> "+ wb.getNumberOfSheets());
		
	}

}
