package com.parkinglot.parkinglot.Service;

import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Exceptions.ParkingLotException;
import com.parkinglot.parkinglot.Mappers.ResponseMapper;
import com.parkinglot.parkinglot.Model.ParkingDeallocateResponse;
import com.parkinglot.parkinglot.Repository.BaysRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class Deallocate {

    private final BaysRepo baysRepo;
    private final ResponseMapper responseMapper;

    @Transactional
    public ParkingDeallocateResponse deAllocateSpot(String parkingLotId, String bayId) {
        Optional<Bay> bay = baysRepo.findById(bayId);
        if (bay.isPresent()) {
            Bay bayDao = bay.get();
            if (bayDao.getIsAvailable()) {
                log.error("Bay is already free for bayId: {} and parkingLotId {}", bayId, parkingLotId);
                throw new ParkingLotException("BAY SLOT IS ALREADY FREED");
            }
            bayDao.setIsAvailable(true);
            Bay updatedBay = baysRepo.save(bayDao);
            log.info("Spot freed for bayId: {} and parkingLotId: {}", bayId, parkingLotId);
            return responseMapper.BayToParkingDeallocateResponse(updatedBay);
        } else {
            log.error("Bay is not found bayId: {} and parkingLotId {}", bayId, parkingLotId);
            throw new ParkingLotException("NO BAY ID FOUND");
        }

    }

}
