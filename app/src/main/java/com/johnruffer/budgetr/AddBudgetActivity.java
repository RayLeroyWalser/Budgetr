package com.johnruffer.budgetr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.johnruffer.budgetr.R;
import com.johnruffer.budgetr.listeners.AddBudgetListener;

public class AddBudgetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        Button addButton = (Button) findViewById( R.id.addButton );
        EditText budgetNameEditText = (EditText) findViewById( R.id.budgetName );
        EditText startAmtEditText = (EditText) findViewById( R.id.budgetStartAmt );
        DatePicker startDatePicker = (DatePicker) findViewById( R.id.startDate );
        DatePicker endDatePicker = (DatePicker) findViewById( R.id.endDate );

        addButton.setOnClickListener( new AddBudgetListener( this,
                budgetNameEditText, startAmtEditText, startDatePicker, endDatePicker ) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_budget, menu);
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
