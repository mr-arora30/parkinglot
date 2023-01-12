package com.parkinglot.parkinglot.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "PARKING_LOT")
@Getter
public class ParkingLot {
    @Column(name = "PARKING_LOT_ID")
    @Id
    private String id;
    private String name;

    private long time_created;
    private long time_updated;

    @OneToMany(mappedBy = "parkingLot")
    private List<Floor> floors;
}
