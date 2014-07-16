package com.johnruffer.budgetr.actionbar;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by John on 7/15/2014.
 */
public class BudgetListActionModeCallback implements ActionMode.Callback {
    @Override
    public boolean onCreateActionMode( ActionMode actionMode, Menu menu ) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode( ActionMode actionMode, Menu menu ) {
        return false;
    }

    @Override
    public boolean onActionItemClicked( ActionMode actionMode, MenuItem menuItem ) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
