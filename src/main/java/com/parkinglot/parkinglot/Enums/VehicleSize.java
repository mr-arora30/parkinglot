package com.parkinglot.parkinglot.Enums;

public enum VehicleSize {
    S(1),M(2),L(3),XL(4);

    public int getIndicator() {
        return indicator;
    }

    private final int indicator;
    VehicleSize(int indicator){
        this.indicator=indicator;
    }
}
