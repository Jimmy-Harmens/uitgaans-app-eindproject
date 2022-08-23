package com.example.stapp.dtos;

//52.363800252511375, 4.883625525271591

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
}
