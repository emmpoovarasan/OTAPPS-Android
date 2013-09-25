package com.example.ota;

import java.util.HashMap;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button goToOrders, listOfOrders, logOut;
	SessionManagement session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Session class instance
		session = new SessionManagement(getApplicationContext());
		
		//Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
		
		/**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
		session.checkLogin();
		
			goToOrders = (Button)findViewById(R.id.goToOrder);
			goToOrders.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(session.isLoggedIn() == true){
						Intent gotoOrderPage = new Intent(getApplicationContext(), OrderPage.class);
						startActivity(gotoOrderPage);
					}else{
						session.checkLogin();
					}
					
				}
			});
			
			listOfOrders = (Button)findViewById(R.id.listOfOrders);
			listOfOrders.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(session.isLoggedIn()==true){
						Intent listOfOrdersPage = new Intent(getApplicationContext(), ListOfOrders.class);
						startActivity(listOfOrdersPage);	
					}else{
						session.checkLogin();
					}
					
				}
			});
			/**
	         * Logout button click event
	         * */
			logOut = (Button)findViewById(R.id.logOut);
			logOut.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Clear the session data
		            // This will clear all session data and
		            // redirect user to LoginActivity
					session.logoutUser();
					/*Intent logOutPage = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(logOutPage);*/
				}
			});
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
