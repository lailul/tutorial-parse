package com.coba.parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

public class App extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "Your Parse ID", "Your Parse Key");
		ParseFacebookUtils.initialize("Your Facebook ID");
		ParseTwitterUtils.initialize("Your Twitter Consumer Key", "Your Twitter Consumer Secret");
		ParseUser.enableAutomaticUser();
	}
}
