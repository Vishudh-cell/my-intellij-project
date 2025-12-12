package com.example.rideshare2.repository;

import com.example.rideshare2.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<Ride, String> {
}
