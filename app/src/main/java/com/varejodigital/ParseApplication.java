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
      Parse.initialize(this, "G2lw9bOOI3T4SI42ULhUb4fcHDKISDbOJIRnZbRU", "FF4GFPPeBdpnvzt7eiNlqtEr1HIgGwazX4StO1Al");

      // Save the current Installation to Parse.
      ParseInstallation.getCurrentInstallation().saveInBackground();

  }

}
