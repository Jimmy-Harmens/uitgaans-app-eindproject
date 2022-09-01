package com.example.stapp;

import com.example.stapp.controllers.GebruikerController;
import com.example.stapp.controllers.StAppLocationController;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.dtos.UserDto;
import com.example.stapp.services.StAppLocationService;
import com.example.stapp.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;

@SpringBootApplication
public class StAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StAppApplication.class, args);
    }


}
