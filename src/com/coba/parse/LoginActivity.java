package com.coba.parse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity implements OnClickListener{
	private EditText login_username, login_password;
	private Button btn_login;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login_username = (EditText)findViewById(R.id.login_username);
		login_password = (EditText)findViewById(R.id.login_password);
		btn_login = (Button)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v == btn_login) {
			if(login_username.getText().toString().trim().length()>0 && login_password.getText().toString().trim().length()>0){
				ParseUser.logInInBackground(login_username.getText().toString(), login_password.getText().toString(), new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	// Hooray! The user is logged in.
					    	Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
					    	Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);        
				            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				            startActivity(intent);
				            finish();
					    } else {
					    	// Sigin failed. Look at the ParseException to see what happened.
					    	Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
					    	Log.d("LoginActivity:", e.toString());
					    }
					  }
					});
				dismissProgressBar();
			}
		}
		
	}
	
	public void showProgressBar(String msg){
		   progressDialog = ProgressDialog.show(this, "", msg, true);
		}

	public void dismissProgressBar(){
	   if(progressDialog != null && progressDialog.isShowing())
	         progressDialog.dismiss();
	}
}
