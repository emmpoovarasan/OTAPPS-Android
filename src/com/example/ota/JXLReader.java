package com.example.ota;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

import android.R.integer;
import android.util.Log;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class JXLReader {

  private String inputFile;
  private String sheetName;
  private String areaName;
  

  public void setInputFile(String inputFile, String sheetName, String areaName) {
    this.inputFile = inputFile;
    this.sheetName = sheetName;
    this.areaName = areaName;
  }
  public  ArrayList<String> getProductName() throws IOException{
	  Log.d("getProductName function", "Successfully entered to getProductName");
	  File inputWorkbook = new File(inputFile);
	  Workbook w;
	  //TreeSet treeSetProductName = new TreeSet();
	  ArrayList<String> arrayProductName = new ArrayList<String>();
	  try {
		w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(sheetName);
		for(int i=1;i<sheet.getRows();i++){
			Cell cellProductName = sheet.getCell(1, i);
			Cell cellInStock = sheet.getCell(2, i);
			Cell cellAmount = sheet.getCell(4, i);
			Log.d("Values of Products", cellProductName.getContents() +"@"+ cellInStock.getContents()+"@"+cellAmount.getContents());
			if(!"".equals(cellProductName.getContents())){
					//treeSetProductName.add(cellProductName.getContents()+"@"+cellInStock.getContents()+"@"+cellAmount.getContents());	
				arrayProductName.add(cellProductName.getContents()+"@"+cellInStock.getContents()+"@"+cellAmount.getContents());
			}
		}
		//arrayProductName.addAll(treeSetProductName);
	} catch (Exception e) {
		// TODO: handle exception
	}
	return arrayProductName;
  }
  public ArrayList<String> getCustomerName() throws IOException{
	  Log.d("getCustomerName function", "Successfully entered to getCustomerName");
	  File inputWorkbook = new File(inputFile);
	  Workbook w;
	  TreeSet<String> treeSetCustomerName = new TreeSet<String>();
	  ArrayList<String> arrayListCustomerName = new ArrayList<String>();
	  try {
		w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(sheetName);
		for(int i = 1; i<sheet.getRows();i++){
			Cell cellCustomer = sheet.getCell(1, i);
			Log.d("Values of arrayListCustomerName", cellCustomer.getContents());
			if(areaName.equals(cellCustomer.getContents())){
				treeSetCustomerName.add(cellCustomer.getContents());
				Log.d("I got customername ", cellCustomer.getContents());
			}
		}
		arrayListCustomerName.addAll(treeSetCustomerName);
	} catch (Exception e) {
		// TODO: handle exception
	}
	  return arrayListCustomerName;
  }
  
  public ArrayList<String> getShopName() throws IOException{
	  Log.d("getShopName function", "Successfully entered to getShopName");
	  File inputWorkbook = new File(inputFile);
	  Workbook w;
	  TreeSet<String> treeSetShopName = new TreeSet<String>();
	  ArrayList<String> arrayListShopName = new ArrayList<String>();
	  try {
		w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(sheetName);
		for(int i = 1; i<sheet.getRows();i++){
			Cell cellArea = sheet.getCell(2, i);
			Cell cellShop = sheet.getCell(1, i);
			Log.d("Values of AreaNames", areaName +"/"+ cellArea.getContents() +"/"+ cellShop.getContents());
			if(areaName.equals(cellArea.getContents())){
				treeSetShopName.add(cellShop.getContents());
				Log.d("I got shopname ", cellShop.getContents());
			}
		}
		arrayListShopName.addAll(treeSetShopName);
	} catch (Exception e) {
		// TODO: handle exception
	}
	  return arrayListShopName;
  }
  public ArrayList<String> getAreaName() throws IOException  {
    File inputWorkbook = new File(inputFile);
    Workbook w;
    TreeSet<String> treeSetAreaName = new TreeSet<String>();
    ArrayList<String> optionArr = new ArrayList<String>();   
    try {
      w = Workbook.getWorkbook(inputWorkbook);
      // Get the first sheet
      Sheet sheet = w.getSheet(sheetName);
      // Loop over first 10 column and lines
      
      //for (int j = 0; j < sheet.getColumns(); j++) {
        for (int i = 1; i < sheet.getRows(); i++) {
          Cell cell = sheet.getCell(1, i);
          treeSetAreaName.add(cell.getContents());
          Log.d("I got AreaName ", cell.getContents());
          /*CellType type = cell.getType();
          if (type == CellType.LABEL) {
            //System.out.println("I got a label " + cell.getContents());
            Log.d("I got a label ", cell.getContents());
            optionArr.add(cell.getContents());
          }

          if (type == CellType.NUMBER) {
            System.out.println("I got a number " + cell.getContents());
            Log.d("I got a number " , cell.getContents());
            
          }*/
        }
      //}
      optionArr.addAll(treeSetAreaName);
      
    } catch (BiffException e) {
      e.printStackTrace();
    }
    //return optionArr;
    return optionArr;
  }

  public boolean getLogin(String inputFile, String sheetName, String repName, String repPwd) throws IOException{
	  boolean loginStatus = false;
	  Log.d("getLogin function", "Successfully entered to getLogin");
	  File inputWorkbook = new File(inputFile);
	  Workbook w;
	  ArrayList<String> arrayListRepName = new ArrayList<String>();
	  try {
		w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(sheetName);
		for(int i = 1; i<sheet.getRows();i++){
			Cell cellRepName = sheet.getCell(1, i);
			Cell cellRepPwd = sheet.getCell(2, i);
			Log.d("Values of repName&repPwd", cellRepName.getContents() +"/"+ cellRepPwd.getContents());
			if(repName.equals(cellRepName.getContents().toUpperCase()) && repPwd.equals(cellRepPwd.getContents().toUpperCase())){
				arrayListRepName.add(cellRepName.getContents()+"/"+cellRepPwd.getContents());
				Log.d("RepName & RepPwd is matched ", cellRepName.getContents()+"/"+cellRepPwd.getContents());
				loginStatus = true;
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	  return loginStatus;
	  
  }
  
  public static ArrayList<String> getMyExcelSheetNames() throws IOException{
	  ArrayList<String> arraySheetName = new ArrayList<String>();
	  //File inputWorkbook = new File(FilePath.getExternalPath());
	  File inputWorkbook = new File("C:\\Users\\NITHYA\\git\\OTAPPS-Android\\OTA\\ORDER APPS.xls");
	  Workbook w;
	  try {
		w = Workbook.getWorkbook(inputWorkbook);
		w.getSheetNames();
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	  return null;
  }

//public static void main(String[] args) throws IOException {
//	JXLReader test = new JXLReader();
//  test.setInputFile("c:/temp/lars.xls");
//  test.read();
//}

} 