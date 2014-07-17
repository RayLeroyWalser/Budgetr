package com.johnruffer.budgetr.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.johnruffer.budgetr.models.Budget;

import java.util.List;

/**
 * Created by john on 7/17/14.
 */
public class BudgetListItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private final List<String> budgetNames;

    public BudgetListItemSelectedListener( List<String> budgetNames ) {
        this.budgetNames = budgetNames;
    }

    @Override
    public void onItemSelected( AdapterView<?> adapterView, View view, int position, long id ) {
        this.budgetNames.add(
                ( (Budget) adapterView.getItemAtPosition( position ) ).getName() );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }
}
