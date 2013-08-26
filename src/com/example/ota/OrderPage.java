package com.example.ota;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import jxl.read.biff.BiffException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderPage extends Activity {
	Button btnAddOrder;//, btnOrderList;
	Spinner spnBeatName, spnShopName, spnCustomerName;
	ScrollView scrollPage;
	LinearLayout linerLayoutContentView;
	public String pathName;
	public String actualPathName = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_page);
		scrollPage = (ScrollView)findViewById(R.id.scroll_view);
		linerLayoutContentView = (LinearLayout)findViewById(R.id.content);
		//find the location of the excel file
		loadExternalPath();
		//load spinnerBeatName
		loadBeatName();
		//load spinnerShopname
		loadShopName();
		//load spinnerCustomername
		loadCustomerName();
		
		
		/*btnAddOrder = (Button)findViewById(R.id.addorderdetails);
		btnAddOrder.setOnClickListener(checkMyDir);
		Log.d("Loaded", getClass().getSimpleName());*/
		
		/*addOrderDetails.setOnClickListener(clickButton);
		btnOrderList = (Button)findViewById(R.id.orderlist);
		btnOrderList.setOnClickListener(clickButton);*/
		//scrollPage.setOnTouchListener(test);
		scrollPage.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollPage.scrollTo(0, linerLayoutContentView.getPaddingTop());
			}
		});
	}
	
	/*private OnTouchListener test = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			scrollPage.scrollTo(0, linerLayoutContentView.getPaddingTop());
			return false;
		}
	};*/
	private OnClickListener checkMyDir = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), STORAGE_SERVICE, Toast.LENGTH_SHORT).show();
			Log.d("checkMyDir", actualPathName);
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
	private void loadExternalPath(){
		/**
		 * find the location of the excel file
		 */
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
	}
	
}
