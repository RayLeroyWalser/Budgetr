package com.johnruffer.budgetr.listeners;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.johnruffer.budgetr.data.BudgetsDataSource;
import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.models.Budget;

import java.util.Date;

/**
 * Created by John on 7/13/2014.
 */
public class AddBudgetListener implements View.OnClickListener {
    private static final String TAG = "AddBudgetListener";
    private final EditText name;
    private final EditText startAmount;
    private final Date startDate;
    private final Date endDate;

    public AddBudgetListener( EditText name, EditText startAmount,
            Date startDate, Date endDate ) {
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
            Toast.makeText( view.getContext(), "Amount must be a positive number",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        if( this.endDate.getTime() <= this.startDate.getTime() ) {
            Toast.makeText( view.getContext(), "End date must be greater than start date",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        Budget budget = new Budget( budgetName, amount, this.startDate, this.endDate );

        Log.v( TAG, budget.toString() );

        BudgetsDataSource budgetsDataSource = new BudgetsDataSource( view.getContext() );
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
    }
}
