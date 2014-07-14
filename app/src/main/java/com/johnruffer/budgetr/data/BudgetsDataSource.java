package com.johnruffer.budgetr.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.exceptions.NotFoundException;
import com.johnruffer.budgetr.models.Budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by John on 7/13/2014.
 */
public class BudgetsDataSource {
    private SQLiteDatabase source;
    private DbOpenHelper helper;

    public BudgetsDataSource( Context context ) {
        this.helper = new DbOpenHelper( context );
        this.source = null;
    }

    public void open() {
        if( null == this.source ) {
            this.source = this.helper.getWritableDatabase();
        }
    }

    public void close() {
        if( null != this.source ) {
            this.source.close();
            this.source = null;
        }
    }

    public List<Budget> getAllBudgets() throws DbErrorException {
        String [] columns = { "name", "startamount", "currentamount", "startdate", "enddate" };
        Cursor cursor = this.source.query( DbOpenHelper.BUDGETS_TABLE, columns,
                null, null, null, null, null, null );
        if( cursor.moveToFirst() ) {
            List<Budget> results = new ArrayList<Budget>();
            do {
                Budget budget = this.createBudgetFromCursor( cursor );
                results.add( budget );
            } while( cursor.moveToNext() );
            return results;
        } else {
            throw new DbErrorException( "Could not retrieve all budgets" );
        }
    }

    public Budget getBudgetByName( String name ) throws NotFoundException {
        // "SELECT name, amount, startdate, enddate FROM budgets WHERE name=?;";
        String [] columns = { "name", "startamount", "currentamount", "startdate", "enddate" };
        String [] selectionArgs = { name };
        Cursor cursor = this.source.query( DbOpenHelper.BUDGETS_TABLE, columns,
                "name LIKE ?", selectionArgs, null, null, null, null );
        if( cursor.moveToFirst() ) {
            return this.createBudgetFromCursor( cursor );
        } else {
            throw new NotFoundException( "Could not find budget for name: " + name );
        }
    }

    public void insertBudget( Budget budget ) throws DbErrorException {
        ContentValues values = new ContentValues();
        values.put( "name", budget.getName() );
        values.put( "startamount", budget.getStartAmount() );
        values.put( "currentamount", budget.getCurrentAmount() );
        values.put( "startdate", budget.getStartDate().getTime() );
        values.put( "enddate", budget.getEndDate().getTime() );
        try {
            if (this.source.insert(DbOpenHelper.BUDGETS_TABLE, null, values) < 0) {
                throw new DbErrorException(
                        "Something went wrong inserting the new budget: " + budget);
            }
        } catch( SQLiteConstraintException e ) {
            throw new DbErrorException( e.getMessage() );
        }
    }

    public void removeBudget( String budgetName ) throws DbErrorException {
        try {
            this.source.delete(DbOpenHelper.BUDGETS_TABLE,
                    "name=?", new String[]{budgetName});
        } catch( SQLiteException e ) {
            throw new DbErrorException( e.getMessage() );
        }
    }

    private Budget createBudgetFromCursor( Cursor cursor ) {
        String name = cursor.getString( cursor.getColumnIndex( "name" ) );
        Double startAmount = cursor.getDouble( cursor.getColumnIndex( "startamount" ) );
        Double currentAmount = cursor.getDouble( cursor.getColumnIndex( "currentamount" ) );
        Date startDate = new Date( cursor.getLong( cursor.getColumnIndex( "startdate" ) ) );
        Date endDate = new Date( cursor.getLong( cursor.getColumnIndex( "enddate" ) ) );

        return new Budget( name, startAmount, currentAmount, startDate, endDate );
    }
}
