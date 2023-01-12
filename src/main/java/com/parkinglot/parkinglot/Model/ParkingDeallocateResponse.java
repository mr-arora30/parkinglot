package com.parkinglot.parkinglot.Model;

import lombok.Data;

@Data
public class ParkingDeallocateResponse {
    private String parkingId;
    private String parkingLotName;
    private String floorId;
    private String bayId;
    private String slotSize;
    private String status;
}
