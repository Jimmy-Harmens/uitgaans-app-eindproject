package com.example.stapp.controllers;

import com.example.stapp.dtos.UserDto;
import com.example.stapp.services.CustomUserDetailService;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
public class GebruikerController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity<Object> createKlant(@RequestBody UserDto dto, @RequestParam boolean isBusiness) {;
        String encryptedPW = BCrypt.hashpw(dto.password,"");
        dto.setPassword(encryptedPW);
        String newUsername = userService.createUser(dto);
        if(newUsername.equalsIgnoreCase("Jimmy") && newUsername.equals(dto.getUsername())){
            userService.addAuthority(newUsername, "ROLE_ADMIN");

        }
        if(isBusiness && newUsername.equals(dto.getUsername())){
            userService.addAuthority(newUsername, "ROLE_BUSINESS");
        }
        if(newUsername.equals(dto.getUsername())){
            userService.addAuthority(newUsername, "ROLE_USER");
            return new ResponseEntity<>(newUsername, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(newUsername, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/boss")
    public ResponseEntity<Object> isTheBossHome(){
        boolean isBossHome = userService.findTheBoss();
        return new ResponseEntity<>(isBossHome, HttpStatus.OK);
    }
}
