package com.example.rideshare.model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import java.util.Date;

@Data
@Document(collection = "rides")
public class Ride {
    @Id
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status; // REQUESTED, ACCEPTED, COMPLETED
    private Date createdAt;
}