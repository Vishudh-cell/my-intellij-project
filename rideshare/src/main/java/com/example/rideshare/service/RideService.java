package com.example.rideshare.service;

import com.example.rideshare.dto.CreateRideRequest;
import com.example.rideshare.model.Ride;
import com.example.rideshare.repository.RideRepository;
import com.example.rideshare.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    // Create ride
    public Ride createRide(CreateRideRequest request, String userId) {

        Ride ride = new Ride();
        ride.setUserId(userId);

        // FIXED — use your actual field names
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());

        ride.setStatus("REQUESTED");
        ride.setCreatedAt(new Date());

        return rideRepository.save(ride);
    }

    // Get rides of a user
    public List<Ride> getUserRides(String userId) {
        return rideRepository.findByUserId(userId);
    }

    // List pending ride requests
    public List<Ride> getPendingRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    // Driver accepts a ride
    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId);

        if (ride == null) {
            throw new BadRequestException("Ride not found");
        }

        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");

        return rideRepository.save(ride);
    }

    // Complete ride
    public Ride completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId);

        if (ride == null) {
            throw new BadRequestException("Ride not found");
        }

        ride.setStatus("COMPLETED");

        return rideRepository.save(ride);
    }
}
