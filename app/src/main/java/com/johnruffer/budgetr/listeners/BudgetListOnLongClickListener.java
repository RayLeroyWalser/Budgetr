package com.johnruffer.budgetr.listeners;

import android.app.Activity;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;

import com.johnruffer.budgetr.actionbar.BudgetListActionModeCallback;

/**
 * Created by John on 7/15/2014.
 */
public class BudgetListOnLongClickListener implements AdapterView.OnItemLongClickListener {
    private final Activity activity;
    private ActionMode actionMode;

    public BudgetListOnLongClickListener( Activity activity ) {
        this.activity = activity;
        this.actionMode = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if( this.actionMode != null ) {
            return false;
        }

        this.actionMode = this.activity.startActionMode( new BudgetListActionModeCallback() );
        view.setSelected( true );
        return true;
    }
}
