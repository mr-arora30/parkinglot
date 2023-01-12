package com.parkinglot.parkinglot.Repository;

import com.parkinglot.parkinglot.Entity.ParkingSlip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlipRepo extends CrudRepository<ParkingSlip,String> {
}
