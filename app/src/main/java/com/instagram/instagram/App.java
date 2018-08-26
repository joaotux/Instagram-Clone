package com.instagram.instagram;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("KBPdsVsTp1FPi12hJcjs0D1obqZTUXYL8ycUphzB")
                // if desired
                .clientKey("EcS2c9FkhpIBreow75rFp61FOiAU8yVEPylbzUy2")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
