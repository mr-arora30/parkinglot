package com.parkinglot.parkinglot.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String bayId;
    private int vehicleSize;
    private String timeCreated;
}
