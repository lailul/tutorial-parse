package com.coba.parse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity{
	private EditText reg_username, reg_password, reg_name;
	private Button btn_reg;
	private ProgressDialog progressDialog;
	private ParseUser user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		reg_username = (EditText)findViewById(R.id.reg_username);
		reg_password = (EditText)findViewById(R.id.reg_password);
		reg_name = (EditText)findViewById(R.id.reg_name);
		btn_reg = (Button)findViewById(R.id.btn_reg);
		btn_reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showProgressBar("Signing Up...");
				user = new ParseUser();
				user.setUsername(reg_username.getText().toString());
				user.setPassword(reg_password.getText().toString());
				user.put("name", reg_name.getText().toString());
				user.signUpInBackground(new SignUpCallback() {
					
					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast.makeText(getApplicationContext(), "Successfully Signed Up", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
						}else{
							Toast.makeText(getApplicationContext(), "Failed to signed Up", Toast.LENGTH_SHORT).show();
							finish();
						}
						dismissProgressBar();
					}
				});
				
			}
		});
	}
	
	public void showProgressBar(String msg){
		   progressDialog = ProgressDialog.show(this, "", msg, true);
		}

	public void dismissProgressBar(){
	   if(progressDialog != null && progressDialog.isShowing())
	         progressDialog.dismiss();
	}
}
