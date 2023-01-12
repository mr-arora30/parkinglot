package com.parkinglot.parkinglot.Repository;

import com.parkinglot.parkinglot.Entity.ParkingLot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepo extends CrudRepository<ParkingLot,String> {
}
