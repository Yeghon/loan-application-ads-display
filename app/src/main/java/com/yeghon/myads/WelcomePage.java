package com.yeghon.myads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yeghon.myads.data.MyAdsPersistence;

/**
 * Created on Tuesday, 7/21/20
 * By yeghon.
 * Copyright 2020 yeghon.
 */
public class WelcomePage extends AppCompatActivity {

    MyAdsPersistence persistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        persistence = new MyAdsPersistence(WelcomePage.this);

    }

    public void proceed(View view) {
        if (persistence.numberOfRowsUser() > 0)
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, SignUp.class));
    }
}