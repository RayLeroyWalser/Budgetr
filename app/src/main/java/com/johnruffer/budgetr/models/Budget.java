package com.johnruffer.budgetr.models;

import java.util.Date;

/**
 * Created by John on 7/13/2014.
 */
public class Budget {
    private final String name;
    private final double startAmount;
    private final double currentAmount;
    private final Date startDate;
    private final Date endDate;

    public Budget( String name, double startAmount, Date startDate, Date endDate) {
        this( name, startAmount, startAmount, startDate, endDate );
    }

    public Budget( String name, double startAmount, double currentAmount,
                   Date startDate, Date endDate) {
        this.name = name;
        this.startAmount = startAmount;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return this.name;
    }

    public double getStartAmount() {
        return this.startAmount;
    }

    public double getCurrentAmount() {
        return this.currentAmount;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        return this.name;
//        return "Budget: " + this.name +
//                " Starting amount: " + this.startAmount +
//                " Current amount: " + this.currentAmount;// +
                //" Start date: " + this.startDate.toString();
    }
}
