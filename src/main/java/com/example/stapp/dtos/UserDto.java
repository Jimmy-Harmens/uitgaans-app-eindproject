package com.example.stapp.dtos;

import com.example.stapp.models.Rating;
import com.example.stapp.models.StAppLocation;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.stapp.models.Authority;

import java.util.List;
import java.util.Set;

public class UserDto {

    public String name;
    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    @JsonSerialize
    public Set<Authority> authorities;
    @JsonSerialize
    public List<StAppLocation> favorites;
    @JsonSerialize
    public List<Rating> ratings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<StAppLocation> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<StAppLocation> favorites) {
        this.favorites = favorites;
    }

    public List<Rating> getRatings(){return ratings;}

    public void setRatings(List<Rating> ratings){this.ratings = ratings;}
}
