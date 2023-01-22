package com.parkinglot.parkinglot.Service;


import com.parkinglot.parkinglot.Cache.CacheManager;
import com.parkinglot.parkinglot.Constants.ApplicationConstants;
import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Mappers.ResponseMapper;
import com.parkinglot.parkinglot.Model.ParkingSlipResponse;
import com.parkinglot.parkinglot.Model.Vehicle;
import com.parkinglot.parkinglot.Repository.BaysRepo;
import com.parkinglot.parkinglot.Repository.ParkingSlipRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



@Service
@AllArgsConstructor
@Slf4j
public class Allocate {
    private final BaysRepo baysRepo;
    private final ParkingSlipRepo parkingSlipRepo;
    private final ResponseMapper responseMapper;

    @Transactional
    public ParkingSlipResponse allocateSpot(String parkingLotId, Vehicle vehicle) {
        ParkingSlip parkingSlip = null;
        Bay allotedBay=CacheManager.getAvailableSlotBySize(parkingLotId,vehicle.getSize());
        if (null!=allotedBay) {
            allotedBay.setIsAvailable(false);
            Bay savedBay = baysRepo.save(allotedBay);
            parkingSlip = printParkingSlip(savedBay, vehicle);
        } else {
            log.info("No Spot Found for parkingLotId: {}", parkingLotId);
            parkingSlip = ParkingSlip.builder()
                    .floorId(ApplicationConstants.NO_SLOT_FOUND)
                    .bayId(ApplicationConstants.NO_SLOT_FOUND)
                    .licensenseNo(vehicle.getLicenseNo())
                    .build();
        }
        log.info("Spot Allocated for parkingLotId: {} at BayId: {} for vehichle licenseNo: {}",
                parkingLotId, parkingSlip.getParkingLotId(), parkingSlip.getLicensenseNo());
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
