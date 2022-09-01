package com.example.stapp.controllers;

import com.example.stapp.dtos.RatingRequestDto;
import com.example.stapp.dtos.UserDto;
import com.example.stapp.models.Rating;
import com.example.stapp.models.StAppLocation;
import com.example.stapp.models.User;
import com.example.stapp.services.StAppLocationService;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private StAppLocationService locationService;

    @GetMapping()
    public ResponseEntity<Object> getRating(@RequestParam String username, @RequestParam Long locationId){
        List<Rating> ratings = userService.toUser(userService.getUser(username)).getRatings();
        int returnRating = 0;
        for(Rating rating : ratings){
            if(Objects.equals(rating.getLocatie().getLocationId(), locationId)){
                returnRating = rating.getRating();
            }
        }
        return new ResponseEntity<>(returnRating, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> setRating(@RequestBody RatingRequestDto requestDto){
        StAppLocation location = locationService.toStAppLocation(locationService.getById(requestDto.locationId));
        User user = userService.toUser(userService.getUser(requestDto.getUsername()));
        for(Rating userRating : user.getRatings()){
            if (Objects.equals(userRating.getLocatie().getLocationId(), requestDto.getLocationId())) {
                locationService.removeRating(location, userRating);
                userService.removeRating(userRating);
                break;
            }
        }
        Rating rating = userService.addRating(requestDto.getUsername(), location, requestDto.rating); //gaat fout
        locationService.addRating(rating);
        locationService.updateRatings(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
