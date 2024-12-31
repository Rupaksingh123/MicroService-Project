package com.Icwd.user.service.services;

import com.Icwd.user.service.entities.Hotel;
import com.Icwd.user.service.entities.Rating;
import com.Icwd.user.service.entities.User;
import com.Icwd.user.service.exceptions.ResouceNotFoundException;
import com.Icwd.user.service.repositories.UserRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        String randamUserId = UUID.randomUUID().toString();
        user.setUserId(randamUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {

        List<User> users = userRepository.findAll(); // Assume this method fetches all users

        // Fetch ratings for each user
        for (User  user : users) {
            user.getUserId();
//            ArrayList<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), ArrayList.class);
//            user.setRatings(ratings);
//            logger.info("Ratings for user {}: {}", user.getUserId(), ratings);

            Rating[] forEntity = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);

            List<Rating> Ratinglist = Arrays.stream(forEntity).toList();

            List<Rating> ratingList=Ratinglist.stream().map(rating -> {


                ResponseEntity<Hotel> forEntitty= restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);
                Hotel hotel=forEntitty.getBody();
                logger.info("response hotel : "+hotel);

                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());

            user.setRatings(ratingList);
        }
        return users;
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User with given id is not found on server !! :  " + userId));
        Rating[] forEntity = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        List<Rating> Ratinglist = Arrays.stream(forEntity).toList();

        List<Rating> ratingList=Ratinglist.stream().map(rating -> {


            ResponseEntity<Hotel> forEntitty= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel=forEntitty.getBody();
            logger.info("response hotel : "+hotel);

            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        logger.info("" +forEntity);
        return user;
    }
}
