package com.parkinglot.parkinglot.Repository;

import com.parkinglot.parkinglot.Entity.Bay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaysRepo extends JpaRepository<Bay,String> {

    Optional<List<Bay>> findAllByParkingLot_IdAndIsAvailable(String parkingLot, Boolean isAvailable);
    Optional<List<Bay>> findFirst100ByParkingLot_IdAndSlotSizeAndIsAvailable(String parkingLot,int slotSize, Boolean isAvailable);

}
