package com.johnruffer.budgetr.models;

import java.util.Date;

/**
 * Created by John on 7/13/2014.
 */
public class Expense {
    private final long id;
    private final String budgetName;
    private final String name;
    private final double amount;
    private final Date date;

    public Expense( String budgetName, String name, double amount, Date date ) {
        this( 0, budgetName, name, amount, date );
    }

    public Expense( long id, String budgetName, String name, double amount, Date date ) {
        this.id = id;
        this.budgetName = budgetName;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public long getId() {
        return this.id;
    }

    public String getBudgetName() {
        return this.budgetName;
    }

    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return "Expense: " + this.name +
                " Amount: " + String.format( "%.2f", this.amount );
    }
}
