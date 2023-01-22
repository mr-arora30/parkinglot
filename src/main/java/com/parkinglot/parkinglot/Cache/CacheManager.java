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
    private static final int CACHE_SIZE = 1;
    private static final boolean REFRESH = true;
    private static final boolean NO_REFRESH = false;


    static {
        parkingLotCache = new ConcurrentHashMap<>();
        LRUCache = new ConcurrentLinkedDeque<>();
    }

    @Autowired
    public void setBaysRepo(BaysRepo baysRepo) {
        CacheManager.baysRepo = baysRepo;
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

    //lazily loads slots by size
    public static Bay getAvailableSlotBySize(String parkingLotId,
                                             VehicleSize vehicleSize) {
        Bay resultBay = null;
        boolean isCacheRefreshed = false;
        Map<Integer, Queue<Bay>> parkingBaysMap = parkingLotCache.get(parkingLotId);
        for (int i = vehicleSize.getSize(); i <= vehicleSize.XL.getSize(); i++) {
            if (parkingBaysMap == null) {
                refer(parkingLotId, i, REFRESH);
                isCacheRefreshed = true;
                parkingBaysMap = parkingLotCache.get(parkingLotId);
                if(parkingBaysMap==null){
                    continue;
                }
            }
            if (null == parkingBaysMap.get(i)) {
                refer(parkingLotId, i, REFRESH);
                isCacheRefreshed = true;
            }
            if (parkingBaysMap != null && null != parkingBaysMap.get(i)) {
                Queue<Bay> bays = parkingBaysMap.get(i);
                if (bays.isEmpty()) {
                    refer(parkingLotId, i, REFRESH);
                    isCacheRefreshed = true;
                    bays = parkingBaysMap.get(i);
                }
                if (!bays.isEmpty()) {
                    resultBay = bays.poll();
                    break;
                }
            }
        }
        if (!isCacheRefreshed) {
            refer(parkingLotId, vehicleSize.getSize(), NO_REFRESH);
        }
        return resultBay;
    }

    private static void refer(String parkingLotId, int vehicleSize, boolean isRefresh) {
        if (!parkingLotCache.containsKey(parkingLotId)) {
            if (LRUCache.size() == CACHE_SIZE) {
                String leastUsed = LRUCache.pollLast();
                parkingLotCache.remove(leastUsed);
            }
        } else {
            LRUCache.remove(parkingLotId);
        }
        LRUCache.offerFirst(parkingLotId);
        if (isRefresh) {
            refreshCacheByVehicleSize(parkingLotId, vehicleSize);
        }
    }
}
