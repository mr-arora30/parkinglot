package com.parkinglot.parkinglot.Repository;

import com.parkinglot.parkinglot.Entity.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepo extends CrudRepository<Floor,String> {
}
