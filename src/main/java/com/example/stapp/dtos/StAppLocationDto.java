package com.example.stapp.dtos;

import com.example.stapp.models.PictureDetails;
import com.example.stapp.models.Rating;
import com.example.stapp.models.Tag;
import com.example.stapp.models.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class StAppLocationDto {
    String city;
    String locationName;
    double Latitude;
    double Longitude;
    String owner; //deze
    String typeLocation;
    int openingTime;
    int closingTime;
    boolean sellsFood;
    boolean sellsDrinks;
    String description;
    int reviewAmount;
    double reviewAverage;
    double recommendationScore;
    double distance;
    long locationId;
    List<Tag> tags;
    List<PictureDetails> pictures;

    @JsonSerialize
    List<Rating> ratings;

    public StAppLocationDto(long locationId,
                            String city,
                            String locationName,
                            double latitude,
                            double longitude,
                            String typeLocation,
                            int openingTime,
                            int closingTime,
                            boolean sellsFood,
                            boolean sellsDrinks,
                            String description,
                            int reviewAmount,
                            double reviewAverage,
                            String owner) {
        this.locationId = locationId;
        this.city = city;
        this.locationName = locationName;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.typeLocation = typeLocation;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.sellsFood = sellsFood;
        this.sellsDrinks = sellsDrinks;
        this.description = description;
        this.reviewAmount = reviewAmount;
        this.reviewAverage = reviewAverage;
        this.owner = owner;
    }

    public StAppLocationDto(long locationId,
                            String city,
                            String locationName,
                            double latitude,
                            double longitude,
                            String typeLocation,
                            int openingTime,
                            int closingTime,
                            boolean sellsFood,
                            boolean sellsDrinks,
                            String description,
                            int reviewAmount,
                            double reviewAverage,
                            String owner,
                            String tag1,
                            String tag2,
                            String tag3,
                            String tag4) {
        this.locationId = locationId;
        this.city = city;
        this.locationName = locationName;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.typeLocation = typeLocation;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.sellsFood = sellsFood;
        this.sellsDrinks = sellsDrinks;
        this.description = description;
        this.reviewAmount = reviewAmount;
        this.reviewAverage = reviewAverage;
        this.owner = owner;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(tag1));
        tags.add(new Tag(tag2));
        tags.add(new Tag(tag3));
        tags.add(new Tag(tag4));
        this.setTags(tags);
    }

    public StAppLocationDto(){

    }

    public List<Rating> getRatings() {
        return ratings;
    }
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public long getLocationId() {
        return locationId;
    }
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return Latitude;
    }
    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }
    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getOwner() {
        return owner;
    } //Deze
    public void setOwner(String bedrijfseigenaar) {
        owner = bedrijfseigenaar;
    }

    public String getTypeLocation() {
        return typeLocation;
    }
    public void setTypeLocation(String typeLocation) {
        this.typeLocation = typeLocation;
    }

    public int getOpeningTime() {
        return openingTime;
    }
    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }

    public int getClosingTime() {
        return closingTime;
    }
    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }

    public boolean isSellsFood() {
        return sellsFood;
    }
    public void setSellsFood(boolean sellsFood) {
        this.sellsFood = sellsFood;
    }

    public boolean isSellsDrinks() {
        return sellsDrinks;
    }
    public void setSellsDrinks(boolean sellsDrinks) {
        this.sellsDrinks = sellsDrinks;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getReviewAmount() {
        return reviewAmount;
    }
    public void setReviewAmount(int reviewAmount) {
        this.reviewAmount = reviewAmount;
    }

    public double getReviewAverage() {
        return reviewAverage;
    }
    public void setReviewAverage(double reviewAverage) {
        this.reviewAverage = reviewAverage;
    }

    public double getRecommendationScore() {
        return recommendationScore;
    }
    public void setRecommendationScore(double recommendationScore) {
        this.recommendationScore = recommendationScore;
    }

    public List<Tag> getTags() {
        return tags;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<PictureDetails> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureDetails> pictures) {
        this.pictures = pictures;
    }
}
