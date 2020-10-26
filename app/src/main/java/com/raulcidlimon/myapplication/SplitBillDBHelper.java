package com.raulcidlimon.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SplitBillDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "splitbill.db";
    public static final int DATABASE_VERSION = 1;

    public SplitBillDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SPLITBILL_TABLE = "CREATE TABLE "
                + SplitBillContract.BillEntry.TABLE_NAME + " ("
                + SplitBillContract.BillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SplitBillContract.BillEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + SplitBillContract.BillEntry.COLUMN_MONEY + " REAL NOT NULL, "
                + SplitBillContract.BillEntry.COLUMN_HUMAN + " TEXT NOT NULL, "
                + SplitBillContract.BillEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIME"
                + ");";

        final String SQL_CREATE_RESULT_TABLE = "CREATE TABLE "
                + SplitBillContract.BillEntry.TABLE_RESULT_NAME + " ("
                + SplitBillContract.BillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SplitBillContract.BillEntry.COLUMN_RESULT_MONEY + " REAL NOT NULL, "
                + SplitBillContract.BillEntry.COLUMN_RESULT_HUMAN + " TEXT NOT NULL, "
                + SplitBillContract.BillEntry.COLUMN_RESULT_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIME"
                + ");";

        db.execSQL(SQL_CREATE_SPLITBILL_TABLE);
        db.execSQL(SQL_CREATE_RESULT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SplitBillContract.BillEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SplitBillContract.BillEntry.TABLE_RESULT_NAME);
        onCreate(db);

    }
}
