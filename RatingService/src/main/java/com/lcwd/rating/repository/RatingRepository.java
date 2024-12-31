package com.lcwd.rating.repository;

import com.lcwd.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating,String> {

    //custom finder Methods
    List<Rating> findByUserId(String userId);

    List<Rating> findByHotelId(String hotelId);
}
