package com.parkinglot.parkinglot.Mappers;

import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Model.ParkingSlipResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ResponseMapper {

    ResponseMapper INSTANCE = Mappers.getMapper( ResponseMapper.class );
    @Mapping(source = "floorId", target = "floorId")
    @Mapping(source = "bayId", target = "bayId")
    ParkingSlipResponse parkingSlipToParkingSlipReponse(ParkingSlip parkingSlip);
}
