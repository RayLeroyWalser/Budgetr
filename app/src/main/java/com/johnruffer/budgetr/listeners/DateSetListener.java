package com.johnruffer.budgetr.listeners;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by John on 7/14/2014.
 */
public class DateSetListener implements DatePickerDialog.OnDateSetListener {
    private final Date date;
    private final TextView viewer;

    public DateSetListener( Date date, TextView dateViewer ) {
        this.date = date;
        this.viewer = dateViewer;
    }

    @Override
    public void onDateSet( DatePicker datePicker, int year, int monthOfYear, int dayOfMonth ) {
        Calendar cal = new GregorianCalendar( year, monthOfYear, dayOfMonth );
        this.date.setTime(cal.getTime().getTime());

        viewer.setText( this.date.toString() );
    }
}
