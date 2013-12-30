package com.example.ota;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
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
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.FocusFinder;
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
	Button btnSaveOrder, btnDashBoard, btnLogOut;//btnLoadProductList, 
	Spinner spnBeatName, spnShopName;//, spnCustomerName, spnProductName;
	ScrollView scrollOrderPage;
	LinearLayout lnrLayContentViewOrderPage;
	TableLayout tblLoadProductList;
	HorizontalScrollView hsv;
	TableRow tr1 = null;
	TextView lbl_TotalNetAmount = null, lbl_TotalNetAmount1 = null;
	
	SessionManagement session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_page);
		
		// Session class instance
		session = new SessionManagement(getApplicationContext());
		//Toast.makeText(getApplicationContext(), "User Login Status: "+ session.isLoggedIn(), Toast.LENGTH_LONG).show();
		/**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
		session.checkLogin();
		
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
		
		//Log.d("Loaded", getClass().getSimpleName());
		// button for save order
		btnSaveOrder = (Button)findViewById(R.id.btnSaveOrder);
		btnSaveOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(session.isLoggedIn() == true){
					// call save order list to excel file
					TableLayout tbllayout = (TableLayout) findViewById(R.id.tableLayoutOrder);
					//Log.d("Table Row Counts" , String.valueOf(tbllayout.getChildCount()));
					if(tbllayout.getChildCount() > 1 ){
						fnReadTableRowValues(FilePath.getExternalPath());
					}else{
						//Log.d("Order Table : "+tblLoadProductList.getChildCount(), "No Records added into the Table");
						Toast.makeText(getApplicationContext(),
								"No Records added in the Table, Minimum ONE Record should be present...",
								Toast.LENGTH_LONG).show();
					}
						
				}else{
					session.checkLogin();
				}
				
			}
		});
		// button for dash board
		btnDashBoard = (Button)findViewById(R.id.btnDashboard);
		btnDashBoard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(session.isLoggedIn() == true){
					Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(myInt);
				}else{
					session.checkLogin();
				}
			}
		});
		// button for logout
		btnLogOut = (Button)findViewById(R.id.btnLogOut);
		btnLogOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Clear the session data
	            // This will clear all session data and
	            // redirect user to LoginActivity
				session.logoutUser();
			}
		});
	}
	/**
	 * 
	 * @param mySheet - is selected sheet name to fetch records for Beat names
	 * @return  - return beatnames in ascending orderse
	 * @throws IOException - manage IOExceptions
	 */
	private ArrayList<String> loadAreaName(String mySheet) throws IOException{ 
		JXLReader test = new JXLReader();
	    test.setInputFile(FilePath.getExternalPath(), mySheet,null);
	    //Log.d("test.setInputFile(actualPathName, mySheet)"," test.setInputFile(actualPathName, mySheet)");
	    return test.getAreaName();
	}
	/**
	 * 
	 * @param mySheet  - is selected sheet name to fetch records for shop names
	 * @param areaName - input for selected beatname
	 * @return  - return shopname in ascending orders
	 * @throws IOException - manage IOExceptions
	 */
	private ArrayList<String> loadShopName(String mySheet, String areaName) throws IOException{
		JXLReader jxlLoadShopName = new JXLReader();
		jxlLoadShopName.setInputFile(FilePath.getExternalPath(), mySheet,areaName);
		//Log.d("loadShopName function", "Success to loadShopName");
		return jxlLoadShopName.getShopName();
	}
	/**
	 * this function handles for load beatname
	 */
	private void fnloadBeatName(){
		/**
		 * load spinnerBeatName
		 */
		spnBeatName = (Spinner)findViewById(R.id.spinnerBeatname);
		ArrayList<String> arrayBeatName = null;
		try {
			arrayBeatName = loadAreaName("AREA NAME");
		} catch (IOException e) {
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		ArrayAdapter<String> dataAdapterBeatName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayBeatName);
		dataAdapterBeatName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			if(session.isLoggedIn() == true){
				spnBeatName.setAdapter(dataAdapterBeatName);
			}else{
				session.checkLogin();
			}
			
			spnBeatName.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(session.isLoggedIn() == true){
						fnloadShopName();
					}else{
						session.checkLogin();
					}
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
				
			});
	}
	/**
	 * this function handle for load spinnerShopname
	 */
	private void fnloadShopName(){
		
		//Log.d("Displayed Shopname", (String)spnBeatName.getSelectedItem());
		//Log.d("Displayed Shopname+1", String.valueOf(spnBeatName.getSelectedItem()));
		spnShopName = (Spinner)findViewById(R.id.spinnerShopname);
		ArrayList<String> arrayShopName = null;
		try {
			arrayShopName = loadShopName("CUSTOMER NAME", String.valueOf(spnBeatName.getSelectedItem()));
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		ArrayAdapter<String> dataAdapterShopName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayShopName);
		dataAdapterShopName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnShopName.setAdapter(dataAdapterShopName);
		spnShopName.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(session.isLoggedIn() == true){
					//Log.d("Click LoadProduct List", "Click LoadProduct List");
					tblLoadProductList = (TableLayout)findViewById(R.id.tableLayoutOrder);
					if(tblLoadProductList.getChildCount()<2){
						//Log.d("Table Rows Count", String.valueOf(tblLoadProductList.getChildCount()));
						fngetProducts("PRODUCT NAME",FilePath.getExternalPath());
					}else{
						//Log.d("Else - Table Rows Count", String.valueOf(tblLoadProductList.getChildCount()));
						tblLoadProductList.removeViews(1, tblLoadProductList.getChildCount()-1);
						//Log.d("Table Rows Count", String.valueOf(tblLoadProductList.getChildCount()));
						fngetProducts("PRODUCT NAME",FilePath.getExternalPath());
					}
					
				}else{
					session.checkLogin();
				}
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	/**
	 * 
	 * @param SheetName - load all the products from this selected sheet
	 * @param fileFullPath - excel file location on the extenal memory drive
	 * load all products to table layout from excel 
	 */
	
	private void fngetProducts(String SheetName, String fileFullPath){
		File f = new File(fileFullPath);
		Workbook w;
		Double TotalNetAmountCalc = 0.0;
		 final DecimalFormat df = new DecimalFormat("0.00");
		try {
			w = Workbook.getWorkbook(f);
			Sheet sheet = w.getSheet(SheetName);
			
			tblLoadProductList = (TableLayout)findViewById(R.id.tableLayoutOrder);
			TableRow row = null;
			// load values from excel to table layout
			for(int i=1;i<sheet.getRows();i++){
				Cell cellProductName = sheet.getCell(1, i);
				Cell cellInStock = sheet.getCell(2, i);
				Cell cellRate = sheet.getCell(4, i);
				
				/*Log.d("Values of Products in loading tables"+i, cellProductName.getContents() +"@"
				+ cellInStock.getContents()+"@"+cellRate.getContents());*/
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
					nr.setGravity(Gravity.RIGHT);
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
					prdName.setGravity(Gravity.LEFT);
					row.addView(prdName);
					llp = (LinearLayout.LayoutParams) prdName.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					prdName.setLayoutParams(llp);
					prdName.setPadding(10, 10, 40, 3);
									
					// in stock
					TextView inStock = new TextView(this);
					inStock.setBackgroundColor(color.darker_gray);
					inStock.setTextColor(Color.BLUE);
					inStock.setText(String.valueOf(cellInStock.getContents().toString()));
					inStock.setGravity(Gravity.RIGHT);
					row.addView(inStock);
					llp = (LinearLayout.LayoutParams) inStock.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					inStock.setLayoutParams(llp);
					inStock.setPadding(10, 10, 40, 3);
					
					// order qty
					final EditText orderQty = new EditText(this);
					orderQty.setBackgroundColor(Color.YELLOW);
					orderQty.setTextColor(Color.BLACK);
					orderQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL |
							InputType.TYPE_NUMBER_FLAG_SIGNED);
					orderQty.setGravity(Gravity.RIGHT);
					//orderQty.setText(String.valueOf("12345"));
					row.addView(orderQty);
					llp = (LinearLayout.LayoutParams) orderQty.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					orderQty.setLayoutParams(llp);
					orderQty.setPadding(10, 10, 40, 3);
					
					// rate
					final TextView rate = new TextView(this);
					rate.setBackgroundColor(color.darker_gray);
					rate.setTextColor(Color.BLUE);
					rate.setText(String.valueOf(cellRate.getContents().toString()));
					rate.setGravity(Gravity.RIGHT);
					row.addView(rate);
					llp = (LinearLayout.LayoutParams) rate.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					rate.setLayoutParams(llp);
					rate.setPadding(10, 10, 40, 3);
					
					// order free
					final EditText freeQty = new EditText(this);
					freeQty.setBackgroundColor(Color.GREEN);
					freeQty.setTextColor(Color.BLACK);
					freeQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL |
							InputType.TYPE_NUMBER_FLAG_SIGNED);
					orderQty.setGravity(Gravity.RIGHT);
					row.addView(freeQty);
					llp = (LinearLayout.LayoutParams) freeQty.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					freeQty.setLayoutParams(llp);
					freeQty.setPadding(10, 10, 40, 3);
					
					// Trade Dis % (amount * trade Dis) / 100
					final EditText tradeDis = new EditText(this);
					tradeDis.setBackgroundColor(Color.YELLOW);
					tradeDis.setTextColor(Color.BLACK);
					tradeDis.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL |
							InputType.TYPE_NUMBER_FLAG_SIGNED);
					tradeDis.setGravity(Gravity.RIGHT);
					row.addView(tradeDis);
					llp = (LinearLayout.LayoutParams) tradeDis.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					tradeDis.setLayoutParams(llp);
					tradeDis.setPadding(10, 10, 40, 3);
					
					// Cash Dis % (amount * cash Dis) / 100
					final EditText cashDis = new EditText(this);
					cashDis.setBackgroundColor(Color.RED);
					cashDis.setTextColor(Color.BLACK);
					cashDis.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL |
							InputType.TYPE_NUMBER_FLAG_SIGNED);
					cashDis.setGravity(Gravity.RIGHT);
					row.addView(cashDis);
					llp = (LinearLayout.LayoutParams) cashDis.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					cashDis.setLayoutParams(llp);
					cashDis.setPadding(10, 10, 40, 3);
					
					// amount
					final TextView amount = new TextView(this);
					amount.setBackgroundColor(color.darker_gray);
					amount.setTextColor(Color.BLUE);
					amount.setText("0.0");
					amount.setGravity(Gravity.RIGHT);
					row.addView(amount);
					llp = (LinearLayout.LayoutParams) amount.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					amount.setLayoutParams(llp);
					amount.setPadding(10, 10, 40, 3);
					
					orderQty.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
						@Override
						public void afterTextChanged(Editable s) {
							Double etOrderQty=0.0;
							try {
								Double.parseDouble(orderQty.getText().toString());
								if(orderQty.getText().length() > 0){
							    	etOrderQty = Double.valueOf(orderQty.getText().toString());
							    }
							    Double tvRate = Double.valueOf(rate.getText().toString());
							    final Double tvAmount = (etOrderQty * tvRate);
							    amount.setText(String.valueOf(df.format(tvAmount)));
							    amount.setGravity(Gravity.RIGHT);
							    lbl_TotalNetAmount = (TextView)findViewById(R.id.lblTotalNetAmount);
							    lbl_TotalNetAmount.setText(Html.fromHtml("<b>Total Net Amount : "
							    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));	
							    
							    lbl_TotalNetAmount1 = (TextView)findViewById(R.id.tvOrderPageTotalNetAmount);
							    lbl_TotalNetAmount1.setText(Html.fromHtml("<b>Total Net Amount : "
							    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));
							    
							} catch (NumberFormatException ne) {
								// TODO: handle exception
								//Log.d("NumberFormat Exception", ne.getMessage());
								Toast.makeText(getApplication(), ne.getMessage(), Toast.LENGTH_LONG).show();
								if(orderQty.getText().toString().length() >0){
									Toast.makeText(getApplicationContext(), 
											"Please enter valid number format.. you entered as "+ne.getMessage(),
											Toast.LENGTH_LONG).show();
								}else{
									//orderQty.setText("0");
									amount.setText("0.0");
									amount.setGravity(Gravity.RIGHT);
									lbl_TotalNetAmount = (TextView)findViewById(R.id.lblTotalNetAmount);
								    lbl_TotalNetAmount.setText(Html.fromHtml("<b>Total Net Amount : "
									+ df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));
								    
								    lbl_TotalNetAmount1 = (TextView)findViewById(R.id.tvOrderPageTotalNetAmount);
								    lbl_TotalNetAmount1.setText(Html.fromHtml("<b>Total Net Amount : "
								    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));
								}
								//orderQty.clearFocus();
							}
							
						}
					});
					
					tradeDis.addTextChangedListener(new TextWatcher() {
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
						@Override
						public void afterTextChanged(Editable s) {
							Double etCashDis = 0.0;
							Double etTradeDis = 0.0;
							Double preAmount = 0.0;
							Double preFinalEtTradeDis = 0.0;
							Double preFinalEtCashDis = 0.0;
							Double chkOrderQty, chkRate;
							try {
								
								chkOrderQty = Double.valueOf(orderQty.getText().toString());
								chkRate = Double.valueOf(rate.getText().toString());
								if(chkOrderQty > 0){
									if(cashDis.getText().length()>0){
										etCashDis = Double.valueOf(cashDis.getText().toString());
									}
									if(tradeDis.getText().length()>0){
										etTradeDis = Double.valueOf(tradeDis.getText().toString());
									}
									preAmount = (chkOrderQty * chkRate);
									preFinalEtTradeDis = preAmount - ((preAmount*etTradeDis)/100);
									preFinalEtCashDis = preFinalEtTradeDis - ((preFinalEtTradeDis * etCashDis)/100);
									
									final Double tvAmount = preFinalEtCashDis;
								    amount.setText(String.valueOf(df.format(tvAmount)));
								    amount.setGravity(Gravity.RIGHT);
								    
								    lbl_TotalNetAmount = (TextView)findViewById(R.id.lblTotalNetAmount);
								    lbl_TotalNetAmount.setText(Html.fromHtml("<b>Total Net Amount : "
								    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));	
								    
								    lbl_TotalNetAmount1 = (TextView)findViewById(R.id.tvOrderPageTotalNetAmount);
								    lbl_TotalNetAmount1.setText(Html.fromHtml("<b>Total Net Amount : "
								    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));
								}
							} catch (Exception e) {
								//Log.d("Exception", e.getMessage());
								Toast.makeText(getApplicationContext(), 
										"Please enter valid order quantity.", Toast.LENGTH_LONG).show();
							}
						}
					});
					
					cashDis.addTextChangedListener(new TextWatcher() {
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
						@Override
						public void afterTextChanged(Editable s) {
							Double etCashDis = 0.0;
							Double etTradeDis = 0.0;
							Double preAmount = 0.0;
							Double preFinalEtTradeDis = 0.0;
							Double preFinalEtCashDis = 0.0;
							Double chkOrderQty, chkRate;
							try {
								chkOrderQty = Double.valueOf(orderQty.getText().toString());
								chkRate = Double.valueOf(rate.getText().toString());
								if(chkOrderQty > 0){
									if(cashDis.getText().length()>0){
										etCashDis = Double.valueOf(cashDis.getText().toString());
									}
									if(tradeDis.getText().length()>0){
										etTradeDis = Double.valueOf(tradeDis.getText().toString());
									}
									preAmount = (chkOrderQty * chkRate);
									preFinalEtTradeDis = preAmount - ((preAmount*etTradeDis)/100);
									preFinalEtCashDis = preFinalEtTradeDis - ((preFinalEtTradeDis * etCashDis)/100);
									
									final Double tvAmount = preFinalEtCashDis;
								    amount.setText(String.valueOf(df.format(tvAmount)));
								    amount.setGravity(Gravity.RIGHT);
								    
								    lbl_TotalNetAmount = (TextView)findViewById(R.id.lblTotalNetAmount);
								    lbl_TotalNetAmount.setText(Html.fromHtml("<b>Total Net Amount : "
								    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));	
								    
								    lbl_TotalNetAmount1 = (TextView)findViewById(R.id.tvOrderPageTotalNetAmount);
								    lbl_TotalNetAmount1.setText(Html.fromHtml("<b>Total Net Amount : "
								    + df.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));
								}
							} catch (Exception e) {
								//Log.d("Exception", e.getMessage());
								Toast.makeText(getApplicationContext(), 
										"Please enter valid order quantity.", Toast.LENGTH_LONG).show();
							}
						}
					});
					
					if(amount.getText().length()>0){
						TotalNetAmountCalc += Double.valueOf(amount.getText().toString());
						lbl_TotalNetAmount = (TextView)findViewById(R.id.lblTotalNetAmount);
						lbl_TotalNetAmount.setText(Html.fromHtml("<b>Total Net Amount : "+ df.format(TotalNetAmountCalc) 
								+"</b>"));
						
						lbl_TotalNetAmount1 = (TextView)findViewById(R.id.tvOrderPageTotalNetAmount);
						lbl_TotalNetAmount1.setText(Html.fromHtml("<b>Total Net Amount : "+ df.format(TotalNetAmountCalc)
								+"</b>"));
					}
					row.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// setting for focus on order edit text box
							orderQty.requestFocus();
							//Toast.makeText(getApplicationContext(), "Test", 2).show();
							
						}
					});
					
				} // end of if statement
			} // end of for loop
			
		} catch (BiffException e) {
			//Log.d("BiffException", e.getMessage());
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			//Log.d("IOException", e.getMessage());
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	
	}
	/**
	 * 
	 * @param filename - file name with path to store orders
	 * Save order list to Excel file
	 */
	
	private void fnReadTableRowValues(String filename){
		try {
			HSSFWorkbook workbook = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFCellStyle cellStyle = null;
			//String filename = null;
			//filename = FilePath.getExternalPath();
			File fp = new File(filename);
			FileInputStream is = new FileInputStream(fp);
			String sheetName = spnShopName.getSelectedItem().toString();
			// generate current time
			Date now = Calendar.getInstance().getTime();
			SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
			String theDate = df.format(now);
			SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy");
			String theDate1 = dft.format(now);
			DecimalFormat df1 = new DecimalFormat("0.00");
			
			if(fp.exists() == true){
				workbook = new HSSFWorkbook(is);
				String st=null;
				boolean status = false;
				for(int s=0;s<workbook.getNumberOfSheets();s++){
					st = workbook.getSheetName(s);
					if(st.equals(sheetName)){
						status=true;
						//Log.d("Got Sheet Names"+s+"/"+sheetName, st);
					}else{
						//Log.d("No Action made on sheet"+s+"/"+sheetName, st);
						Toast.makeText(getApplication(), "No Action made on sheet"+s+"/"+sheetName, Toast.LENGTH_LONG).show();
					}
				}
				if(status == false){
					sheet = workbook.createSheet(sheetName);
					//Log.d("Status of boolean", String.valueOf(status));
				}else{
					sheet = workbook.getSheet(sheetName);
					//Log.d("Status of boolean", String.valueOf(status));
				}
				
				// getting values from tablelayout and place to excel
				Double totalOrderQty=0.0, totalNetAmount=0.0;
				for(int i = 1; i<tblLoadProductList.getChildCount();i++){
					String iRow,inStock,orderQty,productNames,rate,freeQty,tradeDis,cashDis,amount;
					
					iRow=String.valueOf(i);
					productNames = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(1)).getText());
					inStock = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(2)).getText());
					orderQty = String.valueOf(((EditText)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(3)).getText().toString());
					if(orderQty.length()==0){ 
						orderQty="0";
					}
					rate = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(4)).getText());
					if(rate.length()==0){
						rate = "0";
					}
					freeQty = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(5)).getText());
					if(freeQty.length()<=0){
						freeQty = "0";
					}
					tradeDis = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(6)).getText());
					if(tradeDis.length()<=0){
						tradeDis = "0";
					}
					cashDis = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(7)).getText());
					if(cashDis.length()<=0){
						cashDis = "0";
					}
					amount = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(8)).getText());
					if(amount.length()<=0){
						amount ="0";
					}
					totalOrderQty += Double.valueOf(orderQty);
					totalNetAmount += Double.valueOf(amount);
					
						if(i==1){
							row = sheet.createRow(i-1);
							//Log.d("Main Row value", String.valueOf(i-1));
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
							cell.setCellValue(theDate+"/"+iRow+"/"+workbook.getSheetIndex(sheetName));
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
							cell.setCellValue(theDate1);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)4);
							cell.setCellValue("Beat Name");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)5);
							cell.setCellValue(spnBeatName.getSelectedItem().toString());
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)6);
							cell.setCellValue("Rep Name");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)7);
							cell.setCellValue(session.getUserDetails().get("name").toString());
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							
							row = sheet.createRow(i);
							//Log.d("Header Row value", String.valueOf(i+1));
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
							cell.setCellValue("Rate");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)5);
							cell.setCellValue("Free");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)6);
							cell.setCellValue("Trade Dis %");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)7);
							cell.setCellValue("Cash Dis %");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)8);
							cell.setCellValue("Amount");
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
						}
						
						row = sheet.createRow(i+1);
						//Log.d("Details rows", String.valueOf(i+2));
						cell = row.createCell((short)0);
						cell.setCellValue(Integer.valueOf(iRow));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
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
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)3);
						cell.setCellValue(Double.valueOf(orderQty));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)4);
						cell.setCellValue(df1.format(Double.valueOf(rate)));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)5);
						cell.setCellValue(df1.format(Double.valueOf(freeQty)));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)6);
						cell.setCellValue(df1.format(Double.valueOf(tradeDis)));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)7);
						cell.setCellValue(df1.format(Double.valueOf(cashDis)));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell((short)8);
						cell.setCellValue(df1.format(Double.valueOf(amount)));
						// set border to cell
						cellStyle = workbook.createCellStyle();
						cellStyle.setBorderTop((short)1);
						cellStyle.setBorderBottom((short)1);
						cellStyle.setBorderLeft((short)1);
						cellStyle.setBorderRight((short)1);
						cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
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
							cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
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
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)6);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)7);
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cell.setCellStyle(cellStyle);
							
							cell = row.createCell((short)8);
							cell.setCellValue(df1.format(totalNetAmount));
							// set border to cell
							cellStyle = workbook.createCellStyle();
							cellStyle.setBorderTop((short)1);
							cellStyle.setBorderBottom((short)1);
							cellStyle.setBorderLeft((short)1);
							cellStyle.setBorderRight((short)1);
							cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
							cell.setCellStyle(cellStyle);
						}
					
					/*Log.d("List of products"+i, String.valueOf(iRow+"@"+productNames+"@"+inStock+"@"+orderQty
							+"@"+rate+"@"+freeQty+"@"+tradeDis+"@"+cashDis+"@"+amount));*/
				}
				
			}
			is.close();
			FileOutputStream out = new FileOutputStream(new File(filename));
			workbook.write(out);
			out.close();
			
			Toast.makeText(getApplicationContext(), sheetName+" is successfully saved", Toast.LENGTH_SHORT).show();
			// after saved successfully clear the table contents
			tblLoadProductList.removeViews(1, tblLoadProductList.getChildCount()-1);
			lbl_TotalNetAmount.setText("");
			lbl_TotalNetAmount1.setText("");
			
		} catch (FileNotFoundException e) {
			//Log.d("File Not Found Exception", e.getMessage());
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			//Log.d("IO Exception", e.getMessage());
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_page, menu);
		return true;
	}
	
	/**
	 * 
	 * @return - total net amount of this order
	 * calculate total netamount by added each ordered qty
	 */
	
	private Double getTotalNetAmountAfterChangedOrder(){
		Double Calc_TotalNetAmount = 0.0;
		String strNetAmount = null;
		tblLoadProductList = (TableLayout)findViewById(R.id.tableLayoutOrder);
			for(int i = 0;i<tblLoadProductList.getChildCount();i++){
				if(i>0 &&  i < tblLoadProductList.getChildCount()){
					strNetAmount = String.valueOf(((TextView)((TableRow)
							tblLoadProductList.getChildAt(i)).getChildAt(8)).getText());
					if(strNetAmount.length()==0){
						strNetAmount ="0";
					}
					Calc_TotalNetAmount += Double.valueOf(strNetAmount);
				}
			}
		return Calc_TotalNetAmount;
	}
}