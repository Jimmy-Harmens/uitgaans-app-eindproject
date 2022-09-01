package com.example.stapp.controllers;

import com.example.stapp.dtos.RequestDto;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.models.StAppLocation;
import com.example.stapp.services.StAppLocationService;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/location")
public class StAppLocationController {

    Base64.Decoder decoder = Base64.getUrlDecoder();

    @Autowired
    private StAppLocationService locationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> addLocation(@RequestBody StAppLocationDto locationDto){

//        locationDto.setOwner(userService.locationDto.getOwner(); //magic
        Long locatieId = locationService.createlocation(locationDto);


        return new ResponseEntity<>(locatieId, HttpStatus.CREATED);
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

    @GetMapping("/byid")
    public ResponseEntity<Object> getOneLocationById(@RequestParam long locationId){
        StAppLocationDto location = locationService.getById(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<Object> getFavorites(@RequestParam String username){
        List<StAppLocationDto> payload = locationService.getFavorites(username);
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @GetMapping("/owned")
    public ResponseEntity<Object> getOwned(@RequestParam String username){
        List<StAppLocationDto> payload = locationService.getOwned(username);
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteLocation(@RequestParam Long locationId, @RequestHeader String Authorization){
        String username ="";
        String[] chunks = Authorization.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        String[] fields = payload.split(",");
        for(String field : fields){
            if(field.contains("sub")){
                username = field.split(":")[1];
                username = username.substring(1,username.length()-1);
                break;
            }
        }
        if (locationService.deleteLocation(locationId, username)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
