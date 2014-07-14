package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.johnruffer.budgetr.BudgetDetailsActivity;

/**
 * Created by John on 7/13/2014.
 */
public class BudgetClickedListener implements AdapterView.OnItemClickListener {
    private final Activity activity;

    public BudgetClickedListener( Activity activity ) {
        this.activity = activity;
    }

    @Override
    public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
        TextView budgetView = (TextView) view;
        String budget = budgetView.getText().toString();
        Intent intent = new Intent( view.getContext(), BudgetDetailsActivity.class );
        intent.putExtra( "budget", budget );
        this.activity.startActivity( intent );
    }
}
