package com.example.ota;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.read.biff.BiffException;
import jxl.read.biff.CellValue;

import android.R.bool;
import android.R.color;
import android.R.integer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class OrderPage extends Activity {
	// create variables for Button, Spinner, ScrollView, LinearLayout, TableLayout & HorizontalScrollView
	Button btnLoadProductList, btnSaveOrder, btnDashBoard, btnLogOut;
	Spinner spnBeatName, spnShopName;//, spnCustomerName, spnProductName;
	ScrollView scrollOrderPage;
	LinearLayout lnrLayContentViewOrderPage;
	TableLayout tblLoadProductList;
	HorizontalScrollView hsv;
	TableRow tr1 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_page);

		scrollOrderPage = (ScrollView)findViewById(R.id.scroll_view); // assign ScrollView
		lnrLayContentViewOrderPage = (LinearLayout)findViewById(R.id.content); // assign LinearView
		
		fnloadBeatName(); // Load Beat Name
		
		fnloadShopName(); // Load Shop Name
		
		// code for HorizontalScrolling
		hsv = (HorizontalScrollView)findViewById(R.id.horizontalView);
		hsv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				hsv.pageScroll(hsv.getMaxScrollAmount());
				
			}
		});
		// Scroll for Vertical Scrolling 
		scrollOrderPage.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollOrderPage.pageScroll(scrollOrderPage.getMaxScrollAmount());
			}
		});
		
		// load all products from excel file
		btnLoadProductList = (Button)findViewById(R.id.btnLoadProductList);
		btnLoadProductList.setOnClickListener(listenerLoadProductList);
		Log.d("Loaded", getClass().getSimpleName());
		// button for save order
		btnSaveOrder = (Button)findViewById(R.id.btnSaveOrder);
		btnSaveOrder.setOnClickListener(listenerSaveOrder);
		// button for dashboard
		btnDashBoard = (Button)findViewById(R.id.btnDashboard);
		btnDashBoard.setOnClickListener(listenerDashBoard);
		// button for logout
		btnLogOut = (Button)findViewById(R.id.btnLogOut);
		btnLogOut.setOnClickListener(listenerLogOut);
	}
	// set OnClickListener for logout
	private OnClickListener listenerLogOut = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myInt = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(myInt);
		}
	};
	// set OnClickListener for Dashboard
	private OnClickListener listenerDashBoard = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(myInt);
		}
	};
	// set OnClickListener for Load Product List
	private OnClickListener listenerLoadProductList = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d("Click LoadProduct List", "Click LoadProduct List");
			fngetProducts("PRODUCT NAME",FilePath.getExternalPath());
		}
	};

	// load all products to table layout from excel
	private void fngetProducts(String SheetName, String fileFullPath){
		File f = new File(fileFullPath);
		Workbook w;
		try {
			w = Workbook.getWorkbook(f);
			Sheet sheet = w.getSheet(SheetName);
			
			tblLoadProductList = (TableLayout)findViewById(R.id.tableLayoutOrder);
			TableRow row = null;
			// load values from excel to tablelayout
			for(int i=1;i<sheet.getRows();i++){
				Cell cellProductName = sheet.getCell(1, i);
				Cell cellInStock = sheet.getCell(2, i);
				Cell cellAmount = sheet.getCell(4, i);
				Log.d("Values of Products in loading tables"+i, cellProductName.getContents() +"@"+ cellInStock.getContents()+"@"+cellAmount.getContents());
				if(cellProductName.getContents().toString() != ""){
					// Create the table from the source code without xml:
					row = new TableRow(this);
					row.setBackgroundColor(color.darker_gray);
					tblLoadProductList.addView(row);
					// number of rows
					TextView nr = new TextView(this);
					nr.setBackgroundColor(color.darker_gray);
					nr.setTextColor(Color.BLUE);
					nr.setText(String.valueOf(i));
					row.addView(nr);
					LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) nr.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					nr.setLayoutParams(llp);
					nr.setPadding(10, 10, 40, 3);
					
					// product name
					//final String name = String.valueOf(cellProductName.getContents().toString());
					TextView prdName = new TextView(this);
					prdName.setBackgroundColor(color.darker_gray);
					prdName.setTextColor(Color.BLUE);
					prdName.setText(String.valueOf(cellProductName.getContents().toString()));
					prdName.setHorizontalFadingEdgeEnabled(true);
					row.addView(prdName);
					llp = (LinearLayout.LayoutParams) prdName.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					prdName.setLayoutParams(llp);
					prdName.setPadding(10, 10, 40, 3);
									
					// instock
					TextView inStock = new TextView(this);
					inStock.setBackgroundColor(color.darker_gray);
					inStock.setTextColor(Color.BLUE);
					inStock.setText(String.valueOf(cellInStock.getContents().toString()));
					row.addView(inStock);
					llp = (LinearLayout.LayoutParams) inStock.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					inStock.setLayoutParams(llp);
					inStock.setPadding(10, 10, 40, 3);
					
					// orderqty
					EditText orderQty = new EditText(this);
					orderQty.setBackgroundColor(Color.YELLOW);
					orderQty.setTextColor(Color.BLACK);
					//orderQty.setText(String.valueOf("12345"));
					row.addView(orderQty);
					llp = (LinearLayout.LayoutParams) orderQty.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					orderQty.setLayoutParams(llp);
					orderQty.setPadding(10, 10, 40, 3);
					
					// amount
					TextView amount = new TextView(this);
					amount.setBackgroundColor(color.darker_gray);
					amount.setTextColor(Color.BLUE);
					amount.setText(String.valueOf(cellAmount.getContents().toString()));
					row.addView(amount);
					llp = (LinearLayout.LayoutParams) amount.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					amount.setLayoutParams(llp);
					amount.setPadding(10, 10, 40, 3);
					//calculate
					//Double Total = Integer.parseInt(String.valueOf(splitString[1])) * Double.parseDouble(String.valueOf(splitString[2]));
					// netamount
					final TextView netAmount = new TextView(this);
					netAmount.setBackgroundColor(color.darker_gray);
					netAmount.setTextColor(Color.BLUE);
					netAmount.setText("0.0");
					row.addView(netAmount);
					llp = (LinearLayout.LayoutParams) netAmount.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					netAmount.setLayoutParams(llp);
					netAmount.setPadding(10, 10, 40, 3);
					
					row.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							v.setBackgroundColor(Color.DKGRAY);
							
							final TableRow t = (TableRow) v;
						    TextView textViewSno = (TextView) t.getChildAt(0);
						    TextView textViewProductName = (TextView) t.getChildAt(1);
						    TextView textViewInStock = (TextView) t.getChildAt(2);
						    EditText editTextOrderQty = (EditText) t.getChildAt(3);
						    TextView textViewAmount = (TextView) t.getChildAt(4);
						    TextView textViewNetAmount = (TextView) t.getChildAt(5);
						    
						    String tvSno = textViewSno.getText().toString();
						    String tvProductName = textViewProductName.getText().toString();
						    Double tvInStock = Double.valueOf(textViewInStock.getText().toString());
						    Double etOrderQty = 0.0;
						    if(editTextOrderQty.getText().length() > 0){
						    	etOrderQty = Double.valueOf(editTextOrderQty.getText().toString());
						    }
						    Double tvAmount = Double.valueOf(textViewAmount.getText().toString());
						    final Double tvNetAmount = (etOrderQty * tvAmount);//Double.valueOf(textViewNetAmount.getText().toString());
						    
						    /*Toast.makeText(getApplicationContext(), "value was "+
						    		tvSno+"/"+tvProductName+"/"+tvInStock+"/"+etOrderQty+"/"+tvAmount+"/"+tvNetAmount, 
					                Toast.LENGTH_LONG).show();*/
						    v.setBackgroundColor(color.darker_gray);
						    //textViewNetAmount.setText((int) (etOrderQty * tvAmount));
						    netAmount.setText(String.valueOf(tvNetAmount));
						    /*editTextOrderQty.addTextChangedListener(new TextWatcher() {
								
								@Override
								public void afterTextChanged(Editable s) {
									// TODO Auto-generated method stub
									netAmount.setText(String.valueOf(tvNetAmount));
									//t.addView(netAmount);
									Toast.makeText(getApplicationContext(), "afterTextChanged(Editable s) : "+String.valueOf(tvNetAmount), 1).show();
								};

								@Override
								public void beforeTextChanged(CharSequence s,
										int start, int count, int after) {
									// TODO Auto-generated method stub
									//Toast.makeText(getApplicationContext(), "beforeTextChanged(CharSequence s,int start, int count, int after)", 1).show();
								}

								@Override
								public void onTextChanged(CharSequence s,
										int start, int before, int count) {
									// TODO Auto-generated method stub
									netAmount.setText(String.valueOf(tvNetAmount));
									Toast.makeText(getApplicationContext(), "onTextChanged(CharSequence s,int start, int before, int count)"+String.valueOf(tvNetAmount), 1).show();
									
								}
							});*/
						    
						}
					});
					
				} // end of if statement
			} // end of for loop
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	// set OnClickListener for Save Order
	private OnClickListener listenerSaveOrder = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// call save order list to excel file
			fnReadTableRowValues(FilePath.getExternalPath());
		}
	};
	// Save order list to Excel file
	private void fnReadTableRowValues(String filename){
		try {
			HSSFWorkbook workbook = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFCellStyle cellStyle = null;
			String formule_NetAmount = null;
			//String filename = null;
			//filename = FilePath.getExternalPath();
			File fp = new File(filename);
			FileInputStream is = new FileInputStream(fp);
			String sheetName = spnShopName.getSelectedItem().toString();
			// generate current time
			Date now = Calendar.getInstance().getTime();
			SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
			String theDate = df.format(now);
			
			if(fp.exists() == true){
				workbook = new HSSFWorkbook(is);
				String st=null;
				boolean status = false;
				for(int s=0;s<workbook.getNumberOfSheets();s++){
					st = workbook.getSheetName(s);
					if(st.equals(sheetName)){
						status=true;
						Log.d("Got Sheet Names"+s+"/"+sheetName, st);
					}else{
						Log.d("No Action made on sheet"+s+"/"+sheetName, st);
					}
				}
				if(status == false){
					sheet = workbook.createSheet(sheetName);
					Log.d("Status of boolean", String.valueOf(status));
				}else{
					sheet = workbook.getSheet(sheetName);
					Log.d("Status of boolean", String.valueOf(status));
				}
				//Toast.makeText(getApplicationContext(), "Total Child Count is "+tblLoadProductList.getChildCount(), Toast.LENGTH_SHORT).show();
				// getting values from tablelayout and place to excel
				Double totalOrderQty=0.0, totalNetAmount=0.0;
				for(int i = 1; i<tblLoadProductList.getChildCount();i++){
					String iRow,inStock,orderQty,productNames,amount,netAmount;
					
					iRow=String.valueOf(i);
					productNames = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(1)).getText());
					inStock = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(2)).getText());
					orderQty = String.valueOf(((EditText)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(3)).getText().toString());
					if(orderQty.length()==0){ 
						orderQty="0";
					}
					amount = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(4)).getText());
					if(amount.length()==0){
						amount = "0";
					}
					netAmount = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(5)).getText());
					if(netAmount.length()==0){
						netAmount ="0";
					}
					totalOrderQty += Double.valueOf(orderQty);
					totalNetAmount += Double.valueOf(netAmount);
					/*if(orderQty.length()>0){
						netAmount = String.valueOf(Double.parseDouble(orderQty)*Double.parseDouble(amount));
					}else{
						orderQty="0";
						netAmount = String.valueOf(Double.parseDouble(orderQty)*Double.parseDouble(amount));
					}*/
						if(i==1){
							row = sheet.createRow(i-1);
							Log.d("Main Row value", String.valueOf(i-1));
							cell = row.createCell((short)0);
							cell.setCellValue("Order No");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)1);
							cell.setCellValue(theDate+"/"+iRow);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)2);
							cell.setCellValue("Date");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)3);
							cell.setCellValue(theDate);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							row = sheet.createRow(i);
							Log.d("Header Row value", String.valueOf(i+1));
							cell = row.createCell((short)0);
							cell.setCellValue("S.No");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)1);
							cell.setCellValue("Product Name");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)2);
							cell.setCellValue("Stock Qty");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)3);
							cell.setCellValue("Ordered Qty");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)4);
							cell.setCellValue("Amount");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)5);
							cell.setCellValue("Net Amount");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
						}
						
						row = sheet.createRow(i+1);
						Log.d("Details rows", String.valueOf(i+2));
						cell = row.createCell((short)0);
						cell.setCellValue(Integer.valueOf(iRow));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cell.setCellStyle(cellStyle);
												
						cell = row.createCell((short)1);
						cell.setCellValue(productNames);
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)2);
						cell.setCellValue(Double.valueOf(inStock));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)3);
						cell.setCellValue(Double.valueOf(orderQty));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)4);
						cell.setCellValue(Double.valueOf(amount));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)5);
						//formule_NetAmount = "SUM(D"+(i+1)+",E"+(i+1)+")";
						//cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
						cell.setCellValue(Double.valueOf(netAmount));
						//cell.setCellFormula(formule_NetAmount);
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cell.setCellStyle(cellStyle);
						
						
						// create total qty and netamount row
						if(i == tblLoadProductList.getChildCount()-1){
							row = sheet.createRow(i+2);
							cell = row.createCell((short)0);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
													
							cell = row.createCell((short)1);
							cell.setCellValue("Total");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)2);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)3);
							cell.setCellValue(totalOrderQty);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)4);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)5);
							cell.setCellValue(totalNetAmount);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
						}
					
					//Log.d("tblLoadProductList.getChildAt(i)", String.valueOf(tblLoadProductList.getChildAt(i)));
					Log.d("List of products"+i, String.valueOf(iRow+"@"+productNames+"@"+inStock+"@"+orderQty+"@"+amount+"@"+netAmount));
				}
				
			}
			is.close();
			FileOutputStream out = new FileOutputStream(new File(filename));
			workbook.write(out);
			out.close();
			
			Toast.makeText(getApplicationContext(), sheetName+" is successfully saved", Toast.LENGTH_SHORT).show();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d("File Not Found Exception", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("IO Exception", e.getMessage());
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_page, menu);
		return true;
	}
	
	private ArrayList<String> loadAreaName(String mySheet) throws IOException{ 
		JXLReader test = new JXLReader();
	    test.setInputFile(FilePath.getExternalPath(), mySheet,null);
	    Log.d("test.setInputFile(actualPathName, mySheet)"," test.setInputFile(actualPathName, mySheet)");
	    return test.getAreaName();
	}
	private ArrayList<String> loadShopName(String mySheet, String areaName) throws IOException{
		JXLReader jxlLoadShopName = new JXLReader();
		jxlLoadShopName.setInputFile(FilePath.getExternalPath(), mySheet,areaName);
		Log.d("loadShopName function", "Success to loadShopName");
		return jxlLoadShopName.getShopName();
	}
	
	private void fnloadBeatName(){
		/**
		 * load spinnerBeatName
		 */
		spnBeatName = (Spinner)findViewById(R.id.spinnerBeatname);
		ArrayList<String> arrayBeatName = null;
		try {
			arrayBeatName = loadAreaName("AREA NAME");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayAdapter<String> dataAdapterBeatName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayBeatName);
		dataAdapterBeatName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnBeatName.setAdapter(dataAdapterBeatName);
			spnBeatName.setOnItemSelectedListener(listenerLoadShopName);
	}
	private OnItemSelectedListener listenerLoadShopName = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			fnloadShopName();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
		
	};
	/**
	 * load spinnerShopname
	 */
	private void fnloadShopName(){
		
		Log.d("Displayed Shopname", (String)spnBeatName.getSelectedItem());
		Log.d("Displayed Shopname+1", String.valueOf(spnBeatName.getSelectedItem()));
		spnShopName = (Spinner)findViewById(R.id.spinnerShopname);
		ArrayList<String> arrayShopName = null;
		try {
			arrayShopName = loadShopName("CUSTOMER NAME", String.valueOf(spnBeatName.getSelectedItem()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ArrayAdapter<String> dataAdapterShopName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayShopName);
		dataAdapterShopName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnShopName.setAdapter(dataAdapterShopName);
	}

	
	
}

