package com.example.ota;

import java.io.File;
import java.text.DecimalFormat;
import java.util.zip.Inflater;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfOrders extends Activity {
	Button btnGotoOrder, btnDashBoard, btnLogOut;//, btnShowListOfOrders;
	TableLayout tblListOfOrders = null;
	TableRow tblRow = null;
	ScrollView svListOfOrders = null;
	HorizontalScrollView hvListOfOrders = null;
	HorizontalScrollView hvListOfOrderButton = null;
	
	TextView lbl_ListOfTotalNetAmount = null;
	
	public final static String EXTRA_MESSAGE_SHOPNAME = null; 
	//public final static String EXTRA_MESSAGE_ORDERNO = null;
	final DecimalFormat dcf = new DecimalFormat("0.00");
	
	SessionManagement session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_orders);
		// Session class instance
		session = new SessionManagement(getApplicationContext());
		//Toast.makeText(getApplicationContext(), "User Login Status: "+ session.isLoggedIn(), Toast.LENGTH_LONG).show();
		/**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
		session.checkLogin();
		
		fnLoadOrderList(FilePath.getExternalPath());
		
		//Log.d("Start Execute Total Amount Calc", "Starts --------");
		lbl_ListOfTotalNetAmount = (TextView)findViewById(R.id.tvListOfTotalNetAmount);
		lbl_ListOfTotalNetAmount.setText(Html.fromHtml("<b>Total Net Amount : "+ dcf.format(getTotalNetAmountAfterChangedOrder()) +"</b>"));
		//Log.d("End Executed Total Amount Calc", "Ends --------");
		
		btnGotoOrder = (Button)findViewById(R.id.btnOLgoToOrder);
		btnGotoOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(session.isLoggedIn() == true){
					Intent myPage = new Intent(getApplicationContext(), OrderPage.class);
					startActivity(myPage);
				}else{
					session.checkLogin();
				}
			}
		});
		
		btnDashBoard = (Button)findViewById(R.id.btnOLdashBoard);
		btnDashBoard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(session.isLoggedIn() == true){
					Intent myPage = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(myPage);
				}else{
					session.checkLogin();
				}
			}
		});
		// button for logout
		btnLogOut = (Button)findViewById(R.id.btnOLlogOut);
		btnLogOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clear the session data
	            // This will clear all session data and
	            // redirect user to LoginActivity
				session.logoutUser();
			}
		});
		
		svListOfOrders = (ScrollView)findViewById(R.id.svListOfOrdersTable);
		svListOfOrders.post(new Runnable() {
			@Override
			public void run() {
				svListOfOrders.pageScroll(svListOfOrders.getMaxScrollAmount());
			}
		});
		
		hvListOfOrders = (HorizontalScrollView)findViewById(R.id.hvListOfOrdersTable);
		hvListOfOrders.post(new Runnable() {
			@Override
			public void run() {
				hvListOfOrders.pageScroll(hvListOfOrders.getMaxScrollAmount());
			}
		});
		
		hvListOfOrderButton = (HorizontalScrollView)findViewById(R.id.hvListOfOrderButton);
		hvListOfOrderButton.post(new Runnable() {
			@Override
			public void run() {
				hvListOfOrderButton.pageScroll(hvListOfOrderButton.getMaxScrollAmount());
			}
		});
		
	}
	private void fnLoadOrderList(String pathName){
		try {
			if(session.isLoggedIn() == true){
				File fp = new File(pathName);
				Workbook wb;
				wb = Workbook.getWorkbook(fp);
				Sheet st = null;
				tblListOfOrders = (TableLayout)findViewById(R.id.tblListOfOrders);
				
				for(int i = 0; i<wb.getNumberOfSheets();i++){
					st = wb.getSheet(i);
					if(i>3){
						tblRow = new TableRow(this);	
						tblListOfOrders.addView(tblRow);
						
						TextView tvSno = new TextView(this);
						//tvSno.setText(st.getName());
						tvSno.setText(String.valueOf(i-3));
						tblRow.addView(tvSno);
						
						TextView tvCustomer = new TextView(this);
						tvCustomer.setText(st.getName());
						tvCustomer.setPadding(10, 10, 40, 3);
						tblRow.addView(tvCustomer);
						
						final TextView tvOrderNo = new TextView(this);
						//tvOrderNo.setText(st.getName());
						tvOrderNo.setText(getVauesToLoadOrderNoQtyNetAmount(FilePath.getExternalPath(),"ORDERNO",st.getName()));
						tblRow.addView(tvOrderNo);
						tvOrderNo.setPadding(10, 10, 40, 3);
						
						/*TextView tvOrderedQty = new TextView(this);
						//tvOrderedQty.setText(st.getName());
						tvOrderedQty.setText(getVauesToLoadOrderNoQtyNetAmount(FilePath.getExternalPath(),"ORDEREDQTY", st.getName()));
						tblRow.addView(tvOrderedQty);
						tvOrderedQty.setPadding(10, 10, 40, 3);*/
						
						TextView tvTotalNetAmount = new TextView(this);
						//tvTotalNetAmount.setText(st.getName());
						tvTotalNetAmount.setText(getVauesToLoadOrderNoQtyNetAmount(FilePath.getExternalPath(),"NETAMOUNT", st.getName()));
						tvTotalNetAmount.setGravity(Gravity.RIGHT);
						tblRow.addView(tvTotalNetAmount);
						tvTotalNetAmount.setPadding(10, 10, 40, 3);
						//Toast.makeText(getApplicationContext(), i+"-"+st.getName(), Toast.LENGTH_SHORT).show();
						//Toast.makeText(getApplicationContext(), pathName, Toast.LENGTH_SHORT).show();
						
						tblRow.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								v.setBackgroundColor(Color.YELLOW);
								final TableRow t = (TableRow)v;
								TextView tvSno = (TextView)t.getChildAt(0);
								TextView tvShopName = (TextView)t.getChildAt(1);
								/*TextView tvOrderedQty = (TextView)t.getChildAt(2);*/
								TextView tvNetAmount = (TextView)t.getChildAt(2);
								/*String StringTvShopName = tvShopName.getText().toString();*/
								
								Intent myOrder = new Intent(getApplicationContext(), EditOrderList.class);
								/*String messageShopName = tvShopName.getText().toString();*/
								myOrder.putExtra(EXTRA_MESSAGE_SHOPNAME, tvShopName.getText().toString());
								//myOrder.putExtra(EXTRA_MESSAGE_ORDERNO, tvOrderNo.getText().toString());
								startActivity(myOrder);
								v.setBackgroundColor(Color.GREEN);
								finish();
							}
						});
					}
				}
				
			}else{
				session.checkLogin();
			}
		} catch (Exception e) {
			//Log.d("Exception", e.getMessage());
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private String getVauesToLoadOrderNoQtyNetAmount(String filePageName, String returnType, String SelectedSheetName){
		String MyReturn = null;
		Double CalcOrderedQty = 0.0, CalcNetAmount = 0.0;
		//Toast.makeText(getApplicationContext(), "Success", 2).show();
		try {
			File fp = new File(filePageName);
			Workbook wb;
			wb = Workbook.getWorkbook(fp);
			Sheet st = null;
			st = wb.getSheet(SelectedSheetName);
			//Log.d("SELECTED SHEET NAME", SelectedSheetName);
			Cell cellOrderNo = st.getCell(1, 0);
			Cell cellOrderedQty = st.getCell(3, st.getRows()-1);
			Cell cellNetAmount = st.getCell(8, st.getRows()-1);
			//Log.d("GetRowsCount", String.valueOf(st.getRows()-1));
			if(returnType == "ORDERNO"){
				MyReturn = String.valueOf(cellOrderNo.getContents().toString());
				//Toast.makeText(getApplicationContext(), returnType+"/"+MyReturn, 2).show();
				//Log.d("ORDERNO", returnType+"/"+MyReturn);
			}
			/*if(returnType == "ORDEREDQTY"){
				CalcOrderedQty += Double.valueOf(cellOrderedQty.getContents().toString());
				 MyReturn = String.valueOf(CalcOrderedQty);
				 //Toast.makeText(getApplicationContext(), returnType+"/"+MyReturn, 2).show();
				 Log.d("ORDEREDQTY", returnType+"/"+MyReturn);
			}*/
			if(returnType == "NETAMOUNT"){
				CalcNetAmount += Double.valueOf(cellNetAmount.getContents().toString());
				MyReturn = String.valueOf(CalcNetAmount);
				//Toast.makeText(getApplicationContext(), returnType+"/"+MyReturn, 2).show();
				//Log.d("NETAMOUNT", returnType+"/"+MyReturn);
			}
						
		} catch (Exception e) {
			//Log.d("Exception", e.getMessage());
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return MyReturn;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_of_orders, menu);
		return true;
	}

	private Double getTotalNetAmountAfterChangedOrder(){
		Double Calc_TotalNetAmount = 0.0;
		String strNetAmount = null;
		//tblListOfOrders = (TableLayout)findViewById(R.id.tableLayoutOrder);
			for(int i = 0;i<tblListOfOrders.getChildCount();i++){
				if(i>0 &&  i < tblListOfOrders.getChildCount()){
					strNetAmount = String.valueOf(((TextView)((TableRow)tblListOfOrders.getChildAt(i)).getChildAt(3)).getText());
					if(strNetAmount.length()==0){
						strNetAmount ="0";
					}
					Calc_TotalNetAmount += Double.valueOf(strNetAmount);
				}
			}
		return Calc_TotalNetAmount;
	}
}
