package com.johnruffer.budgetr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.johnruffer.budgetr.data.BudgetsDataSource;
import com.johnruffer.budgetr.data.ExpensesDataSource;
import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.exceptions.NotFoundException;
import com.johnruffer.budgetr.listeners.NewExpenseListener;
import com.johnruffer.budgetr.models.Budget;
import com.johnruffer.budgetr.models.Expense;

import java.util.List;

public class BudgetDetailsActivity extends Activity {
    private static final String TAG = "BudgetDetailsActivity";
    private String budgetName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_details);
        // Get and show budget details
        // Get and show all expenses for the budget
        // Set up on click listener for adding an expense

        Intent intent = getIntent();
        this.budgetName = intent.getStringExtra( "budget" );
        this.setBudgetDetails( budgetName );
        this.populateExpensesList( budgetName );
        Button addExpenseButton = (Button) findViewById( R.id.addExpenseButton );
        addExpenseButton.setOnClickListener( new NewExpenseListener( this, budgetName ) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.budget_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setBudgetDetails( this.budgetName );
        this.populateExpensesList( this.budgetName );
    }

    private void setBudgetDetails( String name ) {
        BudgetsDataSource budgetsDataSource = new BudgetsDataSource( this );
        Budget budget;
        try {
            budgetsDataSource.open();
            budget = budgetsDataSource.getBudgetByName(name);
        } catch( NotFoundException e ) {
            Log.e( TAG, e.getMessage() );
            Toast.makeText( this, "Unable to retrieve budget details",
                    Toast.LENGTH_SHORT ).show();
            return;
        } finally {
            budgetsDataSource.close();
        }

        TextView budgetNameTextView = (TextView) findViewById( R.id.budgetName );
        TextView budgetCurrentAmtTextView = (TextView) findViewById( R.id.currentBudgetAmt );
        TextView budgetStartingAmtTextView = (TextView) findViewById( R.id.startingBudgetAmt );

        budgetNameTextView.setText( budget.getName() );
        if( budget.getCurrentAmount() < 0 ) {
            budgetCurrentAmtTextView.setTextColor( Color.RED );
        } else {
            budgetCurrentAmtTextView.setTextColor( Color.BLACK );
        }
        budgetCurrentAmtTextView.setText( String.format( "%.2f", budget.getCurrentAmount() ) );//Double.toString( budget.getCurrentAmount() ) );
        budgetStartingAmtTextView.setText( String.format( "%.2f", budget.getStartAmount() ) );//Double.toString( budget.getStartAmount() ) );
    }

    private void populateExpensesList( String budgetName ) {
        ListView expenseList = (ListView) findViewById( R.id.expensesList );
        List<Expense> expenses;
        ExpensesDataSource expensesDataSource = new ExpensesDataSource( this );
        try {
            expensesDataSource.open();
            expenses = expensesDataSource.getExpensesForBudget( budgetName );
        } catch( DbErrorException e ) {
            Log.e( TAG, e.getMessage() );
            Toast.makeText( this, "Could not retrieve expenses for budget",
                    Toast.LENGTH_SHORT ).show();
            return;
        } finally {
            expensesDataSource.close();
        }

        ArrayAdapter<Expense> expenseViews =
                new ArrayAdapter<Expense>( this,
                        android.R.layout.simple_list_item_1, expenses );
        expenseList.setAdapter( expenseViews );
    }
}
