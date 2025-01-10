package com.Icwd.user.service.controllers;

import com.Icwd.user.service.entities.User;
import com.Icwd.user.service.services.UserService;
import com.Icwd.user.service.services.UserServiceImpl;
import com.netflix.discovery.converters.Auto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);



    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    //creating fall back method ratingHotelFallback
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
        logger.info("Fallback is executed because service is down" +ex.getMessage());
//        User user= User.builder()
//                .email("trial123@gmail.com")
//                .name("trial")
//                .about("some services down")
//                .userId("1111")
//                .build();
        User  user = new User();
        user.setEmail("trial123@gmail.com");
        user.setName(ex.getMessage());
        user.setAbout("some services down");
       // user.setUser Id("1111");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }


}
