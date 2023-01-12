package com.parkinglot.parkinglot.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "FLOOR")
@Getter
public class Floor {
    @Column(name = "FLOOR_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String parkingLotId;
    private long noOfBays;
    private long noOfSBays;
    private long noOfMBays;
    private long noOfLBays;
    private long noOfXLBays;
    private long timeCreated;
    private long timeUpdated;

    @ManyToOne
    @JoinColumn(name ="FK_PARKING_LOT_ID" )
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "floor")
    private List<Bay> bays;

}
