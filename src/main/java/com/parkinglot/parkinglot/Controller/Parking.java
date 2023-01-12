package com.parkinglot.parkinglot.Controller;

import com.parkinglot.parkinglot.Constants.ApplicationConstants;
import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Model.ParkingDeallocateResponse;
import com.parkinglot.parkinglot.Model.ParkingSlipResponse;
import com.parkinglot.parkinglot.Model.Vehicle;
import com.parkinglot.parkinglot.Service.Allocate;
import com.parkinglot.parkinglot.Service.Deallocate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
@AllArgsConstructor
@Slf4j
public class Parking {

    private final Allocate allocate;
    private final Deallocate deallocate;

    @RequestMapping(value = "/allocate/{" + ApplicationConstants.PATH_PARAM_PARKING_LOT_ID + "}",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ParkingSlipResponse> allocateSlot(@PathVariable(ApplicationConstants.PATH_PARAM_PARKING_LOT_ID) String parkingLotId,
                                                            @Valid @RequestBody Vehicle vehicle) {
        ParkingSlipResponse response = allocate.allocateSpot(parkingLotId, vehicle);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @RequestMapping(value = "deallocate/{" + ApplicationConstants.PATH_PARAM_PARKING_LOT_ID + "}/{"
            + ApplicationConstants.PATH_PARAM_BAY_ID + "}",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ParkingDeallocateResponse> deallocateSlot(@PathVariable(ApplicationConstants.PATH_PARAM_PARKING_LOT_ID)
                                                                    String parkingLotId,
                                                                    @PathVariable(ApplicationConstants.PATH_PARAM_BAY_ID)
                                                                    String bayId
    ) {
        ParkingDeallocateResponse parkingDeallocateResponse = deallocate.deAllocateSpot(parkingLotId, bayId);
        return new ResponseEntity<>(parkingDeallocateResponse, HttpStatus.CREATED);
    }
}
