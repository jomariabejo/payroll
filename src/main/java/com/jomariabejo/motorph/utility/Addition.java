package com.jomariabejo.motorph.utility;

public class Addition {
    private int primaryNumber;
    private int secondaryNumber;


    public Addition(int primaryNumber, int secondaryNumber) {
        this.primaryNumber = primaryNumber;
        this.secondaryNumber = secondaryNumber;
    }

    public int add() {
        return (this.primaryNumber + this.secondaryNumber);
    }
}
