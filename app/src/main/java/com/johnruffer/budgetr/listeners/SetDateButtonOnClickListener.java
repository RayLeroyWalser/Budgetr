package com.johnruffer.budgetr.listeners;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by John on 7/14/2014.
 */
public class SetDateButtonOnClickListener implements View.OnClickListener {
    private final Date date;
    private final TextView dateViewer;

    public SetDateButtonOnClickListener( Date date, TextView dateViewer ) {
        this.date = date;
        this.dateViewer = dateViewer;
    }

    @Override
    public void onClick(View view) {
        Calendar cal = new GregorianCalendar();
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(),
                new DateSetListener( this.date, this.dateViewer ), cal.get( Calendar.YEAR ),
                cal.get( Calendar.MONTH ), cal.get( Calendar.DAY_OF_MONTH ) );
        dialog.show();
    }
}
