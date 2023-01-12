package com.parkinglot.parkinglot.Enums;

public enum VehicleSize {
    S(1), M(2), L(3), XL(4);

    public int getSize() {
        return size;
    }

    private final int size;

    VehicleSize(int size) {
        this.size = size;
    }
}
