package com.yeghon.myads;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yeghon.myads.data.MyAdsPersistence;
import com.yeghon.myads.utils.PopUp;

import static com.yeghon.myads.utils.SharedFunctions.*;

/**
 * Created on Tuesday, 7/21/20
 * By yeghon.
 * Copyright 2020 yeghon.
 */
public class LoginActivity extends AppCompatActivity {
    private Context context;
    private MyAdsPersistence persistence;
    private EditText custPhone, custPass;
    private TextView welcome;
    private Button loginButton;

//    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        context = this;
        persistence = new MyAdsPersistence(context);

        custPhone = findViewById(R.id.user_phone);
        custPass = findViewById(R.id.user_pass);
        welcome = findViewById(R.id.welcome_banner);
        loginButton = findViewById(R.id.login_button);

        Cursor userData = persistence.getUserData(1);
        while (userData.moveToNext()) {
            int userPassId = userData.getColumnIndex(persistence.PASSWORD);
            int userFname = userData.getColumnIndex(persistence.FNAME);
            int lApplied = userData.getColumnIndex(persistence.LOANAMOUNT);

            if (null == userData.getString(userPassId) || userData.getString(userPassId).equalsIgnoreCase(""))
                setNewPassword(context, custPhone, custPass);
            else
                loginUser(context, custPhone, custPass, userData.getString(userFname), userData.getString(userPassId), userData.getString(lApplied));
        }

//        b1 = findViewById(R.id.quick_log);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                showDialog1(LoginActivity.this, "Testing!");
//            }
//        });
    }

    private void loginUser(final Context context, EditText custPhone, final EditText custPass, final String fName, final String userPassword, final String previousAmount) {
        welcome.setText(String.format("Welcome %s, \n\n Please enter your secure M-Banking Pin", formatName(fName)));
        custPhone.setVisibility(View.GONE);
        changeInputType(custPass, "password");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custPass.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, password can not be empty!");
                else if (custPass.getText().toString().length() < 4 || custPass.getText().toString().length() > 4)
                    showError(context, "Sorry, invalid password length!");
                else if (!custPass.getText().toString().equalsIgnoreCase(userPassword))
                    showError(context, "Sorry, password mismatch. Try again");
                else if (custPass.getText().toString().equalsIgnoreCase(userPassword)) {
                    Intent main = new Intent(context, MainActivity.class);
                    main.putExtra("F_NAME", fName);
                    main.putExtra("P_AMOUNT", previousAmount);
                    main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(main);
                }


            }
        });


    }

    private void setNewPassword(final Context context, final EditText custPhone, final EditText custPass) {
        changeInputType(custPhone, "password");
        changeInputType(custPass, "password");

        custPhone.setHint("Please enter new password");
        custPass.setHint("Confirm new password");
        welcome.setText(R.string.m_pin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custPhone.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, new password cannot be empty");
                else if (custPass.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, confirm password cannot be empty!");
                else if (custPhone.getText().toString().length() < 4 || custPhone.getText().toString().length() > 4)
                    showError(context, "Invalid password length. Password should be 4 characters long");
                else if (custPass.getText().toString().length() < 4 || custPass.getText().toString().length() > 4)
                    showError(context, "Invalid password length. Password should be 4 characters long");
                else if (!custPhone.getText().toString().equalsIgnoreCase(custPass.getText().toString()))
                    showError(context, "Confirm password and new password need to match!");
                else {
                    persistence.updatePassword(1, custPhone.getText().toString());
                    startIntentWithFlags(context, LoginActivity.class);
                }
            }
        });


    }

    /*
    public void signUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    public void goToDash(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }*/

    public void resetPassword(View view) {
        PopUp.buildPopUp(findViewById(android.R.id.content), this);
    }

    public void goToTerms(View view) {
        showBasic(this, "Coming soon!");
    }
}