package com.parkinglot.parkinglot.Service;


import com.parkinglot.parkinglot.Constants.ApplicationConstants;
import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Model.Vehicle;
import com.parkinglot.parkinglot.Repository.BaysRepo;
import com.parkinglot.parkinglot.Repository.ParkingSlipRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class Allocate {
    private final BaysRepo baysRepo;
    private final ParkingSlipRepo parkingSlipRepo;

    @Transactional
    public ParkingSlip allocate(String parkingLotId, Vehicle vehicle) {
        Optional<List<Bay>> bayList = baysRepo.findAllByParkingLot_IdAndIsAvailable(parkingLotId, true);
        Optional<Bay> bay = getBayBySize(bayList,vehicle);
        if(bay.isPresent()){
            Bay bayDao=bay.get();
            bayDao.setIsAvailable(false);
            Bay savedBay=baysRepo.save(bayDao);
            return printParkingSlip(savedBay,vehicle);

        } else{
            return ParkingSlip.builder()
                    .floorId(ApplicationConstants.NO_SLOT_FOUND)
                    .slotId(ApplicationConstants.NO_SLOT_FOUND)
                    .licensenseNo(vehicle.getLicenseNo())
                    .build();
        }

    }

    private Optional<Bay> getBayBySize(Optional<List<Bay>>bayList, Vehicle vehicle) {
        if (bayList.isPresent()) {
            return bayList.get().stream().filter(bays ->
                            bays.getSlotSize() >= vehicle.getSize().getIndicator()
                    && null!=bays.getIsAvailable()
                                    && bays.getIsAvailable()==true)
                    .sorted(Comparator.comparing(Bay::getSlotSize)).findFirst();
        }
        return Optional.empty();
    }

    private ParkingSlip printParkingSlip(Bay bay, Vehicle vehicle){
       ParkingSlip parkingSlip= ParkingSlip.builder().slotId(bay.getId())
                .floorId(bay.getFloor().getId())
                .parkingLotId(bay.getParkingLot().getId())
                .vehicleSize(bay.getSlotSize())
                .licensenseNo(vehicle.getLicenseNo())
                .build();
        return parkingSlipRepo.save(parkingSlip);
    }


}
