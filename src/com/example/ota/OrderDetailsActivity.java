package com.example.ota;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class OrderDetailsActivity extends Activity {
	ScrollView scrollOrderDetails;
	RelativeLayout relativeOrderDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		
		scrollOrderDetails = (ScrollView) findViewById(R.id.scroll_view_order_details);
		relativeOrderDetails = (RelativeLayout)findViewById(R.id.relatovelayoutcontent);
		
		scrollOrderDetails.post( new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollOrderDetails.scrollTo(0, relativeOrderDetails.getPaddingTop());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_details, menu);
		return true;
	}

}
