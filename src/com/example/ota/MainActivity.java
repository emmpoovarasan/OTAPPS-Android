package com.example.ota;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button goToOrders, listOfOrders, logOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		goToOrders = (Button)findViewById(R.id.goToOrder);
		goToOrders.setOnClickListener(listenerGoToOrders);
		
		listOfOrders = (Button)findViewById(R.id.listOfOrders);
		listOfOrders.setOnClickListener(listenerListOfOrders);
		
		logOut = (Button)findViewById(R.id.logOut);
		logOut.setOnClickListener(listenerLogOut);
		
	}
	private OnClickListener listenerGoToOrders = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent gotoOrderPage = new Intent(getApplicationContext(), OrderPage.class);
			startActivity(gotoOrderPage);
		}
	};
	private OnClickListener listenerListOfOrders = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent listOfOrdersPage = new Intent(getApplicationContext(), OrderPage.class);
			startActivity(listOfOrdersPage);
		}
	};
	
	private OnClickListener listenerLogOut = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent logOutPage = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(logOutPage);
		}
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
