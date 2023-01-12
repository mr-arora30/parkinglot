package com.parkinglot.parkinglot.Mappers;

import com.parkinglot.parkinglot.Constants.ApplicationConstants;
import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Model.ParkingDeallocateResponse;
import com.parkinglot.parkinglot.Model.ParkingSlipResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResponseMapper {
    @AfterMapping
    default void afterMapping(@MappingTarget ParkingDeallocateResponse target, Bay source) {
        if (source.getIsAvailable()) {
            target.setStatus(ApplicationConstants.SLOT_FREED);
        }
    }

    @Mapping(source = "floorId", target = "floorId")
    @Mapping(source = "bayId", target = "bayId")
    @Mapping(source = "parkingLotId", target = "parkingId")
    ParkingSlipResponse parkingSlipToParkingSlipReponse(ParkingSlip parkingSlip);

    @Mapping(source = "floor.id", target = "floorId")
    @Mapping(source = "id", target = "bayId")
    @Mapping(source = "parkingLot.id", target = "parkingId")
    @Mapping(source = "parkingLot.name", target = "parkingLotName")
    @Mapping(source = "slotSize", target = "slotSize")
    @Mapping(source = "isAvailable", target = "status")
    ParkingDeallocateResponse BayToParkingDeallocateResponse(Bay bay);
}
