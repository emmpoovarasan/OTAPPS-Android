package com.example.ota;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.R.color;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;


public class TestJava {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws IOException, BiffException {
		// TODO Auto-generated method stub
		File fp = new File("C:\\Users\\POO\\git\\OTAPPS-Android\\OTA\\ORDER APPS1.xls");
		//WritableWorkbook wb = Workbook.createWorkbook(fp);
		WorkbookSettings wbSettings = new WorkbookSettings();
		
		wbSettings.setLocale(new Locale("en", "EN"));
		
		Workbook wb = Workbook.getWorkbook(fp);
		//wb.getNumberOfSheets();
		System.out.println("Reports  =====> "+ wb.getNumberOfSheets());
		//wb.createSheet("Reports", wb.getNumberOfSheets()+1);
		//System.out.println("Reports  =====> "+ wb.getNumberOfSheets());
		
	}

}




/*private void setTable(){
	
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
*/

/*private void fnFillRow(TableRow row, Object element){
	Log.d("fnFillRow(TableRow row, String element)", String.valueOf(element.toString()));
	ArrayList arrayString = new ArrayList();
	arrayString.add(element);
	Iterator it = arrayString.iterator();
	int i = 1;
	while(it.hasNext()){
		Object ele = it.next();
		Log.d(i+"===>", ele.toString());
		i++;
		final String[] splitString = element.split("@");
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
		netAmount.setPadding(10, 10, 40, 3);
		
	}
	
}*/

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