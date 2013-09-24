package com.example.ota;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.os.Bundle;
import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EditOrderList extends Activity {
ScrollView svEditOL = null;
HorizontalScrollView hvEditOL = null;
TableLayout tlEditOL = null;
TableRow trEditOL = null;
TextView tvEditShopName = null, tvEditOrderNo = null, tvEditBeatName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_order_list);
		// Get the message from the intent
		Intent getIntentMessage = getIntent();
		String messageReceivedShopName = getIntentMessage.getStringExtra(ListOfOrders.EXTRA_MESSAGE_SHOPNAME);
		//String messageReceivedOrderNo = getIntentMessage.getStringExtra(ListOfOrders.EXTRA_MESSAGE_ORDERNO);
		//Toast.makeText(getApplicationContext(), messageReceivedShopName, 2).show();
		Log.d("RECEIVED MESSAGE FROM LIST OF ORDER LIST PAGE", messageReceivedShopName);
		//Log.d("RECEIVED MESSAGE FROM LIST OF ORDER LIST PAGE", messageReceivedOrderNo);
		tvEditShopName = (TextView)findViewById(R.id.tv_edit_ShopName);
		tvEditShopName.setText(messageReceivedShopName);
		
		/*tvEditOrderNo = (TextView)findViewById(R.id.tv_edit_OrderNo);
		tvEditOrderNo.setText(messageReceivedOrderNo);*/
		
		fngetProducts(messageReceivedShopName, FilePath.getExternalPath());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_order_list, menu);
		return true;
	}

	
	private void fngetProducts(String SheetName, String fileFullPath){
		File f = new File(fileFullPath);
		Workbook w;
		try {
			w = Workbook.getWorkbook(f);
			Sheet sheet = w.getSheet(SheetName);
			
			tlEditOL = (TableLayout)findViewById(R.id.tblEditOrderList);
			TableRow row = null;
			// load values from excel to tablelayout
			for(int i=0;i<sheet.getRows()-1;i++){
				if(i==0){
					Cell cellOrderNo = sheet.getCell(1,i);
					tvEditOrderNo = (TextView)findViewById(R.id.tv_edit_OrderNo);
					tvEditOrderNo.setText(cellOrderNo.getContents().toString());
					
					Cell cellBeatName = sheet.getCell(5,i);
					tvEditBeatName = (TextView)findViewById(R.id.tv_edit_BeatName);
					tvEditBeatName.setText(cellBeatName.getContents().toString());
				}
				if(i>1 && i<sheet.getRows()-1){
					Cell cellProductName = sheet.getCell(1, i);
					Cell cellInStock = sheet.getCell(2, i);
					Cell cellAmount = sheet.getCell(4, i);
					Cell cellNetAmount = sheet.getCell(5,i);
					Log.d("Values of Products in loading tables"+i, cellProductName.getContents() +"@"+ cellInStock.getContents()+"@"+cellAmount.getContents());
					if(cellProductName.getContents().toString() != ""){
						// Create the table from the source code without xml:
						row = new TableRow(this);
						row.setBackgroundColor(color.darker_gray);
						tlEditOL.addView(row);
						// number of rows
						TextView nr = new TextView(this);
						nr.setBackgroundColor(color.darker_gray);
						nr.setTextColor(Color.BLUE);
						nr.setText(String.valueOf(i-1));
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
	
						// netamount
						final TextView netAmount = new TextView(this);
						netAmount.setBackgroundColor(color.darker_gray);
						netAmount.setTextColor(Color.BLUE);
						netAmount.setText(String.valueOf(cellNetAmount.getContents().toString()));
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
							    final Double tvNetAmount = (etOrderQty * tvAmount);
							    
							    v.setBackgroundColor(color.darker_gray);
							    netAmount.setText(String.valueOf(tvNetAmount));
							   
							    
							}
						});
						
					} // end of if statement
				} // end of i>1 if statement
			} // end of for loop
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
