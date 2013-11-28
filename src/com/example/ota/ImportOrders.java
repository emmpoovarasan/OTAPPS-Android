package com.example.ota;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.jtds.jdbc.*;

import android.util.Log;

public class ImportOrders {

	public void Connections(){
		Connection conn = null;
		String driver;
		String connString, username, password;
		Statement stmt;
		ResultSet reset;
		try {
			driver = "net.sourceforge.jtds.jdbc.Driver";
			Class.forName(driver).newInstance();
			connString = "jdbc:jtds:sqlserver://server_ip_address:1433/DBNAME;encrypt=fasle;user=xxxxxxxxx;" +
					"password=xxxxxxxx;instance=SQLEXPRESS;";
			username ="poovarasan";
			password = "poovarasan";
			conn = DriverManager.getConnection(connString, username, password);
			stmt = conn.createStatement();
			reset = stmt.executeQuery("select * from table");
			
			
			while (reset.next()) {
				Log.w("Data", reset.getString("name"));
				
			}
			
			conn.close();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
