package com.varejodigital;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;


public class ParseApplication extends Application {


  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
//    Parse.enableLocalDatastore(this);

//    ParseObject.registerSubclass(Tatto.class);
//    ParseObject.registerSubclass(Message.class);

    // Add your initialization code here
    Parse.initialize(this, "BRngIIZDSL6AYVOHADLC2ncFbaZYCkNx5zR86CvB", "MDwf4nJgCYKivOxHFsmE3FFzQgTXIOKyHD3cgUL7");


      // Save the current Installation to Parse.
      ParseInstallation.getCurrentInstallation().saveInBackground();

  }

}
