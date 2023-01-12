package com.parkinglot.parkinglot.Controller;

import com.parkinglot.parkinglot.Constants.ApplicationConstants;
import com.parkinglot.parkinglot.Entity.ParkingSlip;
import com.parkinglot.parkinglot.Model.Vehicle;
import com.parkinglot.parkinglot.Service.Allocate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
@AllArgsConstructor
public class Parking {

    private final Allocate allocate;
    @RequestMapping(value = "/allocate/{"+ ApplicationConstants.PATH_PARAM_PARKING_LOT_ID+"}",
            method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<ParkingSlip> allocateSlot(@PathVariable(ApplicationConstants.PATH_PARAM_PARKING_LOT_ID) String parkingLotId,
                                                    @Valid @RequestBody Vehicle vehicle){
    ParkingSlip parkingSlip=allocate.allocate(parkingLotId,vehicle);

    return new ResponseEntity<>(parkingSlip, HttpStatus.OK);

    }

    @RequestMapping(value = "deallocate/{"+ApplicationConstants.PATH_PARAM_PARKING_LOT_ID+"}/{"
            +ApplicationConstants.PATH_PARAM_BAY_ID+"}",
            method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public void deallocateSlot(@PathVariable (ApplicationConstants.PATH_PARAM_PARKING_LOT_ID)
                                   int parkingLotId,
                               @PathVariable (ApplicationConstants.PATH_PARAM_BAY_ID)
                               int bayId
                               ){

    }
    @GetMapping("/")
    public String test(){
        return "HElLO";
    }
}
