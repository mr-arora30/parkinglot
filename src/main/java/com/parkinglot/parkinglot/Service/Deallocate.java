package com.parkinglot.parkinglot.Service;

import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Mappers.ResponseMapper;
import com.parkinglot.parkinglot.Model.ParkingDeallocateResponse;
import com.parkinglot.parkinglot.Repository.BaysRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class Deallocate {

    private final BaysRepo baysRepo;
    private final ResponseMapper responseMapper;

    @Transactional
    public ParkingDeallocateResponse deAllocateSpot(String parkingLotId, String bayId){
       Optional<Bay> bay = baysRepo.findById(bayId);
       if(bay.isPresent()){
           Bay bayDao = bay.get();
           bayDao.setIsAvailable(true);
          Bay updatedBay = baysRepo.save(bayDao);
          return responseMapper.BayToParkingDeallocateResponse(updatedBay);
       }else {
            return  null;
       }

    }

}
