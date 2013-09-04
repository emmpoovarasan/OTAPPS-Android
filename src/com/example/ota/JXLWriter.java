package com.example.ota;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
/*import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.DateFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;*/
import jxl.write.WriteException;

public class JXLWriter {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws WriteException 
	 */
	
	public static String writeExcelSheet(){
		File fp = new File("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls");
		Workbook wb;
		
		return null;
	}
	/*public static void main(String[] args) throws IOException, WriteException {
		// TODO Auto-generated method stub
		//File fp = new File(FilePath.getExternalPath());
		File fp = new File("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls");
		
		WritableWorkbook workbook = Workbook.createWorkbook(fp);
		System.out.println("No.Of.Sheets ====>"+workbook.getNumberOfSheets());
		
		//workbook.createSheet("ORDERNO", workbook.getNumberOfSheets()+1);
		//workbook.write();
		//workbook.close();
		
		File fp = new File(FilePath.getExternalPath());
		
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en","EN"));
		
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String theDate = df.format(now);
		System.out.println(theDate);
		
		WritableWorkbook workbook = Workbook.createWorkbook(fp, wbSettings);
		workbook.createSheet(theDate, 0);
		
		
		
		
		
	}*/

}
