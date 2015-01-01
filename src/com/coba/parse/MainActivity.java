package com.coba.parse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;


public class MainActivity extends Activity implements OnClickListener{
	private Button register_btn, login_btn, facebook_btn, twitter_btn;
	private ProgressDialog progressDialog;
	private ParseUser _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register_btn = (Button)findViewById(R.id.register);
        register_btn.setOnClickListener(this);
        login_btn = (Button)findViewById(R.id.button1);
        login_btn.setOnClickListener(this);
        facebook_btn = (Button)findViewById(R.id.button2);
        facebook_btn.setOnClickListener(this);
        twitter_btn = (Button)findViewById(R.id.button3);
        twitter_btn.setOnClickListener(this);
        _user = ParseUser.getCurrentUser();
    }

    @Override
	public void onClick(View v) {
    	if(v == register_btn){
    		startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    	}else if(v == login_btn){
    		startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    	}else if(v == facebook_btn){
    		showProgressBar("Connecting to facebook...");
    		ParseFacebookUtils.logIn(this, 1, new LogInCallback() {
				
				@Override
				public void done(final ParseUser user, ParseException e) {
					_user = user;
					if (user == null) {
						Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
						Log.d("MyApp", e.toString());
						dismissProgressBar();
					} else if (user.isNew()) {
						dismissProgressBar();
						Log.d("MyApp", "User signed up and logged in through Facebook!");
						if(ParseFacebookUtils.getSession().isOpened()){
						      Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback()
		                          {
		                              @Override
		                              public void onCompleted(GraphUser us, Response response)
		                              {
		                                  if (us != null)
		                                  {
		                                      Toast toast = Toast.makeText(getApplicationContext(), "Thanks, " + us.getName() + ". You are now successfully logged in through Facebook!", Toast.LENGTH_LONG);
		                                      _user.put("name", us.getName());
		                                      _user.saveInBackground();
		                                      toast.setGravity(Gravity.TOP, 0, 20);
		                                      toast.show();
		                                      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
		                                      finish();
		                                  }
		                              }
		                          }).executeAsync();
						}
					}else{
						dismissProgressBar();
						Log.d("MyApp", "User logged in through Facebook!");
						Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback()
						{
							@Override
						    public void onCompleted(GraphUser us, Response response)
						    {
								if (us != null){
									Toast toast = Toast.makeText(getApplicationContext(), "Thanks, " + us.getName() + ". You are now successfully logged in through Facebook!", Toast.LENGTH_LONG);
						            toast.setGravity(Gravity.TOP, 0, 20);
						            toast.show();
						            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
						            finish();
						        }
							}
						}).executeAsync();
					}
					
				}
			});
    	}else{
    		showProgressBar("Connecting to twitter...");
			ParseTwitterUtils.logIn(this, new LogInCallback() {
				  @Override
				  public void done(ParseUser user, ParseException err) {
					  dismissProgressBar();
					  if (user == null) {
						  Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
					      Log.d("MyApp", err.toString());
					  } else if (user.isNew()) {
					      Log.d("MyApp", "User signed up and logged in through Twitter!");
					      user.put("name", ParseTwitterUtils.getTwitter().getScreenName());
					      user.saveInBackground();
					      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
					      finish();
					  } else {
					      Log.d("MyApp", "User logged in through Twitter!");
					      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
					      finish();
					  }
				  }
			});
    	}
		
	}
    
    public void showProgressBar(String msg){
		   progressDialog = ProgressDialog.show(this, "", msg, true);
		}

	public void dismissProgressBar(){
	   if(progressDialog != null && progressDialog.isShowing())
	         progressDialog.dismiss();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  if(requestCode==1){
		  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	  }
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	
}
