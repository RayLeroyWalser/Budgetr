package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.johnruffer.budgetr.AddExpenseActivity;

/**
 * Created by John on 7/13/2014.
 */
public class NewExpenseListener implements View.OnClickListener {
    private final Activity activity;
    private final String budget;

    public NewExpenseListener( Activity activity, String budget ) {
        this.activity = activity;
        this.budget = budget;
    }
    @Override
    public void onClick(View view) {
        // TODO implement
        Intent intent = new Intent( this.activity, AddExpenseActivity.class );
        intent.putExtra( "budget", budget );

        this.activity.startActivityForResult( intent, 1 );
    }
}
