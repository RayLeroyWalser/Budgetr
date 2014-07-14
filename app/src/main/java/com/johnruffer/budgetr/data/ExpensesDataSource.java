package com.johnruffer.budgetr.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.exceptions.NotFoundException;
import com.johnruffer.budgetr.models.Budget;
import com.johnruffer.budgetr.models.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by John on 7/13/2014.
 */
public class ExpensesDataSource {
    private SQLiteDatabase source;
    private DbOpenHelper helper;

    public ExpensesDataSource( Context context ) {
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

    public List<Expense> getAllExpenses() {
        return new ArrayList<Expense>();
    }

    public List<Expense> getExpensesForBudget( String budget ) throws DbErrorException {
        // "SELECT amount, startdate, enddate FROM expenses WHERE budget=?;";
        String [] columns = { "id", "budget", "expensename", "amount", "date" };
        String [] selectionArgs = { budget };
        Cursor cursor = this.source.query( DbOpenHelper.EXPENSES_TABLE, columns,
                "budget LIKE ?", selectionArgs, null, null, null, null );
        if( cursor.moveToFirst() ) {
            List<Expense> results = new ArrayList<Expense>();
            do {
                results.add( this.createExpenseFromCursor( cursor ) );
            } while( cursor.moveToNext() );
            return results;
        } else {
            throw new DbErrorException(
                    "Something went wrong when finding expenses for name: " + budget );
        }
    }

    public void removeExpense( long id ) throws DbErrorException {
        // TODO implement
    }

    public void applyExpense( Expense expense ) throws DbErrorException {
        ContentValues values = new ContentValues();
        values.put( "expensename", expense.getName() );
        values.put( "budget", expense.getBudgetName() );
        values.put( "amount", expense.getAmount() );
        values.put( "date", expense.getDate().getTime() );

        try {
            if (this.source.insert(DbOpenHelper.EXPENSES_TABLE, null, values) < 0) {
                throw new DbErrorException(
                        "Something went wrong inserting the new expense: " + expense );
            }
        } catch( SQLiteConstraintException e ) {
            throw new DbErrorException( e.getMessage() );
        }
    }

    private Expense createExpenseFromCursor( Cursor cursor ) {
        long id = cursor.getLong( cursor.getColumnIndex( "id" ) );
        String budget = cursor.getString( cursor.getColumnIndex( "budget" ) );
        String name = cursor.getString( cursor.getColumnIndex( "expensename" ) );
        Double amount = cursor.getDouble( cursor.getColumnIndex( "amount" ) );
        Date date = new Date( cursor.getLong( cursor.getColumnIndex( "date" ) ) );

        return new Expense( id, budget, name, amount, date );
    }
}
