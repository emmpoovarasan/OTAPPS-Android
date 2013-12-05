package com.example.ota;

import java.sql.*;

import android.util.Log;

public class ExportOrders {

	public static String Connections(){
		String outPuts=null;
		Connection conn;
		Statement stmt;
		ResultSet reset;
		
		try {
			String dataBaseDriver = "net.sourceforge.jtds.jdbc.Driver";
			Class.forName(dataBaseDriver);
			Log.d("AFTER CLASS FORNAME ", "SUCCESS");
			String URL = "jdbc:jtds:sqlserver://192.168.1.80:1433/ota;user=sa;password=flower";
			
			conn = DriverManager.getConnection(URL, "sa", "flower");
			
			Log.d("CONNECTION IS OPENED", "SUCCESS");
			
			stmt = conn.createStatement();
			
			Log.d("STATEMENT IS CREATED", "SUCCESS");
			
			reset = stmt.executeQuery("select * from tblmstexportorders");
			
			Log.d("RESULT SET IS OPENED", "SUCCESS");
			
			while (reset.next()) {
				outPuts = outPuts + reset.getString("customer_name");
				Log.d("Data", reset.getString("customer_name"));
				
			}
			
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d("Class Not Found Exception", e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.d("SQL Exception", e.getMessage());
		}
	
		return outPuts;
	}

}