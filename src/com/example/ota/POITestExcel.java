package com.example.ota;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jxl.Cell;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class POITestExcel {
	public static String poiDynamicCreateSheet(String mySheet) throws IOException, FileNotFoundException{
		String test=null;
		String filename = "C:\\Users\\NITHYA\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls";
		//"C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls"
		File fp = new File(filename);
		FileInputStream is = new FileInputStream(filename);
		HSSFWorkbook workbook = null;
		if(fp.exists() == true){
			workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.createSheet(mySheet);
			test = mySheet;
		}
		FileOutputStream out = new FileOutputStream(new File(filename));
		workbook.write(out);
		out.close();
		return test;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Starts");
		File fp = new File("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls");
		FileInputStream is = new FileInputStream("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls");
		HSSFWorkbook workbook;
		if(fp.exists() == true){
			System.out.println("file exist==="+String.valueOf(fp.exists()));
			
			//FileOutputStream fout = new FileOutputStream(fp);
			workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.createSheet("Report");
			
			Map<String, Object[]> data = new HashMap<String, Object[]>();
			data.put("1", new Object[] {"Emp No.", "Name", "Salary"});
			data.put("2", new Object[] {1d, "John", 1500000d});
			data.put("3", new Object[] {2d, "Sam", 800000d});
			data.put("4", new Object[] {3d, "Dean", 700000d});
			Set<String> keyset = data.keySet();
			int rownum = 0;
			for (String key : keyset) {
			    HSSFRow row = sheet.createRow(rownum++);
			    Object [] objArr = data.get(key);
			    int cellnum = 0;
			    for (Object obj : objArr) {
			        HSSFCell cell = row.createCell((short) cellnum++);
			        if(obj instanceof Date)
			            cell.setCellValue((Date)obj);
			        else if(obj instanceof Boolean)
			            cell.setCellValue((Boolean)obj);
			        else if(obj instanceof String)
			            cell.setCellValue((String)obj);
			        else if(obj instanceof Double)
			            cell.setCellValue((Double)obj);
			    }
			}
			
			is.close();
			
			workbook.createSheet("ABD");
			
			
			FileOutputStream out = new FileOutputStream(new File("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Ends");
					
			System.out.println("Get Sheet Counts =======> "+workbook.getNumberOfSheets());
			
			System.out.println("Get Sheet Counts =======> "+workbook.getNumberOfSheets());
			int i = 0;
			while(workbook.getNumberOfSheets() != 0){
				System.out.println(workbook.getSheetName(i));
				i++;
			}
			System.out.println("Ends");
			
			//workbook.write(fout);
			//is.close();
			
		}else{
			System.out.println("file exist==="+String.valueOf(fp.exists()));
		}

	}
*/
}
