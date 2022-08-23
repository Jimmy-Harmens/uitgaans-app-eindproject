package com.example.stapp.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class StAppLocation {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    Long locationId;
    @Column
    String city;
    @Column
    String locationName;
    @Column
    double latitude;
    @Column
    double longitude;
    @Column
    String typeLocation;
    @Column
    int openingTime;
    @Column
    int closingTime;
    @Column
    boolean sellsFood;
    @Column
    boolean sellsDrinks;
    @Column
    String description;
    @Column
    int reviewAmount;
    @Column
    double reviewAverage;

    @OneToMany(
            mappedBy = "locatie",
            targetEntity = Rating.class
    )
    Set<Rating> ratings;

    @ManyToOne
    @JoinColumn
    User owner;

    @ManyToMany(mappedBy = "favorites")
    Set<User> favoredBy;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<User> getFavoredBy() {
        return favoredBy;
    }

    public void setFavoredBy(Set<User> favoredBy) {
        this.favoredBy = favoredBy;
    }
}
