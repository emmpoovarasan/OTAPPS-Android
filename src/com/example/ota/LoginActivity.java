package com.example.ota;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String pathName;
	private String actualPathName = null;
	String userName, passWord;
	EditText username, password;
	Button login;
	ScrollView scrollLoginPage;
	LinearLayout lnrLayContentViewLoginPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// added this line to scroll the page
		//Setting scroll view & content view
		scrollLoginPage = (ScrollView)findViewById(R.id.scroll_view_login);
		lnrLayContentViewLoginPage = (LinearLayout)findViewById(R.id.content_login);
		// load excel file path
		loadExternalPath();
		// UI elements gets bind in form of Java Objects
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		login = (Button)findViewById(R.id.login);
		// now we have got the handle over the UI widgets
        // setting listener on Login Button
        // i.e. OnClick Event
		login.setOnClickListener(loginListener);
		
		// for adding this code scrolling the page;
		scrollLoginPage.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollLoginPage.scrollTo(0, lnrLayContentViewLoginPage.getPaddingTop());
			}
		});
	}
	private OnClickListener loginListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//getting inputs from user and performing data operations
			//if(username.getText().toString().equals("poo") && password.getText().toString().equals("poo")){
				try {
					Log.d("Login status", String.valueOf(checkLoginStatus()));
					if(checkLoginStatus() == true){
						// responding to the User inputs
						Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
						// to navigate other page
						Intent myintent = new Intent(getApplicationContext(), OrderPage.class);
						startActivity(myintent);
					}else{
						Toast.makeText(getApplicationContext(), "Login not successfully", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}/*else{
				Toast.makeText(getApplicationContext(), "Login not successfully", Toast.LENGTH_LONG).show();
			}
		}*/
	}; 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private boolean checkLoginStatus() throws IOException{
		JXLReader jxlLogin = new JXLReader();
		return jxlLogin.getLogin(actualPathName, "REP NAME", username.getText().toString().toUpperCase(), password.getText().toString().toUpperCase());
	}
	private void loadExternalPath(){
		/**
		 * find the location of the excel file
		 */
		try {
			if(Environment.isExternalStorageRemovable()==true){
				Toast.makeText(getApplicationContext(), "ExternalStoageDirectory is found "+Environment.isExternalStorageRemovable(), Toast.LENGTH_SHORT).show();
				Log.d("ExternalStoageDirectory Found", "ExternalStoageDirectory is found "+Environment.isExternalStorageRemovable());
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
				
			}else{
				Toast.makeText(getApplicationContext(), "No ExternalStoageDirectory", Toast.LENGTH_SHORT).show();
				Log.d("No ExternalStoageDirectory", "No ExternalStoageDirectory");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
			Log.d("File Not Found", "File Not Found");
		}
	}
}
