package com.example.ota;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
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
	 * @throws BiffException 
	 * @throws IOException 
	 * @throws WriteException 
	 */
	
	public static ArrayList<String> otaWriteExcelSheet() throws BiffException, IOException{
		String filename;
		//"C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls"
		//System.out.println(POITestExcel.poiDynamicCreateSheet("VIA"));
		//System.out.println(POITestExcel.poiDynamicCreateSheet("VIA1"));
		//filename = "C:\\Users\\NITHYA\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls";
		//filename = "C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls";
		filename = FilePath.getExternalPath();
		File fp = new File(filename);
		Workbook wb;
		wb = Workbook.getWorkbook(fp);
		Sheet st = null;
		
		ArrayList<String> arr = new ArrayList<String>();
		for(int i = 0; i<wb.getNumberOfSheets();i++){
			st = wb.getSheet(i);
			arr.add(st.getName());
		}
		
		return arr;
		
	}
	public static void main(String[] args) throws BiffException, IOException{
		
		ArrayList<String> test = otaWriteExcelSheet();
		
		Iterator itr = test.iterator();
		while (itr.hasNext()) {
			Object type = itr.next();
			//test.add((String)type);
			//System.out.println(type+",");
		}
		
		System.out.println(test);
		
		/*String filename1 = "C:\\Users\\NITHYA\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls";
		File fp1 = new File(filename1);
		Workbook wb1;
		wb1 = Workbook.getWorkbook(fp1);
		Sheet s = null;
		
		for(int i = 0; i<wb1.getNumberOfSheets();i++){
			s = wb1.getSheet(i);
			System.out.println(s.getName()+",");
			
			//System.out.println(wb1.getSheetNames());
			
		}
*/		
		
		
		
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
