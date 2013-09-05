package com.example.ota;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.CellValue;

import android.R.color;
import android.R.integer;
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
	Button btnLoadProductList, btnSaveOrder, btnDashBoard, btnLogOut;
	Spinner spnBeatName, spnShopName;//, spnCustomerName, spnProductName;
	ScrollView scrollOrderPage;
	LinearLayout lnrLayContentViewOrderPage;
	TableLayout tblLoadProductList;
	HorizontalScrollView hsv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_page);

		scrollOrderPage = (ScrollView)findViewById(R.id.scroll_view);
		lnrLayContentViewOrderPage = (LinearLayout)findViewById(R.id.content);
		
		fnloadBeatName();
		
		fnloadShopName();
		
		hsv = (HorizontalScrollView)findViewById(R.id.horizontalView);
		hsv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				hsv.pageScroll(hsv.getMaxScrollAmount());
				
			}
		});
		
		scrollOrderPage.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//scrollOrderPage.scrollTo(0, lnrLayContentViewOrderPage.getPaddingTop());
				scrollOrderPage.pageScroll(scrollOrderPage.getMaxScrollAmount());
			}
		});
		
		btnLoadProductList = (Button)findViewById(R.id.btnLoadProductList);
		btnLoadProductList.setOnClickListener(listenerLoadProductList);
		Log.d("Loaded", getClass().getSimpleName());
		
		btnSaveOrder = (Button)findViewById(R.id.btnSaveOrder);
		btnSaveOrder.setOnClickListener(listenerSaveOrder);
		
		btnDashBoard = (Button)findViewById(R.id.btnDashboard);
		btnDashBoard.setOnClickListener(listenerDashBoard);
		
		btnLogOut = (Button)findViewById(R.id.btnLogOut);
		btnLogOut.setOnClickListener(listenerLogOut);
		
		
	}
	
	private OnClickListener listenerLogOut = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myInt = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(myInt);
		}
	};
	
	private OnClickListener listenerDashBoard = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(myInt);
		}
	};
	
	private OnClickListener listenerLoadProductList = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "Add to Order List", Toast.LENGTH_SHORT).show();
			Log.d("Click LoadProduct List", "Click LoadProduct List");
			
			/*Intent myintent = new Intent(getApplicationContext(), DashboardPage.class);
			Toast.makeText(getApplicationContext(), "Navigate to order details page", Toast.LENGTH_SHORT).show();
			startActivity(myintent);*/
			//setTable();
			fngetProducts();
		}
	};
	
	private OnClickListener listenerSaveOrder = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fnReadTableRowValues();
		}
	};

	private void fnReadTableRowValues(){
		//Toast.makeText(getApplicationContext(), "Total Child Count is "+tblLoadProductList.getChildCount(), Toast.LENGTH_SHORT).show();
		try {
			HSSFWorkbook workbook = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			String formule_NetAmount = null;
			String filename = null;
			filename = FilePath.getExternalPath();
			File fp = new File(filename);
			FileInputStream is = new FileInputStream(fp);
			String sheetName = spnShopName.getSelectedItem().toString();
			
			Date now = Calendar.getInstance().getTime();
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String theDate = df.format(now);
			//System.out.println(theDate); 
			
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
				
				for(int i = 1; i<tblLoadProductList.getChildCount();i++){
					
					String iRow,inStock,orderQty,productNames,amount,netAmount;
					
					iRow=String.valueOf(i);
					productNames = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(1)).getText());
					inStock = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(2)).getText());
					orderQty = String.valueOf(((EditText)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(3)).getText().toString());	
					amount = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(4)).getText());
					netAmount = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(5)).getText());
					//netAmount = String.valueOf(Double.parseDouble(orderQty)*Double.parseDouble(amount));
					//netAmount =  String.valueOf(((EditText)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(3)).getText().toString());
						if(i==1){
							row = sheet.createRow(i-1);
							Log.d("Main Row value", String.valueOf(i-1));
							cell = row.createCell((short)0);
							cell.setCellValue("Order No");
							cell = row.createCell((short)1);
							cell.setCellValue(theDate+"/"+iRow);
							cell = row.createCell((short)2);
							cell.setCellValue("Date");
							cell = row.createCell((short)3);
							cell.setCellValue(theDate);
							
							row = sheet.createRow(i);
							Log.d("Header Row value", String.valueOf(i+1));
							cell = row.createCell((short)0);
							cell.setCellValue("S.No");
							
							cell = row.createCell((short)1);
							cell.setCellValue("Product Name");
							
							cell = row.createCell((short)2);
							cell.setCellValue("Stock Qty");
							
							cell = row.createCell((short)3);
							cell.setCellValue("Ordered Qty");
							
							cell = row.createCell((short)4);
							cell.setCellValue("Amount");
							
							cell = row.createCell((short)5);
							cell.setCellValue("Net Amount");
						}
						
						row = sheet.createRow(i+1);
						Log.d("Details rows", String.valueOf(i+2));
						cell = row.createCell((short)0);
						cell.setCellValue(iRow);
						
						cell = row.createCell((short)1);
						cell.setCellValue(productNames);
						
						cell = row.createCell((short)2);
						cell.setCellValue(inStock);
						
						cell = row.createCell((short)3);
						cell.setCellValue(orderQty);
						
						cell = row.createCell((short)4);
						cell.setCellValue(amount);
						
						cell = row.createCell((short)5);
						//formule_NetAmount = "SUM(D"+(i+1)+",E"+(i+1)+")";
						//cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
						cell.setCellValue(netAmount);
						//cell.setCellFormula(formule_NetAmount);
						
					
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
	private void commented(){
		/*//TableLayout t1 = (TableLayout)findViewById(R.id.tableLayoutOrder);
		//TableRow row = new TableRow(this.getParent());
		Toast.makeText(getApplicationContext(), "Total Child Count is "+tblLoadProductList.getChildCount(), Toast.LENGTH_SHORT).show();
		for(int i = 1; i<tblLoadProductList.getChildCount();i++){
			Integer iRow,inStock,orderQty;
			String productNames;
			Double amount,netAmount;
			String iRow,inStock,orderQty,productNames,amount,netAmount;
			
			Log.d("tblLoadProductList.getChildAt(i)", String.valueOf(tblLoadProductList.getChildAt(i)));
			//String s = String.valueOf(tblLoadProductList.getChildAt(i));
			
			//Toast.makeText(getApplicationContext(), "Get Row value "+ String.valueOf(tblLoadProductList.getChildAt(i).getContentDescription().toString()), Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "Total child count of Rows "+row.getChildCount(), Toast.LENGTH_SHORT).show();
			for(int j=1; j<=row.getChildCount();j++){
				Toast.makeText(getApplicationContext(), "Get Row Column value "+ String.valueOf(row.getChildAt(j)), Toast.LENGTH_SHORT).show();
			}
			
			//TableRow row = (TableRow)tblLayout.getChildAt(0);
			//TextView textView = (TextView)row.getChildAt(XXX);
			// blah blah textView.getText();			
			iRow=String.valueOf(i);
			productNames = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(1)).getText());
			inStock = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(2)).getText());
			orderQty = String.valueOf(((EditText)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(3)).getText().toString());
			
			amount = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(4)).getText());
			netAmount = String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(5)).getText());
			
			//Log.d("Value of row" + i,String.valueOf(((TextView)((TableRow)tblLoadProductList.getChildAt(i)).getChildAt(1)).getText()));
			Log.d("List of products"+i, String.valueOf(iRow+"@"+productNames+"@"+inStock+"@"+orderQty+"@"+amount+"@"+netAmount));
			
			if(i==3){
				break;
			}
		}*/
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
	
	
	
	private void setTable(){
		
		tblLoadProductList = (TableLayout)findViewById(R.id.tableLayoutOrder);
		
		// Create the table from the source code without xml:
		TableRow row = new TableRow(this);
		row.setBackgroundColor(color.darker_gray);
		tblLoadProductList.addView(row);
		
		JXLReader jl = new JXLReader();
		jl.setInputFile(FilePath.getExternalPath(), "PRODUCT NAME", null);
		
		Toast.makeText(getApplication(), "setTable()", Toast.LENGTH_SHORT).show();
		
		Log.d("setTable()", "setTable() is called");
		
		List productList = new ArrayList();
		
		try {
			productList.add(jl.getProductName());
			Log.d("End of getProductName", "productList.add(jl.getProductName()) is called");
			if (productList.size() > 0){
				Log.d("Check Status of productList.size()", String.valueOf(productList.size()));
				Iterator itr = productList.iterator();
				Log.d("Test",itr.toString()); 
				while(itr.hasNext()){
					Object element = itr.next();
					Log.d("Object element ", element.toString());
					Toast.makeText(getApplicationContext(), (String)element.toString(), Toast.LENGTH_SHORT).show();
					Log.d("Object element splited ", String.valueOf(element.toString().split(",").toString()+"\n"));
					fnFillRow(row, element);
				}
			}else{
				Log.d("productList.size() is", String.valueOf(productList.size()));
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	private void fngetProducts(){
		File f = new File(FilePath.getExternalPath());
		Workbook w;
		try {
			w = Workbook.getWorkbook(f);
			Sheet sheet = w.getSheet("PRODUCT NAME");
			
			tblLoadProductList = (TableLayout)findViewById(R.id.tableLayoutOrder);
			
			//tblLoadProductList.setColumnShrinkable(0, true);
			//tblLoadProductList.setColumnShrinkable(1, true);
			//tblLoadProductList.setColumnShrinkable(2, true);
			/*tblLoadProductList.setColumnShrinkable(3, true);
			tblLoadProductList.setColumnShrinkable(4, true);
			tblLoadProductList.setColumnShrinkable(5, true);*/
			
			
			for(int i=1;i<sheet.getRows();i++){
				
				Cell cellProductName = sheet.getCell(1, i);
				Cell cellInStock = sheet.getCell(2, i);
				Cell cellAmount = sheet.getCell(4, i);
				
				Log.d("Values of Products in loading tables"+i, cellProductName.getContents() +"@"+ cellInStock.getContents()+"@"+cellAmount.getContents());
				if(cellProductName.getContents().toString() != ""){
						//treeSetProductName.add(cellProductName.getContents()+"@"+cellInStock.getContents()+"@"+cellAmount.getContents());	
					//arrayProductName.add(cellProductName.getContents()+"@"+cellInStock.getContents()+"@"+cellAmount.getContents());
					// Create the table from the source code without xml:
					TableRow row = new TableRow(this);
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
					TextView netAmount = new TextView(this);
					netAmount.setBackgroundColor(color.darker_gray);
					netAmount.setTextColor(Color.BLUE);
					netAmount.setText("00000.00");
					row.addView(netAmount);
					llp = (LinearLayout.LayoutParams) netAmount.getLayoutParams();
					llp.setMargins(0, 0, 0, 1);
					netAmount.setLayoutParams(llp);
					netAmount.setPadding(10, 10, 40, 3);
					
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	private void fnFillRow(TableRow row, Object element){
		Log.d("fnFillRow(TableRow row, String element)", String.valueOf(element.toString()));
		ArrayList arrayString = new ArrayList();
		arrayString.add(element);
		Iterator it = arrayString.iterator();
		int i = 1;
		while(it.hasNext()){
			Object ele = it.next();
			Log.d(i+"===>", ele.toString());
			i++;
			/*final String[] splitString = element.split("@");
			// number of rows
			TextView nr = new TextView(this);
			nr.setBackgroundColor(color.darker_gray);
			nr.setTextColor(Color.BLUE);
			nr.setText(String.valueOf(tblLoadProductList.getChildCount()));
			row.addView(nr);
			LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) nr.getLayoutParams();
			llp.setMargins(0, 0, 0, 1);
			nr.setLayoutParams(llp);
			nr.setPadding(10, 10, 40, 3);
			
			// product name
			TextView prdName = new TextView(this);
			prdName.setBackgroundColor(color.darker_gray);
			prdName.setTextColor(Color.BLUE);
			prdName.setText(String.valueOf(splitString[0]));
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
			inStock.setText(String.valueOf(splitString[1]));
			row.addView(inStock);
			llp = (LinearLayout.LayoutParams) inStock.getLayoutParams();
			llp.setMargins(0, 0, 0, 1);
			inStock.setLayoutParams(llp);
			inStock.setPadding(10, 10, 40, 3);
			
			// orderqty
			EditText orderQty = new EditText(this);
			orderQty.setBackgroundColor(Color.YELLOW);
			orderQty.setTextColor(Color.BLACK);
			row.addView(orderQty);
			llp = (LinearLayout.LayoutParams) orderQty.getLayoutParams();
			llp.setMargins(0, 0, 0, 1);
			orderQty.setLayoutParams(llp);
			orderQty.setPadding(10, 10, 40, 3);
			
			// amount
			TextView amount = new TextView(this);
			amount.setBackgroundColor(color.darker_gray);
			amount.setTextColor(Color.BLUE);
			amount.setText(String.valueOf(splitString[2]));
			row.addView(amount);
			llp = (LinearLayout.LayoutParams) amount.getLayoutParams();
			llp.setMargins(0, 0, 0, 1);
			amount.setLayoutParams(llp);
			amount.setPadding(10, 10, 40, 3);
			//calculate
			//Double Total = Integer.parseInt(String.valueOf(splitString[1])) * Double.parseDouble(String.valueOf(splitString[2]));
			// netamount
			TextView netAmount = new TextView(this);
			netAmount.setBackgroundColor(color.darker_gray);
			netAmount.setTextColor(Color.BLUE);
			netAmount.setText("0000");
			row.addView(netAmount);
			llp = (LinearLayout.LayoutParams) netAmount.getLayoutParams();
			llp.setMargins(0, 0, 0, 1);
			netAmount.setLayoutParams(llp);
			netAmount.setPadding(10, 10, 40, 3);*/
			
		}
		
	}
	
}

/*private void buildOrderTable(){
Integer rowCount = JXLReader.getProductRows(FilePath.getExternalPath(), "PRODUCT NAME");
TableLayout table = (TableLayout)findViewById(R.id.tableLayoutOrder);
ArrayList<String> arrayProductName = null;
try {
	arrayProductName = loadProductName("PRODUCT NAME");
	Iterator it = arrayProductName.iterator();
	int i = 1;
	while (it.hasNext()) {
		Object element = it.next();
		TableRow row = new TableRow(this);
		row.setBackgroundColor(color.darker_gray);
		table.addView(row);
		fillRow(String.valueOf(element),row,i);
		i++;
	}
	for(int i=0;i<rowCount;i++){
		// Below you can choose wich way you want to create your table
		// Comment on the corresponding part of the code to choose:

		// Create the table from the source code without xml:
		TableRow row = new TableRow(this);
		row.setBackgroundColor(color.darker_gray);
		table.addView(row);
		fillRow(row,i);
	}
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}


}
private void fillRow(String element,TableRow row, int noRow){
//String productName = String.valueOf(spnProductName.getSelectedItem());
String productName = element;
final String[] splitString = productName.split("@");

		// number of rows
		TextView nr = new TextView(this);
		nr.setBackgroundColor(color.white);
		nr.setTextColor(Color.BLUE);
		nr.setText(noRow);
		row.addView(nr);
		LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) nr.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		nr.setLayoutParams(llp);
		nr.setPadding(10, 10, 40, 3);
		
		// product name
		TextView prdName = new TextView(this);
		prdName.setBackgroundColor(Color.WHITE);
		prdName.setTextColor(Color.BLUE);
		prdName.setText(String.valueOf(splitString[0]));
		prdName.setHorizontalFadingEdgeEnabled(true);
		row.addView(prdName);
		llp = (LinearLayout.LayoutParams) prdName.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		prdName.setLayoutParams(llp);
		prdName.setPadding(10, 10, 40, 3);
						
		// instock
		TextView inStock = new TextView(this);
		inStock.setBackgroundColor(Color.WHITE);
		inStock.setTextColor(Color.BLUE);
		inStock.setText(String.valueOf(splitString[1]));
		row.addView(inStock);
		llp = (LinearLayout.LayoutParams) inStock.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		inStock.setLayoutParams(llp);
		inStock.setPadding(10, 10, 40, 3);
		
		// orderqty
		EditText orderQty = new EditText(this);
		orderQty.setBackgroundColor(Color.YELLOW);
		orderQty.setTextColor(Color.BLACK);
		row.addView(orderQty);
		llp = (LinearLayout.LayoutParams) orderQty.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		orderQty.setLayoutParams(llp);
		orderQty.setPadding(10, 10, 40, 3);
		
		// amount
		TextView amount = new TextView(this);
		amount.setBackgroundColor(Color.WHITE);
		amount.setTextColor(Color.BLUE);
		amount.setText(String.valueOf(splitString[2]));
		row.addView(amount);
		llp = (LinearLayout.LayoutParams) amount.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		amount.setLayoutParams(llp);
		amount.setPadding(10, 10, 40, 3);
		//calculate
		Double Total = Integer.parseInt(String.valueOf(splitString[1])) * Double.parseDouble(String.valueOf(splitString[2]));
		// netamount
		TextView netAmount = new TextView(this);
		netAmount.setBackgroundColor(Color.WHITE);
		netAmount.setTextColor(Color.BLUE);
		netAmount.setText(String.valueOf(Total));
		row.addView(netAmount);
		llp = (LinearLayout.LayoutParams) netAmount.getLayoutParams();
		llp.setMargins(0, 0, 0, 1);
		netAmount.setLayoutParams(llp);
		netAmount.setPadding(10, 10, 40, 3);
		

// number of rows
TextView nr = new TextView(this);
nr.setBackgroundColor(color.white);
nr.setTextColor(Color.BLUE);
nr.setText(String.valueOf(noRow));
row.addView(nr);
LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) nr.getLayoutParams();
llp.setMargins(0, 0, 0, 1);
nr.setLayoutParams(llp);
nr.setPadding(10, 10, 40, 3);

// first name
TextView firstN = new TextView(this);
firstN.setBackgroundColor(Color.WHITE);
firstN.setTextColor(Color.BLUE);
//firstN.setText(firstNames[noRow]);
firstN.setText(String.valueOf(splitString[0]));
row.addView(firstN);
llp = (LinearLayout.LayoutParams) firstN.getLayoutParams();
llp.setMargins(0, 0, 0, 1);
firstN.setLayoutParams(llp);
firstN.setPadding(10, 10, 20, 3);


// last name
TextView lastN = new TextView(this);
lastN.setBackgroundColor(Color.WHITE);
lastN.setTextColor(Color.BLUE);
lastN.setText(String.valueOf(splitString[1]));
row.addView(lastN);
llp = (LinearLayout.LayoutParams) lastN.getLayoutParams();
llp.setMargins(0, 0, 0, 1);
lastN.setLayoutParams(llp);
lastN.setPadding(10, 10, 20, 3);

}*/

/*private ArrayList<String> loadProductName(String mySheet) throws IOException{
JXLReader jxlLoadProductName = new JXLReader();
jxlLoadProductName.setInputFile(FilePath.getExternalPath(), mySheet,null);
Log.d("loadProductsName function", "Success to loadProductsName");
return jxlLoadProductName.getProductName();
}
private ArrayList<String> loadCustomerName(String mySheet, String customerName) throws IOException{
JXLReader jxlLoadCustomerName = new JXLReader();
jxlLoadCustomerName.setInputFile(FilePath.getExternalPath(), mySheet,customerName);
Log.d("loadShopName function", "Success to loadShopName");
return jxlLoadCustomerName.getCustomerName();
}*/

/*
 * 		//spnShopName.setOnItemSelectedListener(listenerLoadCustomerName);
 private OnItemSelectedListener listenerLoadCustomerName = new OnItemSelectedListener() {

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(arg0.getContext(), 
				"OnItemSelectedListener : " + arg0.getItemAtPosition(arg2).toString(),
				Toast.LENGTH_SHORT).show();
		loadCustomerName();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
};*/
/**
 * load spinner customer name
 *//*
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
};*/

/**
 * load spinner product name
 *//*
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
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
};*/