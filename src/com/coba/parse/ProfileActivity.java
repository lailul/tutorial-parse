package com.coba.parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class ProfileActivity extends Activity{
	private TextView hello_text;
	private ParseUser user;
	private Button logout_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		hello_text = (TextView)findViewById(R.id.hello_text);
		user = ParseUser.getCurrentUser();
		hello_text.setText("Hello, "+user.getString("name")+"!");
		logout_btn = (Button)findViewById(R.id.logout_btn);
		logout_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				if(ParseFacebookUtils.getSession() != null){
					ParseFacebookUtils.getSession().close();
				}
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
				
			}
		});
	}
}
