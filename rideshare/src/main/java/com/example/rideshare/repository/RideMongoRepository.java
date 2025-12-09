package com.example.rideshare.repository;


import com.example.rideshare.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideMongoRepository extends MongoRepository<Ride, String> {
}