package com.johnruffer.budgetr.exceptions;

/**
 * Created by John on 7/13/2014.
 */
public class DbErrorException extends Exception {
    public DbErrorException( String message ) {
        super( message );
    }
}
