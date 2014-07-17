package com.johnruffer.budgetr.actionbar;

import android.app.Activity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.johnruffer.budgetr.R;
import com.johnruffer.budgetr.data.BudgetsDataSource;
import com.johnruffer.budgetr.exceptions.DbErrorException;

import java.util.List;

/**
 * Created by John on 7/15/2014.
 */
public class BudgetListActionModeCallback implements ActionMode.Callback {
    private static final String TAG = "BudgetListActionModeCallback";
    private final List<String> budgetsToBeDeleted;
    private final Activity activity;

    public BudgetListActionModeCallback( Activity activity, List<String> budgetsToBeDeleted ) {
        this.budgetsToBeDeleted = budgetsToBeDeleted;
        this.activity = activity;
    }

    @Override
    public boolean onCreateActionMode( ActionMode actionMode, Menu menu ) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate( R.menu.budget_list_edit, menu );
        return true;
    }

    @Override
    public boolean onPrepareActionMode( ActionMode actionMode, Menu menu ) {
        return false;
    }

    @Override
    public boolean onActionItemClicked( ActionMode actionMode, MenuItem menuItem ) {

        switch( menuItem.getItemId() ) {
            case R.id.deleteBudget:
                Log.d( TAG, String.format( "Deleted %d of %d budgets",
                        removeBudgets(), this.budgetsToBeDeleted.size() ) );
                actionMode.finish();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }

    private int removeBudgets() {
        BudgetsDataSource budgetsDataSource = new BudgetsDataSource( this.activity );
        int successCount = 0;
        budgetsDataSource.open();
        for( String i : this.budgetsToBeDeleted ) {
            try {
                budgetsDataSource.removeBudget(i);
                successCount++;
            } catch( DbErrorException e ) {
                Log.e( TAG, e.getMessage() );
            }
        }
        budgetsDataSource.close();

        return successCount;
    }
}
