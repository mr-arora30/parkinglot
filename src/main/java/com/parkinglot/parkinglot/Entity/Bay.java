package com.parkinglot.parkinglot.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Bay {
    @Id
    @Column(name = "BAY_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull
    private int slotSize;
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "FK_PARKING_LOT_ID")
    private ParkingLot parkingLot;

    @ManyToOne
    @JoinColumn(name = "FK_FLOOR_ID")
    private Floor floor;


}
