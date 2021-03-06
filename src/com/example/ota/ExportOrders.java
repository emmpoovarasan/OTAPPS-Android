package com.example.ota;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import android.util.Log;
import android.widget.Toast;

public class ExportOrders {

	public static String Connections(String dataBaseDriver,String URL,String userName,String userPassword){
		String outPuts= new String();
		Connection conn;
		
		Statement stmt;
		ResultSet reset;
		
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String theDate = df.format(now);
		outPuts = "No orders exports, Please check the excel file";
		try {
			
			//dataBaseDriver = "net.sourceforge.jtds.jdbc.Driver";
			
			Class.forName(dataBaseDriver);
			//Log.d("AFTER CLASS FORNAME ", "SUCCESS");
			//URL = "jdbc:jtds:sqlserver://192.168.1.80:1433/ota;user=sa;password=flower";
			
			
			//conn = DriverManager.getConnection(URL, "sa", "flower");
			conn = DriverManager.getConnection(URL, userName, userPassword);
			
			//Log.d("CONNECTION IS OPENED", "SUCCESS");
			
			stmt = conn.createStatement();
			
			//Log.d("STATEMENT IS CREATED", "SUCCESS");
			
			
			File excelFile;
			Workbook wb;
			Sheet st;
			int totalSheets;
			
			try {
				excelFile = new File(FilePath.getExternalPath());
				wb = Workbook.getWorkbook(excelFile);
				wb.getSheetNames();
				totalSheets = wb.getNumberOfSheets();
				//Log.d("Total no of Sheets", String.valueOf(totalSheets));
				for(int i=4; i<totalSheets;i++){
					
					//Log.d("Sheet"+i, wb.getSheet(i).getName());
					st = wb.getSheet(i);
					String tblMstString = new String();
					String tblDtlString = new String();
					String suffixSymbol = "','";
					String customerName, orderNo = null, orderDate = null,beatName,repName = null,slno,productName;
					String stockQty,orderQty, orderdAmount, ord_Qty,qty_Rate,qty_Free,tradeDis,cashDis,tot_ord_Rate;
					
					
					customerName = wb.getSheet(i).getName();
					 
					for(int j=0;j<st.getRows();j++){
						
						if(j==0){
							/*Log.d("Master", st.getCell(0, j).getContents()+" - "+st.getCell(1, j).getContents()
									+" - "+st.getCell(2, j).getContents()+" - "+st.getCell(3, j).getContents()
									+" - "+st.getCell(4, j).getContents()+" - "+st.getCell(5, j).getContents()
									+" - "+st.getCell(6, j).getContents()+" - "+st.getCell(7, j).getContents());*/
							orderNo = st.getCell(1, j).getContents();
							String tempDate = st.getCell(3, j).getContents();
							SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
							Calendar c = Calendar.getInstance();
							c.setTime(format1.parse(tempDate));
							
							orderDate = df.format(c.getTime()) ;//st.getCell(3, j).getContents();
							beatName = st.getCell(5, j).getContents();
							repName = st.getCell(7, j).getContents();
							
							tblMstString = tblMstString +"('"+ customerName + suffixSymbol + orderNo + suffixSymbol
									+ orderDate + suffixSymbol + beatName +suffixSymbol
									+ repName + "',";
							
						}
						
						if(j>1 && j<st.getRows()-1){
							/*Log.d("Details", st.getCell(0, j).getContents()+" - "+st.getCell(1, j).getContents()
									+" - "+st.getCell(2, j).getContents()+" - "+st.getCell(3, j).getContents()
									+" - "+st.getCell(4, j).getContents()+" - "+st.getCell(5, j).getContents()
									+" - "+st.getCell(6, j).getContents()+" - "+st.getCell(7, j).getContents());*/
							slno = st.getCell(0, j).getContents();
							productName = st.getCell(1, j).getContents();
							stockQty = st.getCell(2, j).getContents();
							ord_Qty = st.getCell(3, j).getContents();
							qty_Rate = st.getCell(4, j).getContents();
							qty_Free = st.getCell(5, j).getContents();
							tradeDis = st.getCell(6, j).getContents();
							cashDis = st.getCell(7, j).getContents();
							tot_ord_Rate = st.getCell(8, j).getContents();
							
							
							tblDtlString = tblDtlString+"('"+customerName + suffixSymbol + orderNo + suffixSymbol
									+ slno + suffixSymbol
									+  productName +  "'," + stockQty + ","+ ord_Qty +","+qty_Rate+","
									+ qty_Free +","
									+ tradeDis +"," + cashDis +"," + tot_ord_Rate
									+ ",'" + repName + suffixSymbol + theDate+"')";
							
							//Log.d("Details table", tblDtlString);
							String InsertTblDtl = "insert into tbldtlexportorders values "+tblDtlString;
							//Log.d("Details Table Query Builder", InsertTblDtl);
							stmt.executeUpdate(InsertTblDtl);
							//Log.d("Details Table Query Inserted", "Success");
							tblDtlString ="";
							outPuts = "Orders exported successfully";
							
						}
						
						if(j==st.getRows()-1){
							/*Log.d("Footer", st.getCell(0, j).getContents()+" - "+st.getCell(1, j).getContents()
									+" - "+st.getCell(2, j).getContents()+" - "+st.getCell(3, j).getContents()
									+" - "+st.getCell(4, j).getContents()+" - "+st.getCell(5, j).getContents()
									+" - "+st.getCell(6, j).getContents()+" - "+st.getCell(7, j).getContents());*/
							
							orderQty = st.getCell(3, j).getContents();
							orderdAmount = st.getCell(8, j).getContents();
							
							tblMstString = tblMstString + orderQty + ","
									+ orderdAmount +",'" + repName + suffixSymbol + theDate+"')";
						}
						
					}
					
					//Log.d("Master Table", tblMstString);
					String InsertTblMst = "insert into tblmstexportorders values "+tblMstString;
					//Log.d("Master Table Query Builder", InsertTblMst);
					stmt.executeUpdate(InsertTblMst);
					//Log.d("Master Table Query Inserted", "Success");
					tblMstString = "";
					
					outPuts = "Orders exported successfully";
					/*Log.d("Details table", tblDtlString);
					String InsertTblDtl = "insert into tbldtlexportorders values "+tblDtlString;
					Log.d("Details Table Query Builder", InsertTblDtl);
					stmt.executeUpdate(InsertTblDtl);
					Log.d("Details Table Query Inserted", "Success");*/
				}
				
			} catch (BiffException e) {
				//Log.d("BiffException", e.getMessage());
				Toast.makeText(null, e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				//Log.d("IO Exception", e.getMessage());
				Toast.makeText(null, e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (ParseException e) {
				//Log.d("ParseException", e.getMessage());
				Toast.makeText(null, e.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			conn.close();
			
		} catch (ClassNotFoundException e) {
			//Log.d("Class Not Found Exception", e.getMessage());
			Toast.makeText(null, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (SQLException e) {
			//Log.d("SQL Exception", e.getMessage());
			Toast.makeText(null, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	
		return outPuts;
	}

}


/*String insertQry;
insertQry = "INSERT INTO TblMstExportOrders VALUES('customer_name','order_no','2013-12-10',
'beat_name','rep_name'," +
		"18,1800,'entered_by',2013-12-10)";

stmt.executeUpdate(insertQry);

reset = stmt.executeQuery("select * from tblmstexportorders");

Log.d("RESULT SET IS OPENED", "SUCCESS");

while (reset.next()) {
	outPuts = outPuts + reset.getString("customer_name");
	Log.d("Data", reset.getString("customer_name"));
	
}*/