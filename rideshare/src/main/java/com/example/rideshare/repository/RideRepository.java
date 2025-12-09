package com.example.rideshare.repository;

import com.example.rideshare.model.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RideRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Ride save(Ride ride) {
        return mongoTemplate.save(ride);
    }

    public Ride findById(String id) {
        return mongoTemplate.findById(id, Ride.class);
    }

    public List<Ride> findByUserId(String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Ride.class);
    }

    public List<Ride> findByStatus(String status) {
        Query query = Query.query(Criteria.where("status").is(status));
        return mongoTemplate.find(query, Ride.class);
    }
}
