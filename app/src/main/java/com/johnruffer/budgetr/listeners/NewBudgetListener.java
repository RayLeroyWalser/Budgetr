package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.johnruffer.budgetr.AddBudgetActivity;

/**
 * Created by John on 7/13/2014.
 */
public class NewBudgetListener implements View.OnClickListener {
    private final Activity activity;

    public NewBudgetListener( Activity activity ) {
        this.activity = activity;
    }

    @Override
    public void onClick( View view ) {
        Intent intent = new Intent( this.activity, AddBudgetActivity.class );
        this.activity.startActivityForResult( intent, 1 );
    }
}
