package com.johnruffer.budgetr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.johnruffer.budgetr.listeners.AddBudgetListener;
import com.johnruffer.budgetr.listeners.SetDateButtonOnClickListener;

import java.util.Date;

public class AddBudgetActivity extends Activity {
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        Button addButton = (Button) findViewById( R.id.addButton );
        EditText budgetNameEditText = (EditText) findViewById( R.id.budgetName );
        EditText startAmtEditText = (EditText) findViewById( R.id.budgetStartAmt );
        Button startDateButton = (Button) findViewById( R.id.startDateButton  );
        Button endDateButton = (Button) findViewById( R.id.endDateButton );
        TextView startDateViewer = (TextView) findViewById( R.id.startDateView );
        TextView endDateViewer = (TextView) findViewById( R.id.endDateView );

        this.startDate = new Date();
        this.endDate = new Date();

        startDateButton.setOnClickListener(
                new SetDateButtonOnClickListener( this.startDate, startDateViewer ) );
        endDateButton.setOnClickListener(
                new SetDateButtonOnClickListener( this.endDate, endDateViewer ) );
        addButton.setOnClickListener( new AddBudgetListener( budgetNameEditText,
                startAmtEditText, this.startDate, this.endDate ) );
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
