package com.parkinglot.parkinglot;

import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Exceptions.ParkingLotException;
import com.parkinglot.parkinglot.Mappers.ResponseMapper;
import com.parkinglot.parkinglot.Model.ParkingDeallocateResponse;
import com.parkinglot.parkinglot.Repository.BaysRepo;
import com.parkinglot.parkinglot.Service.Deallocate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DeallocateServiceTest {
    @Mock
    private BaysRepo baysRepo;
    @Mock
    private ResponseMapper responseMapper;
    @InjectMocks
    private Deallocate deallocate;

    @Test
    public void testDeallocateSpotForSuccess(){
        Bay bay = new Bay();
        bay.setId("test");
        bay.setIsAvailable(false);
        ParkingDeallocateResponse parkingDeallocateResponse = new ParkingDeallocateResponse();
        parkingDeallocateResponse.setBayId("test");
        when(baysRepo.findById(any())).thenReturn(Optional.of(bay));
        when(responseMapper.BayToParkingDeallocateResponse(any())).thenReturn(parkingDeallocateResponse);
        ParkingDeallocateResponse parkingDeallocateResponse1=deallocate.deAllocateSpot("TEST","TEST");
        Assertions.assertEquals(parkingDeallocateResponse1.getBayId(),parkingDeallocateResponse.getBayId());
    }
    @Test
    public void testDeallocateSpotForException(){
        when(baysRepo.findById(any())).thenReturn(Optional.empty());
       Throwable exception= Assertions.assertThrows(ParkingLotException.class,
               ()->deallocate.deAllocateSpot("test","test"));
        Assertions.assertEquals("NO BAY ID FOUND",exception.getMessage());
    }
}
