package com.varejodigital;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;


public class ParseApplication extends Application {

  private static Context mContext;

  @Override
  public void onCreate() {
    super.onCreate();

    setAppContext(getApplicationContext());

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

  public static void setAppContext(Context context){
    mContext = context;
  }

  public static Context getAppContext(){
    return mContext;
  }


}
