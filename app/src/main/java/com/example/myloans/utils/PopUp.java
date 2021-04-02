package com.example.myloans.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;

import com.example.myloans.LoginActivity;
import com.example.myloans.MainActivity;
import com.example.myloans.R;
import com.example.myloans.data.MyAdsPersistence;

import static com.example.myloans.utils.SharedFunctions.formatName;
import static com.example.myloans.utils.SharedFunctions.showBasic;
import static com.example.myloans.utils.SharedFunctions.showError;
import static com.example.myloans.utils.SharedFunctions.startIntentWithFlags;

/**
 * Created on Monday, 7/27/20
 * By yeghon.
 * Copyright 2020 yeghon.
 */
public class PopUp {


    public static void buildPopUp(View view, final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        ViewGroup viewGroup = (ViewGroup) view;
        View dialogView = LayoutInflater.from(context).inflate(R.layout.reset_pin_dialog, viewGroup, false);
        builder.setView(dialogView);

        final MyAdsPersistence persistence = new MyAdsPersistence(context);

        final EditText newPin = dialogView.findViewById(R.id.new_pin);
        final EditText confirmPin = dialogView.findViewById(R.id.confirm_new_pin);
        Button reset = dialogView.findViewById(R.id.reset_pin);

        newPin.setHint("Please enter new password");
        confirmPin.setHint("Confirm new password");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPin.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, new password cannot be empty");
                else if (confirmPin.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, confirm password cannot be empty!");
                else if (newPin.getText().toString().length() < 4 || newPin.getText().toString().length() > 4)
                    showError(context, "Invalid password length. Password should be 4 characters long");
                else if (confirmPin.getText().toString().length() < 4 || confirmPin.getText().toString().length() > 4)
                    showError(context, "Invalid password length. Password should be 4 characters long");
                else if (!newPin.getText().toString().equalsIgnoreCase(confirmPin.getText().toString()))
                    showError(context, "Confirm password and new password need to match!");
                else {
                    persistence.updatePassword(1, newPin.getText().toString());
                    startIntentWithFlags(context, LoginActivity.class);
                }
            }
        });


        final AlertDialog alertDialog = builder.create();
//        if (!context.isFinishing()) {
        alertDialog.show();
//        alertDialog.setCancelable(false);
//        }
    }

    public static void popApplyLoan(View view, final Context context, final String fName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        ViewGroup viewGroup = (ViewGroup) view;
        View dialogView = LayoutInflater.from(context).inflate(R.layout.appply_loan_layout, viewGroup, false);
        builder.setView(dialogView);

        final MyAdsPersistence persistence = new MyAdsPersistence(context);

        final EditText lPurpose = dialogView.findViewById(R.id.l_purpose);
        final EditText lAmount = dialogView.findViewById(R.id.l_amount);
        final EditText lInstallemnts = dialogView.findViewById(R.id.l_installments);
        Button lEligibility = dialogView.findViewById(R.id.l_eligibility);
        Button lSubmit = dialogView.findViewById(R.id.l_submit);

        lEligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBasic(context, "Your loan eligibility will be shared via sms. Thank you!");
            }
        });

        lSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check for emptiness of other fields
                if (lPurpose.getText().toString().equalsIgnoreCase(""))
                    showBasic(context, "Specify purpose of loan");
                else if (lPurpose.getText().toString().length() > 100)
                    showBasic(context, "Purpose specified too long");
                else if (lAmount.getText().toString().equalsIgnoreCase(""))
                    showBasic(context, "Specify amount you wish to apply");
                else if (lAmount.getText().toString().length() > 5 || lAmount.getText().toString().length() < 3)
                    showBasic(context, "Invalid amount entered");
                else if (lInstallemnts.getText().toString().equalsIgnoreCase(""))
                    showBasic(context, "Specify time in months you wish to repay the loan");
                else if (lInstallemnts.getText().toString().length() > 2)
                    showBasic(context, "Invalid installment length!");
                else {
                    try {
                        persistence.applyLoan(1, lAmount.getText().toString(),
                                lInstallemnts.getText().toString(), lPurpose.getText().toString());

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setMessage("Hello, " + formatName(fName) + ", your loan application was successful. " +
                                "\n Amount: \t Ksh. " + lAmount.getText().toString() + ".00" +
                                "\n Installments: \t " + lInstallemnts.getText().toString() + " Months" +
                                "\n Purpose: \t " + lPurpose.getText().toString() + "" +
                                "\n Thank you for using our services!");
                        alertDialogBuilder.setPositiveButton("Apply!",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        startIntentWithFlags(context, MainActivity.class);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    } catch (Exception e) {
                        showBasic(context, "Sorry, your loan application could not be processed. Try again later or contact support for more assistance!");

                    }

                }
            }
        });


        final AlertDialog alertDialog = builder.create();
//        if (!context.isFinishing()) {
        alertDialog.show();
//        alertDialog.setCancelable(false);
//        }
    }
}
