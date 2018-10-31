package com.demo.simplecook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.demo.simplecook.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // TODO - Initialize DB and Retrofit layers here
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
