package com.Icwd.hotel.services;

import com.Icwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel create(Hotel hotel);

    //getAll
    List<Hotel> getAll();

    //getBy Id
    Hotel get(String id);
}
