package com.example.stapp.controllers;

import com.example.stapp.dtos.RequestDto;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.services.StAppLocationService;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/location")
public class StAppLocationController {

    @Autowired
    private StAppLocationService locationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<StAppLocationDto> addLocation(@RequestBody StAppLocationDto locationDto){

//        locationDto.setOwner(userService.locationDto.getOwner(); //magic
        String locatieNaam = locationService.createlocation(locationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{locatie-naam}")
                .buildAndExpand(locatieNaam).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/hardcode")
    public ResponseEntity<Object> massAddLocation(@RequestBody List<StAppLocationDto> locations){ //not for commercial use!!
        for(StAppLocationDto locationDto : locations){
            locationService.createlocation(locationDto);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Object> getRecommendations(@RequestParam String city,
                                                     @RequestParam double latitude,
                                                     @RequestParam double longitude,
                                                     @RequestParam String username,
                                                     @RequestParam(required = false) boolean isHungry,
                                                     @RequestParam(required = false) boolean isThirsty){
        RequestDto requestData = new RequestDto();
        requestData.setCity(city);
        requestData.setLatitude(latitude);
        requestData.setLongitude(longitude);
        requestData.setUsername(username);
        requestData.setHeeftHonger(isHungry);
        requestData.setHeeftDorst(isThirsty);
        List<StAppLocationDto> recommendations = locationService.getRecommendations(requestData);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    } //het gaat om deze

    @GetMapping("/byId")
    public ResponseEntity<Object> getOneLocationById(@RequestParam long locationId){
        return new ResponseEntity<>(locationService.getById(locationId), HttpStatus.OK);
    }
}
