package com.example.rideshare.controller;

import com.example.rideshare.dto.CreateRideRequest;
import com.example.rideshare.model.Ride;
import com.example.rideshare.service.RideService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public ResponseEntity<Ride> createRide(
            @Valid @RequestBody CreateRideRequest request,
            Authentication auth) {

        String userId = auth.getName();
        Ride ride = rideService.createRide(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ride);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Ride>> getUserRides(Authentication auth) {
        String userId = auth.getName();
        return ResponseEntity.ok(rideService.getUserRides(userId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Ride>> getPendingRides() {
        return ResponseEntity.ok(rideService.getPendingRides());
    }

    @PostMapping("/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(
            @PathVariable String rideId,
            Authentication auth) {

        String driverId = auth.getName();
        return ResponseEntity.ok(rideService.acceptRide(rideId, driverId));
    }

    @PostMapping("/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }
}
