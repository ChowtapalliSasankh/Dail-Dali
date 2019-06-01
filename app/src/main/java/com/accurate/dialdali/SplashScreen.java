package com.accurate.dialdali;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.accurate.dialdali.utility.UtilClass;


public class SplashScreen extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent;
                if(UtilClass.getDataFromPref(getApplicationContext(),"IsUser_Registered").equals("Success"))
                {
                    mainIntent = new Intent(SplashScreen.this,HomeActivity.class);
                }
                else
                {
                    mainIntent = new Intent(SplashScreen.this,Register.class);
                }
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}