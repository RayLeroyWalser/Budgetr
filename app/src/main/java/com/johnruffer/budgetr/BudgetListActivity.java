package com.johnruffer.budgetr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.johnruffer.budgetr.R;
import com.johnruffer.budgetr.data.BudgetsDataSource;
import com.johnruffer.budgetr.exceptions.DbErrorException;
import com.johnruffer.budgetr.listeners.BudgetClickedListener;
import com.johnruffer.budgetr.listeners.BudgetListItemSelectedListener;
import com.johnruffer.budgetr.listeners.BudgetListOnLongClickListener;
import com.johnruffer.budgetr.listeners.NewBudgetListener;
import com.johnruffer.budgetr.models.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_list);

        this.populateBudgetList();

        Button addBudgetButton = (Button) findViewById( R.id.addBudget );
        addBudgetButton.setOnClickListener( new NewBudgetListener( this ) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.budget_list, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        this.populateBudgetList();
    }

    private void populateBudgetList() {
        ListView budgetList = (ListView) findViewById( R.id.budgetList );
        budgetList.clearChoices();
        BudgetsDataSource budgetsDataSource = new BudgetsDataSource( this );
        List<Budget> budgets = null;
        try {
            budgetsDataSource.open();
            budgets = budgetsDataSource.getAllBudgets();
        } catch( DbErrorException e ) {
            return;
        } finally {
            budgetsDataSource.close();
        }
        ArrayAdapter<Budget> budgetViews =
                new ArrayAdapter<Budget>( this,
                        android.R.layout.simple_list_item_1, budgets );
        budgetList.setAdapter( budgetViews );
        budgetList.setOnItemClickListener( new BudgetClickedListener( this ) );

        List<String> budgetsToBeDeleted = new ArrayList<String>();
        budgetList.setOnLongClickListener(
                new BudgetListOnLongClickListener( this, budgetsToBeDeleted ) );
        budgetList.setOnItemSelectedListener(
                new BudgetListItemSelectedListener( budgetsToBeDeleted ) );
    }
}
