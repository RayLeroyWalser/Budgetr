package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.johnruffer.budgetr.data.ExpensesDataSource;
import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.models.Expense;

import java.util.Date;

/**
 * Created by John on 7/12/2014.
 */
public class AddExpenseListener implements OnClickListener {
    private static final String TAG = "AddExpenseListener";
    private final Activity activity;
    private final EditText name;
    private final EditText amount;
    private final String budgetName;
    private final Date expenseDate;

    public AddExpenseListener( Activity activity, EditText name,
            EditText amount, String budgetName, Date expenseDate ) {
        this.activity = activity;
        this.name = name;
        this.amount = amount;
        this.budgetName = budgetName;
        this.expenseDate = expenseDate;
    }
    public void onClick( View view ) {
        String expenseName = this.name.getText().toString();
        double amount;
        try {
            amount = Double.parseDouble( this.amount.getText().toString() );
        } catch( NumberFormatException e ) {
            Log.e( TAG, e.getMessage() );
            Toast.makeText( view.getContext(), "Amount is not a valid number",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        Expense expense = new Expense( this.budgetName,
                expenseName, amount, this.expenseDate );

        ExpensesDataSource expensesDataSource = new ExpensesDataSource( view.getContext() );
        try {
            expensesDataSource.open();
            expensesDataSource.applyExpense( expense );
        } catch( DbErrorException e ) {
            Log.e( TAG, e.getMessage() );
            Toast.makeText( view.getContext(), "Unable to apply expense",
                    Toast.LENGTH_SHORT ).show();
            return;
        } finally {
            expensesDataSource.close();
        }

        Toast.makeText( view.getContext(), "Successfully applied expense",
                Toast.LENGTH_SHORT ).show();

        this.activity.finishActivity( 1 );
    }
}
