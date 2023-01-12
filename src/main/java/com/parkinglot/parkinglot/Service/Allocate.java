package com.parkinglot.parkinglot.Service;


import com.parkinglot.parkinglot.Constants.ApplicationConstants;
import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Mappers.ResponseMapper;
import com.parkinglot.parkinglot.Model.ParkingSlipResponse;
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
    private final ResponseMapper responseMapper;

    @Transactional
    public ParkingSlipResponse allocateSpot(String parkingLotId, Vehicle vehicle) {
        Optional<List<Bay>> bayList = baysRepo.findAllByParkingLot_IdAndIsAvailable(parkingLotId, true);
        Optional<Bay> bay = getBayBySize(bayList, vehicle);
        ParkingSlip parkingSlip = null;
        if (bay.isPresent()) {
            Bay bayDao = bay.get();
            bayDao.setIsAvailable(false);
            Bay savedBay = baysRepo.save(bayDao);
            parkingSlip = printParkingSlip(savedBay, vehicle);

        } else {
            parkingSlip = ParkingSlip.builder()
                    .floorId(ApplicationConstants.NO_SLOT_FOUND)
                    .bayId(ApplicationConstants.NO_SLOT_FOUND)
                    .licensenseNo(vehicle.getLicenseNo())
                    .build();
        }

        return responseMapper.parkingSlipToParkingSlipReponse(parkingSlip);

    }

    private Optional<Bay> getBayBySize(Optional<List<Bay>> bayList, Vehicle vehicle) {
        if (bayList.isPresent()) {
            return bayList.get().stream().filter(bays ->
                            bays.getSlotSize() >= vehicle.getSize().getSize()
                                    && null != bays.getIsAvailable()
                                    && bays.getIsAvailable() == true)
                    .sorted(Comparator.comparing(Bay::getSlotSize)).findFirst();
        }
        return Optional.empty();
    }

    private ParkingSlip printParkingSlip(Bay bay, Vehicle vehicle) {
        ParkingSlip parkingSlip = ParkingSlip.builder().bayId(bay.getId())
                .floorId(bay.getFloor().getId())
                .parkingLotId(bay.getParkingLot().getId())
                .vehicleSize(bay.getSlotSize())
                .licensenseNo(vehicle.getLicenseNo())
                .build();
        return parkingSlipRepo.save(parkingSlip);
    }


}
