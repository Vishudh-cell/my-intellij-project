package com.example.rideshare2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "rides")
public class Ride {
    @Id
    private String id;

    private String passengerUsername;
    private String driverUsername;

    private String pickupLocation;
    private String dropLocation;

    private Double fare;
    private String status;  // REQUESTED, ACCEPTED, COMPLETED

}