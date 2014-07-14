package com.johnruffer.budgetr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by John on 7/13/2014.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "budgetrdb";
    public static final String BUDGETS_TABLE = "budgets";
    public static final String EXPENSES_TABLE = "expenses";
    private int dbVersion;

    public DbOpenHelper( Context context ) {
        super( context, DB_NAME, null, 1 );
        this.dbVersion = 1;
    }

    @Override
    public void onCreate( SQLiteDatabase sqLiteDatabase ) {
        sqLiteDatabase.execSQL( "CREATE TABLE " + BUDGETS_TABLE +
                " (name text PRIMARY KEY ON CONFLICT FAIL," +
                " startamount double, currentamount double," +
                " startdate int, enddate int);" );
        sqLiteDatabase.execSQL( "CREATE TABLE " + EXPENSES_TABLE +
                " (id INTEGER PRIMARY KEY, expensename text," +
                " amount double, date int," +
                " budget text, FOREIGN KEY(budget) REFERENCES budgets(name));" );
        sqLiteDatabase.execSQL( "CREATE TRIGGER decrement_amt AFTER INSERT ON " +
                EXPENSES_TABLE + " BEGIN " +
                " UPDATE " + BUDGETS_TABLE + " SET currentamount=((SELECT currentamount" +
                " FROM " + BUDGETS_TABLE + " WHERE name=new.budget)-new.amount)" +
                " WHERE " + BUDGETS_TABLE + ".name=new.budget;" +
                " END;" );
        sqLiteDatabase.execSQL( "CREATE TRIGGER increment_amt AFTER DELETE ON " +
                EXPENSES_TABLE + " BEGIN " +
                " UPDATE " + BUDGETS_TABLE + " SET currentamount=((SELECT currentamount" +
                " FROM " + BUDGETS_TABLE + " WHERE name=old.budget)+old.amount)" +
                " WHERE " + BUDGETS_TABLE + ".name=old.budget;" +
                " END;" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion ) {
        Log.w(DbOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BUDGETS_TABLE );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXPENSES_TABLE);
        onCreate( sqLiteDatabase );
    }
}
