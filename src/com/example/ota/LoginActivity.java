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
		
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		login = (Button)findViewById(R.id.login);
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
			try {
					Log.d("Login status", String.valueOf(checkLoginStatus()));
					if(checkLoginStatus() == true){
						// responding to the User inputs
						Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
						// to navigate other page
						Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(myintent);
					}else{
						Toast.makeText(getApplicationContext(), "Login not successfully", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
	}; 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private boolean checkLoginStatus() throws IOException{
			JXLReader jxlLogin = new JXLReader();	
			if(FilePath.getExternalPath() !=null){
				return jxlLogin.getLogin(FilePath.getExternalPath(), "REP NAME", username.getText().toString().toUpperCase(), password.getText().toString().toUpperCase());	
			}else{
				return false;
			}
		
	}
}
