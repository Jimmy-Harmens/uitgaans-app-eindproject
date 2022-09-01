package com.example.stapp.controllers;

import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.models.Favorite;
import com.example.stapp.models.StAppLocation;
import com.example.stapp.repositories.FavoritesRepository;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> isLocationFavorite(@RequestParam String username, @RequestParam Long locationId){
        List<StAppLocation> usersFavorites = userService.getUser(username).getFavorites();
        boolean isFavorite = false;
        for(StAppLocation favorite : usersFavorites){
            if (Objects.equals(favorite.getLocationId(), locationId)) {
                isFavorite = true;
                break;
            }
        }
        return new ResponseEntity<>(isFavorite, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addOrRemoveFavorite(@RequestParam String username, @RequestParam Long locationId){
        List<StAppLocation> usersFavorites = userService.getUser(username).getFavorites();
        boolean isFavorite = false;
            for(StAppLocation favorite : usersFavorites){
                if (Objects.equals(favorite.getLocationId(), locationId)) {
                    isFavorite = true;
                    break;
                }
            }
        if(isFavorite){
            userService.removeFavorite(username, locationId);
        }else{
            userService.addFavorite(username, locationId);
        }
        return new ResponseEntity<>("Favorite toggled", HttpStatus.OK);
    }
}
