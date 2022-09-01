package com.example.stapp.services;

import com.example.stapp.dtos.RequestDto;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.dtos.UserDto;
import com.example.stapp.exceptions.RecordNotFoundException;
import com.example.stapp.models.*;
import com.example.stapp.repositories.RatingRepository;
import com.example.stapp.repositories.StAppLocationRepository;
import com.example.stapp.repositories.UserRepository;
import com.example.stapp.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.lang.Integer.valueOf;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StAppLocationRepository locationRepository;

//    @Autowired
//    private AuthorityRepository authorityRepository;

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {
        if(userExists(userDto.getUsername())){
            return "Deze gebruikersnaam is niet beschikbaar";
        }else if(userRepository.findByEmail(userDto.email) != null){
            return "Er bestaat al een account met dit email-adres";
        }else{
            String randomString = RandomStringGenerator.generateAlphaNumeric(20);
            userDto.setApikey(randomString);
            User newUser = userRepository.save(toUser(userDto));
            return newUser.getUsername();
        }
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public void addFavorite(String username, Long locationId){
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        StAppLocation location = locationRepository.findByLocationId(locationId);
        user.addFavorite(location);
        userRepository.save(user);
    }

    public void removeFavorite(String username, Long locationId){
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        StAppLocation location = locationRepository.findByLocationId(locationId);
        user.removeFavorite(location);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        dto.name = user.getName();
        dto.favorites = user.getFavorites();
        dto.ratings = user.getRatings();
        dto.owned = user.getOwnedLocation();
        dto.profilePicture = user.getProfilePicture();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setRatings(userDto.getRatings());
        user.setFavorites(userDto.getFavorites());
        user.setOwnedLocation(userDto.getOwned());
        user.setProfilePicture(userDto.getProfilePicture());

        return user;
    }


    public Rating addRating(String username, StAppLocation stAppLocation, int rating) {
        User user = userRepository.findByUsername(username);
        Rating ratingToAdd = new Rating(user, stAppLocation, rating);
        user.addRating(ratingToAdd);
        userRepository.save(user);
        return ratingToAdd;
    }

    public void  removeRating(Rating rating){
        User user = userRepository.findByUsername(rating.getGebruiker().getUsername());
        user.removeRating(rating);
        userRepository.save(user);
    }

    public void addProfilePicture(PictureDetails profilePicture, String username){
        User user = userRepository.findByUsername(username);
        user.setProfilePicture(profilePicture);
        userRepository.save(user);
    }

    public boolean findTheBoss() {
        return userRepository.existsById("Jimmy");
    }

    @PostConstruct
    public void hardcodeUsers(){
        UserDto admin = new UserDto();

        admin.setName("Jimmy Harmens");
        admin.setUsername("Jimmy");
        admin.setPassword("$2a$12$nq.4NL2/YblTP1/RV47c9.2icktDrkLDSFTXEAhGZXdXtWoYo9hLm");
        admin.setEmail("jimm.harmens@gmail.com");
        admin.setEnabled(true);
        admin.setAuthorities(new HashSet<>());

        createUser(admin);
        addAuthority("Jimmy", "ROLE_ADMIN");
        addAuthority("Jimmy", "ROLE_USER");

        UserDto business = new UserDto();

        business.setName("John Doe");
        business.setUsername("Johnny");
        business.setPassword("$2a$12$/k2n2gD01gqoxbsQ/4sW.eCr5EvOypqdNyYyKsvtljcVSQKCMT6/e");
        business.setEmail("john.doe@gmail.com");
        business.setEnabled(true);
        business.setAuthorities(new HashSet<>());

        createUser(business);
        addAuthority("Johnny", "ROLE_BUSINESS");
        addAuthority("Johnny", "ROLE_USER");

        UserDto user = new UserDto();

        user.setName("Max Smith");
        user.setUsername("Maximus");
        user.setPassword("$2a$12$zMZMqeZOS6PV2Q0c0//Hr./00wj0eSqWks6I0du9efpyYqqxtl1ba");
        user.setEmail("max.smith@gmail.com");
        user.setEnabled(true);
        user.setAuthorities(new HashSet<>());

        createUser(user);
        addAuthority("Maximus", "ROLE_USER");
    }
}

