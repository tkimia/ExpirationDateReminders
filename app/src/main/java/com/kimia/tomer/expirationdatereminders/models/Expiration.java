package com.kimia.tomer.expirationdatereminders.models;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Expiration {
    private final long id;
    private String name;
    private long expirationDate;

    public Expiration(long id, String name, long expirationDate) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public long getDaysLeft() {
        long now = Calendar.getInstance().getTimeInMillis();
        long tillExpiration  = expirationDate - now;

        return TimeUnit.MILLISECONDS.toDays(tillExpiration) + 1; //off by 1
    }

    public long getId() {
        return id;
    }

}
