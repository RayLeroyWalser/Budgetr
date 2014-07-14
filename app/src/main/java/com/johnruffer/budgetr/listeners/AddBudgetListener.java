package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.johnruffer.budgetr.data.BudgetsDataSource;
import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.models.Budget;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by John on 7/13/2014.
 */
public class AddBudgetListener implements View.OnClickListener {
    private static final String TAG = "AddBudgetListener";
    private final Activity activity;
    private final EditText name;
    private final EditText startAmount;
    private final DatePicker startDate;
    private final DatePicker endDate;

    public AddBudgetListener( Activity activity, EditText name, EditText startAmount,
            DatePicker startDate, DatePicker endDate ) {
        this.activity = activity;
        this.name = name;
        this.startAmount = startAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Override
    public void onClick( View view ) {
        String budgetName = name.getText().toString();
        double amount = 0.0;
        try {
            amount = Double.parseDouble(startAmount.getText().toString());
        } catch( NumberFormatException e ) {
            Toast.makeText( view.getContext(), "Amount is not a number",
                    Toast.LENGTH_SHORT ).show();
        }

        if( amount < 0 ) {
            Toast.makeText( this.activity, "Amount must be a positive number",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        Calendar cal = new GregorianCalendar();
        cal.set( startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth() );
        Date start = cal.getTime();

        cal.set( endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth() );
        Date end = cal.getTime();

        if( end.getTime() <= start.getTime() ) {
            Toast.makeText( view.getContext(), "End date must be greater than start date",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        Budget budget = new Budget( budgetName, amount, start, end );

        Log.v( TAG, budget.toString() );

        BudgetsDataSource budgetsDataSource = new BudgetsDataSource( this.activity);
        try {
            budgetsDataSource.open();
            budgetsDataSource.insertBudget(budget);
        } catch( DbErrorException e ) {
            Log.e( TAG, e.getMessage() );
            Toast.makeText( view.getContext(), "Failed to add budget",
                    Toast.LENGTH_SHORT ).show();
        } finally {
            budgetsDataSource.close();
        }
        Toast.makeText( view.getContext(), "Successfully added budget",
                Toast.LENGTH_SHORT ).show();
        this.activity.finishActivity( 1 );
    }
}
