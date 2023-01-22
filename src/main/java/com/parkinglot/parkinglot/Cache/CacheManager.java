package com.parkinglot.parkinglot.Cache;

import com.parkinglot.parkinglot.Entity.Bay;
import com.parkinglot.parkinglot.Enums.VehicleSize;
import com.parkinglot.parkinglot.Repository.BaysRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;


@Component
public class CacheManager {
    public static Map<String, Map<Integer, Queue<Bay>>> parkingLotCache;
    private static BaysRepo baysRepo;
    private static Deque<String> LRUCache;
    private static final int CACHE_SIZE = 100;


    static {
        parkingLotCache = new ConcurrentHashMap<>();
            LRUCache = new ConcurrentLinkedDeque<>();
    }

    @Autowired
    public void setBaysRepo(BaysRepo baysRepo) {
        CacheManager.baysRepo = baysRepo;
    }

    public static void load(String parkingLotId) {

        if (null != parkingLotCache.get(parkingLotId)) {
            return;
        }
        refreshCacheByVehicleSize(parkingLotId, VehicleSize.S.getSize());
        refreshCacheByVehicleSize(parkingLotId, VehicleSize.M.getSize());
        refreshCacheByVehicleSize(parkingLotId, VehicleSize.L.getSize());
        refreshCacheByVehicleSize(parkingLotId, VehicleSize.XL.getSize());
        updateLastUsed(parkingLotId);
    }

    private static void refreshCacheByVehicleSize(String parkingLotId, int vehicleSize) {
        Optional<List<Bay>> bays = baysRepo.findFirst100ByParkingLot_IdAndSlotSizeAndIsAvailable
                (parkingLotId, vehicleSize, true);
        if (bays.isPresent() && !bays.get().isEmpty()) {
            ConcurrentLinkedQueue<Bay> refreshedBays =
                    bays.get().stream().collect(Collectors.toCollection(ConcurrentLinkedQueue::new));

            Map<Integer, Queue<Bay>> parkingBaysMap = parkingLotCache.get(parkingLotId);
            if (null != parkingBaysMap) {
                parkingBaysMap.put(vehicleSize, refreshedBays);
            } else {
                parkingBaysMap = new ConcurrentHashMap<>();
                parkingBaysMap.put(vehicleSize, refreshedBays);
                parkingLotCache.put(parkingLotId, parkingBaysMap);
            }
        }

    }

    public static Bay getAvailableSlotBySize(String parkingLotId,
                                             VehicleSize vehicleSize) {
        Bay resultBay = null;

        Map<Integer, Queue<Bay>> parkingBaysMap = parkingLotCache.get(parkingLotId);
        if (null != parkingBaysMap) {
            for (int i = vehicleSize.getSize(); i <= vehicleSize.XL.getSize(); i++) {
                Queue<Bay> bays = parkingBaysMap.get(i);
                if (!bays.isEmpty()) {
                    resultBay = bays.poll();
                    break;
                } else {
                    refreshCacheByVehicleSize(parkingLotId, i);
                    bays = parkingBaysMap.get(i);
                    if (!bays.isEmpty()) {
                        resultBay = bays.poll();
                        break;
                    }
                }
            }
        }
        updateLastUsed(parkingLotId);
        return resultBay;
    }

    private static void updateLastUsed(String parkingLotId) {
        if (parkingLotCache.containsKey(parkingLotId)) {
            if (LRUCache.size() == CACHE_SIZE) {
                String leastUsed = LRUCache.pollLast();
                if (leastUsed.equals(parkingLotId)) {
                    parkingLotCache.remove(leastUsed);
                }
            }
            LRUCache.remove(parkingLotId);
            LRUCache.offerFirst(parkingLotId);
        }
    }
}
