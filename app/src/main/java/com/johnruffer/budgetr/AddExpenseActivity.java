package com.johnruffer.budgetr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.johnruffer.budgetr.listeners.AddExpenseListener;


public class AddExpenseActivity extends Activity {
    private static final String TAG = "AddExpenseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        TextView balanceTextView = (TextView) findViewById( R.id.balance );
        EditText expenseNameEditText = (EditText) findViewById( R.id.expenseName );
        EditText expenseAmountEditText = (EditText) findViewById( R.id.expenseAmount );
        Button addExpenseButton = (Button) findViewById( R.id.addExpense );

        String budgetName = getIntent().getStringExtra( "budget" );

        addExpenseButton.setOnClickListener(
                new AddExpenseListener( this, expenseNameEditText,
                expenseAmountEditText, budgetName ) );
        balanceTextView.setText( "This will be your current balance" );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
