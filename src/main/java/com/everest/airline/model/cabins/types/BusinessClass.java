package com.everest.airline.model.cabins.types;

import com.everest.airline.model.cabins.Cabin;

public class BusinessClass extends Cabin {

    public BusinessClass(int capacity, int occupiedSeats, double baseFare) {
        super(capacity, occupiedSeats, baseFare);
    }

    //some behavior related to business class

}
