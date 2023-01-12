package com.parkinglot.parkinglot.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class ParkingSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String licensenseNo;
    private String parkingLotId;
    private String floorId;
    private String slotId;
    private int vehicleSize;
    private String timeCreated;
}
