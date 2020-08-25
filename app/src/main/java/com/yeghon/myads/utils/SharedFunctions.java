package com.yeghon.myads.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.yeghon.myads.LoginActivity;
import com.yeghon.myads.MainActivity;
import com.yeghon.myads.model.NewCustomer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created on Wednesday, 8/5/20
 * By yeghon.
 * Copyright 2020 yeghon.
 */
public class SharedFunctions {

//    public static void showBasic(Context context, String message) {
//        new SweetAlertDialog(context)
//                .setTitleText(message)
//                .show();
//    }

//    public static void showError(Context context, String message) {
////        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//        new SweetAlertDialog(context)
//                .setTitleText("Sorry...")
//                .setContentText(message)
//                .show();
//    }

    public static void showError(final Context context, String message) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Ok!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        startIntentWithFlags(context, MainActivity.class);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public static void showSuccess(Context context, String message) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                .setTitleText("Good job!")
                .setContentText(message)
                .show();
    }

    public static void showBasic(final Context context, String message) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Ok!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        startIntentWithFlags(context, MainActivity.class);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public static void successSignUp(final Context context, NewCustomer customer){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Hello " + formatName(customer.getfName()) + ", your registration was successful. \n Please login to setup your secure password!");
        alertDialogBuilder.setPositiveButton("Login!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startIntentWithFlags(context, LoginActivity.class);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public static void startIntentWithFlags(Context context, Class<?> t) {
        Intent newIntent = new Intent(context, t);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(newIntent);
    }

    public static void openBrowser(Context context, String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void shareSocial(Context context, String url){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Get instant access to quick loans. Click <applink>");
        sendIntent.setType("text/plain");
        sendIntent.setPackage(url);
        try {
            context.startActivity(sendIntent);
        } catch (Exception e){
            openBrowser(context, "http://www." + url.split("\\.")[1] + ".com");
        }
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static void saveToPreferences(Context context, String preferenceName, int preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(preferenceName, preferenceValue);
        editor.apply();
    }

    public static void saveToPreferences(Context context, String preferenceName, boolean preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getBoolean(preferenceName, defaultValue);
    }

    public static boolean checkNumeric(String msisdn) {
        if (msisdn.matches("-?\\d+(\\.\\d+)?")) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatPhone(String msisdn) {
        String start_char = String.valueOf(msisdn.charAt(0));
        String phoneNumber;

        int msisdn_length = msisdn.length();
        if (start_char.equals("+") && msisdn_length == 13) {
            phoneNumber = msisdn.substring(1);
        } else if (start_char.equals("2") && msisdn_length == 12) {
            phoneNumber = msisdn;
        } else if (start_char.equals("0") && msisdn_length == 10) {
            msisdn = "+254" + msisdn;
            phoneNumber = msisdn.replace("+2540", "254");
        } else if (start_char.equals("7") && msisdn_length == 9) {
            phoneNumber = "254" + msisdn;
        } else {
            phoneNumber = "0";
        }

        return phoneNumber;
    }

    public static String formatName(String name) {
        String uName;
        String[] splitUname = name.split("\\s+");
        // get the first name
        uName = splitUname[0].toLowerCase();
        // format the name to uppercase only the first letter of the name
        String fCharacter = String.valueOf(uName.charAt(0)).toUpperCase();
        return fCharacter + "" + uName.substring(1);
    }

    public static void changeInputType(EditText editText, String type) {
        switch (type) {
            case "numeric":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                break;
            case "password":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }
    }

    public static void promptLogout(final Context context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Log out?");
        alertDialogBuilder.setMessage("Sure to log out?");
        alertDialogBuilder.setPositiveButton("Yes!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        logout(context);
                    }
                });

        alertDialogBuilder.setNegativeButton("No!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        displayDialog(context, "Mail is coming soon!");
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public static void logout(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
