package com.example.myloans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myloans.data.MyAdsPersistence;

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