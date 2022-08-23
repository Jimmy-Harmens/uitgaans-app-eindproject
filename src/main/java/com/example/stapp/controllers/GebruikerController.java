package com.example.stapp.controllers;

import com.example.stapp.dtos.UserDto;
import com.example.stapp.services.CustomUserDetailService;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    @GetMapping({"/login"})
    public ResponseEntity<Object> getUser(@RequestParam String username, @RequestParam String password){
        UserDto userDto = userService.getUser(username);
        if (userDto.password.equals(password)){
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> createKlant(@RequestBody UserDto dto) {;

        String newUsername = userService.createUser(dto);
        if(newUsername.equals(dto.getUsername())){
            userService.addAuthority(newUsername, "ADMIN");
            return new ResponseEntity<>(newUsername, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(newUsername, HttpStatus.OK);
        }
    }
}
