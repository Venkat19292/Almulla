package com.exchange.almulla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ckeckUserAlreadyLogin();
    }


    private void ckeckUserAlreadyLogin() {

        final Boolean isFirstTime = (Boolean) CommonMethodes.getPreferences(SplashScreen.this, CommonEnvironmentValues.KEY_REMEMBER_ME, CommonEnvironmentValues.PREFTYPE_BOOLEAN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isFirstTime != null) {
                    //if false then
                    if (!isFirstTime) {
                        moveToLogin();
                    } else {
                        gotoHomeActivity();
                    }
                } else {
                    moveToLogin();
                }


            }
        }, 1000);
    }

    private void gotoHomeActivity() {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void moveToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
