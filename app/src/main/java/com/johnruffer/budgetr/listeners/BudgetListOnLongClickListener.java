package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.view.ActionMode;
import android.view.View;

import com.johnruffer.budgetr.actionbar.BudgetListActionModeCallback;

import java.util.List;

/**
 * Created by John on 7/15/2014.
 */
public class BudgetListOnLongClickListener implements View.OnLongClickListener {
    private final Activity activity;
    private final List<String> budgetsToBeDeleted;
    private ActionMode actionMode;

    public BudgetListOnLongClickListener( Activity activity, List<String> budgetsToBeDeleted ) {
        this.activity = activity;
        this.budgetsToBeDeleted = budgetsToBeDeleted;
        this.actionMode = null;
    }

    @Override
    public boolean onLongClick(View view) {
        if( this.actionMode != null ) {
            return false;
        }

        this.actionMode = this.activity.startActionMode(
                new BudgetListActionModeCallback( this.activity, this.budgetsToBeDeleted ) );
        view.setSelected( true );
        return true;
    }
}
