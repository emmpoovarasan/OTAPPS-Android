package com.example.ota;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import jxl.read.biff.BiffException;

import android.R.color;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class OrderPage extends Activity {
	Button btnAddOrder;//, btnOrderList;
	Spinner spnBeatName, spnShopName, spnCustomerName, spnProductName;
	ScrollView scrollOrderPage;
	LinearLayout lnrLayContentViewOrderPage;
	public String pathName;
	public String actualPathName = null;
	TableLayout table1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_page);
		// added this line to scroll the page
		//Setting scroll view & content view
		scrollOrderPage = (ScrollView)findViewById(R.id.scroll_view);
		lnrLayContentViewOrderPage = (LinearLayout)findViewById(R.id.content);
		//find the location of the excel file
		loadExternalPath();
		//load spinnerBeatName
		loadBeatName();
		//load spinnerShopname
		loadShopName();
		//load spinnerCustomername
		loadCustomerName();
		//load spinnerProductname
		loadProductName();
		
		// load table dynamic
		//buildOrderTable();
		// for adding this code scrolling the page;
		scrollOrderPage.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollOrderPage.scrollTo(0, lnrLayContentViewOrderPage.getPaddingTop());
			}
		});
		
		btnAddOrder = (Button)findViewById(R.id.getorderdetails);
		btnAddOrder.setOnClickListener(addOrderDetailsPage);
		Log.d("Loaded", getClass().getSimpleName());
		
		/*addOrderDetails.setOnClickListener(clickButton);
		btnOrderList = (Button)findViewById(R.id.orderlist);
		btnOrderList.setOnClickListener(clickButton);*/
		//scrollPage.setOnTouchListener(test);
	}
	
	/*private OnTouchListener test = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			scrollPage.scrollTo(0, linerLayoutContentView.getPaddingTop());
			return false;
		}
	};*/
	private OnClickListener addOrderDetailsPage = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "Add to Order List", Toast.LENGTH_SHORT).show();
			Log.d("Click Order Button", "Add to Order List");
			//Log.d("checkMyDir", actualPathName);
			// to navigate other page
			/*Intent myintent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
			Toast.makeText(getApplicationContext(), "Navigate to order details page", Toast.LENGTH_SHORT).show();
			startActivity(myintent);*/
			setTable();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_page, menu);
		return true;
	}
	
	private ArrayList<String> loadAreaName(String mySheet) throws IOException{ 
		JXLReader test = new JXLReader();
	    test.setInputFile(actualPathName, mySheet,null);
	    Log.d("test.setInputFile(actualPathName, mySheet)"," test.setInputFile(actualPathName, mySheet)");
	    return test.getAreaName();
	}
	private ArrayList<String> loadShopName(String mySheet, String areaName) throws IOException{
		JXLReader jxlLoadShopName = new JXLReader();
		jxlLoadShopName.setInputFile(actualPathName, mySheet,areaName);
		Log.d("loadShopName function", "Success to loadShopName");
		return jxlLoadShopName.getShopName();
	}
	private ArrayList<String> loadCustomerName(String mySheet, String customerName) throws IOException{
		JXLReader jxlLoadCustomerName = new JXLReader();
		jxlLoadCustomerName.setInputFile(actualPathName, mySheet,customerName);
		Log.d("loadShopName function", "Success to loadShopName");
		return jxlLoadCustomerName.getCustomerName();
	}
	private ArrayList<String> loadProductName(String mySheet) throws IOException{
		JXLReader jxlLoadProductName = new JXLReader();
		jxlLoadProductName.setInputFile(actualPathName, mySheet,null);
		Log.d("loadShopName function", "Success to loadShopName");
		return jxlLoadProductName.getProductName();
	}
	private void loadExternalPath(){
		/**
		 * find the location of the excel file
		 */
		try {
			if(Environment.isExternalStorageRemovable()==true){
				//Toast.makeText(getApplicationContext(), "ExternalStoageDirectory is found "+Environment.isExternalStorageRemovable(), Toast.LENGTH_SHORT).show();
				Log.d("ExternalStoageDirectory Found", "ExternalStoageDirectory is found "+Environment.isExternalStorageRemovable());
				pathName = Environment.getExternalStorageDirectory().toString()+"//OTA";
				Log.d("Files", "Path : "+pathName);		
				File f = new File(pathName);
				File fl[] = f.listFiles();
				Log.d("Files", "Size : "+fl.length);		
				for(int i=0; i<fl.length;i++){
					Log.d("Files", "File Name : "+fl[i].getName());
					Log.d("Files", "AbsFile Path : "+fl[i].getAbsolutePath());
					actualPathName = fl[i].getAbsolutePath();			
				}
			}else{
				//Toast.makeText(getApplicationContext(), "No ExternalStoageDirectory", Toast.LENGTH_SHORT).show();
				Log.d("No ExternalStoageDirectory", "No ExternalStoageDirectory");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	private void loadBeatName(){
		/**
		 * load spinnerBeatName
		 */
		spnBeatName = (Spinner)findViewById(R.id.spinnerBeatname);
		ArrayList<String> arrayBeatName = null;
		try {
			arrayBeatName = loadAreaName("AREA NAME");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayAdapter<String> dataAdapterBeatName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayBeatName);
		dataAdapterBeatName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnBeatName.setAdapter(dataAdapterBeatName);
		spnBeatName.setOnItemSelectedListener(listenerLoadShopName);
		
	}
	/*Intent goBack = new Intent(getApplicationContext(), LoginActivity.class);
	startActivity(goBack);*/
	private OnItemSelectedListener listenerLoadShopName = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			/*Toast.makeText(arg0.getContext(), 
					"OnItemSelectedListener : " + arg0.getItemAtPosition(arg2).toString(),
					Toast.LENGTH_SHORT).show();*/
			loadShopName();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	/*private OnClickListener listenerLoadShopName = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			//load spinnerShopname
			loadShopName();
			
		}
	};*/
	/**
	 * load spinnerShopname
	 */
	private void loadShopName(){
		
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
		spnShopName.setOnItemSelectedListener(listenerLoadCustomerName);
	}
	
	private OnItemSelectedListener listenerLoadCustomerName = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			/*Toast.makeText(arg0.getContext(), 
					"OnItemSelectedListener : " + arg0.getItemAtPosition(arg2).toString(),
					Toast.LENGTH_SHORT).show();*/
			loadCustomerName();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	/**
	 * load spinner customer name
	 */
	private void loadCustomerName(){
		spnCustomerName = (Spinner)findViewById(R.id.spinnerCutomername);
		ArrayList<String> arrayCustomerName = null;
		try {
			arrayCustomerName = loadCustomerName("CUSTOMER NAME", String.valueOf(spnShopName.getSelectedItem()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ArrayAdapter<String> dataAdapterCustomerName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayCustomerName);
		dataAdapterCustomerName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnCustomerName.setAdapter(dataAdapterCustomerName);
		spnCustomerName.setOnItemSelectedListener(listenerCreateFile);
	}
	private OnItemSelectedListener listenerCreateFile = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), String.valueOf(spnCustomerName.getSelectedItem()), Toast.LENGTH_SHORT).show();
			Log.d("Get Custname", String.valueOf(spnCustomerName.getSelectedItem()));
			
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * load spinner product name
	 */
	private void loadProductName(){
		spnProductName = (Spinner)findViewById(R.id.spinnerProductname);
		ArrayList<String> arrayProductName = null;
		try {
			arrayProductName = loadProductName("PRODUCT NAME");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ArrayAdapter<String> dataAdapterProductName = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayProductName);
		dataAdapterProductName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnProductName.setAdapter(dataAdapterProductName);
		spnProductName.setOnItemSelectedListener(listenerLoadProductName);
	}
	
	private OnItemSelectedListener listenerLoadProductName = new OnItemSelectedListener() {
		
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplication(), String.valueOf(spnProductName.getSelectedItem()), Toast.LENGTH_SHORT).show();
			Log.d("Select ProdcutName", String.valueOf(spnProductName.getSelectedItem()));
			/*try {
				if(CheckWriteOrderList() == true){
					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "Failed to create excel", Toast.LENGTH_SHORT).show();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}*/
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	private void setTable(){
		//Toast.makeText(getApplication(), "setTable()", Toast.LENGTH_SHORT).show();
		Log.d("setTable()", "setTable() is called");
		String productName = String.valueOf(spnProductName.getSelectedItem());
		final String[] splitString = productName.split("@");
		
		table1 = (TableLayout)findViewById(R.id.tableLayoutOrder);
		
		// Create the table from the source code without xml:
		TableRow row = new TableRow(this);
		row.setBackgroundColor(color.darker_gray);
		table1.addView(row);
		
		// number of rows
		TextView nr = new TextView(this);
		nr.setBackgroundColor(color.white);
		nr.setTextColor(Color.BLUE);
		nr.setText(String.valueOf(table1.getChildCount()));
		row.addView(nr);
		
		// product name
		TextView prdName = new TextView(this);
		prdName.setBackgroundColor(Color.WHITE);
		prdName.setTextColor(Color.BLUE);
		prdName.setText(String.valueOf(splitString[0]));
		prdName.setHorizontalFadingEdgeEnabled(true);
		row.addView(prdName);
						
		// instock
		TextView inStock = new TextView(this);
		inStock.setBackgroundColor(Color.WHITE);
		inStock.setTextColor(Color.BLUE);
		inStock.setText(String.valueOf(splitString[1]));
		row.addView(inStock);
		
		// orderqty
		EditText orderQty = new EditText(this);
		orderQty.setBackgroundColor(Color.YELLOW);
		orderQty.setTextColor(Color.BLACK);
		row.addView(orderQty);
		
		// amount
		TextView amount = new TextView(this);
		amount.setBackgroundColor(Color.WHITE);
		amount.setTextColor(Color.BLUE);
		amount.setText(String.valueOf(splitString[2]));
		row.addView(amount);
		Double Total = Integer.parseInt(String.valueOf(splitString[1])) * Double.parseDouble(String.valueOf(splitString[2]));
		// netamount
		TextView netAmount = new TextView(this);
		netAmount.setBackgroundColor(Color.WHITE);
		netAmount.setTextColor(Color.BLUE);
		netAmount.setText(String.valueOf(Total));
		row.addView(netAmount);
				
	}
	private void buildOrderTable(){
		int rowCount = 5;
		TableLayout table = (TableLayout)findViewById(R.id.tableLayoutOrder);
		for(int i=0;i<5;i++){
			// Below you can choose wich way you want to create your table
			// Comment on the corresponding part of the code to choose:

			// Create the table from the source code without xml:
			TableRow row = new TableRow(this);
			row.setBackgroundColor(color.darker_gray);
			table.addView(row);
			fillRow(row,i);
		}
		
	}
	private void fillRow(TableRow row, int noRow){
		String productName = String.valueOf(spnProductName.getSelectedItem());
		final String[] splitString = productName.split("@");
		
		
		// number of rows
		TextView nr = new TextView(this);
		nr.setBackgroundColor(color.white);
		nr.setTextColor(Color.BLUE);
		nr.setText(String.valueOf(noRow));
		row.addView(nr);
		/*LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) nr.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		nr.setLayoutParams(llp);
		nr.setPadding(10, 10, 40, 3);*/
		
		// first name
		TextView firstN = new TextView(this);
		firstN.setBackgroundColor(Color.WHITE);
		firstN.setTextColor(Color.BLUE);
		//firstN.setText(firstNames[noRow]);
		firstN.setText(String.valueOf(splitString[0]));
		row.addView(firstN);
		/*llp = (LinearLayout.LayoutParams) firstN.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		firstN.setLayoutParams(llp);
		firstN.setPadding(10, 10, 20, 3);*/

		
		// last name
		TextView lastN = new TextView(this);
		lastN.setBackgroundColor(Color.WHITE);
		lastN.setTextColor(Color.BLUE);
		lastN.setText(String.valueOf(splitString[1]));
		row.addView(lastN);
		/*llp = (LinearLayout.LayoutParams) lastN.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		lastN.setLayoutParams(llp);
		lastN.setPadding(10, 10, 20, 3);*/
		
	}
	/*private boolean CheckWriteOrderList() throws IOException{
		JXLReader jxlCheckWriteOrderList = new JXLReader();
		return jxlCheckWriteOrderList.getWriteOrderList("\""+Environment.getExternalStorageDirectory()+"\"", "CUST ORDER LIST", String.valueOf(spnProductName.getSelectedItem()));
	}*/
}
