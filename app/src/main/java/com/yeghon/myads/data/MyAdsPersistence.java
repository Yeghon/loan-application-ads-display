package com.yeghon.myads.data;

/**
 * Created on Wednesday, 8/12/20
 * By yeghon.
 * Copyright 2020 yeghon.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yeghon.myads.model.NewCustomer;

import java.util.ArrayList;
import java.util.Map;

public class MyAdsPersistence extends SQLiteOpenHelper {

    public static final String DBNAME = "YeghonFarm.db";
    public static final String USERTABLE = "customer";
    public static final String USERID = "id";
    public static final String FNAME = "f_name";
    public static final String LNAME = "l_name";
    public static final String ONAME = "o_name";
    public static final String PHONENUMBER = "phone_number";
    public static final String NATIONALID = "national_id";
    public static final String DOB = "date_of_birth";
    public static final String PASSWORD = "password";
    public static final String LOANPURPOSE = "loan_purpose";
    public static final String LOANINSTALLMENTS = "loan_installments";
    public static final String LOANAMOUNT = "loan_amount";


    public MyAdsPersistence(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USERTABLE + "(" + USERID + " integer primary key, " + FNAME + " text," + LNAME + " text," + ONAME +
                " text, " + PHONENUMBER + " text, " + NATIONALID + " text, " +  LOANPURPOSE + " text, " + LOANINSTALLMENTS + " text, " + LOANAMOUNT + " text, " + DOB + " text , " + PASSWORD + " text)"
        );
//        db.execSQL("create table "
//                + OPERATION_DB_TABLE + " ("
//                + OPERATION_ID + " integer primary key autoincrement, "
//                + OPERATION_DESCRIPTION + " text, "
//                + OPERATION_COST + " text, "
//                + OPERATION_DATE + " text, "
//                + OPERATOR_CONTACT + " text, "
//                + OPERATION_COMMENTS + " text, "
//                + YEGHONFARM_ID + " integer, FOREIGN KEY ( " + YEGHONFARM_ID + ") REFERENCES " + YEGHONFARM_DB_TABLE + " (" + YEGHONFARM_COLUMN_ID + "))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERTABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + OPERATION_DB_TABLE);
        onCreate(db);
    }

    public boolean createNewUser(NewCustomer newCustomer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("f_name", newCustomer.getfName());
        contentValues.put("l_name", newCustomer.getlName());
        contentValues.put("o_name", newCustomer.getoName());
        contentValues.put("phone_number", newCustomer.getPhoneNumber());
        contentValues.put("national_id", newCustomer.getNationalId());
        contentValues.put("date_of_birth", newCustomer.getdOb());
        db.insert(USERTABLE, null, contentValues);
        return true;
    }



    public Cursor getUserData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + USERTABLE + " where id=" + id + "", null);
        return res;
    }

    public int numberOfRowsUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERTABLE);
        return numRows;
    }

    public int updatePassword(Integer id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        return db.update(USERTABLE, contentValues, USERID + " = " + id, null);
    }

    public int applyLoan(Integer id, String amount, String installemnts, String purpose) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loan_amount", amount);
        contentValues.put("loan_purpose", purpose);
        contentValues.put("loan_installments", installemnts);
        return db.update(USERTABLE, contentValues, USERID + " = " + id, null);
    }

//    public ArrayList<String> getAllProductsFarm() {
//        ArrayList<String> array_list = new ArrayList<>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from " + YEGHONFARM_DB_TABLE, null);
//        res.moveToFirst();
//
//        while (res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex(YEGHONFARM_PROUCT_NAME)));
//            res.moveToNext();
//        }
//        return array_list;
//    }

//    public ArrayList<String> getAllProductsDescrption() {
//        ArrayList<String> array_list = new ArrayList<>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from " + OPERATION_DB_TABLE, null);
//        res.moveToFirst();
//
//        while (res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex(OPERATION_DESCRIPTION)));
//            res.moveToNext();
//        }
//        return array_list;
//    }
//
//    public void dropRecord(String tableName, String tableId, Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(YEGHONFARM_DB_TABLE, YEGHONFARM_COLUMN_ID + "=?", new String[]{String.valueOf(id)});
//    }
}