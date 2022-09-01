package com.example.stapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tags",
            joinColumns = @JoinColumn(name = "locationId"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    @Column
    List<Tag> tags;

    @JsonIgnore
    @OneToMany(
            mappedBy = "locatie",
            targetEntity = Rating.class
    )
    List<Rating> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "locatie",
            targetEntity = PictureDetails.class
    )
    List<PictureDetails> pictures = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    User owner;

    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    List<User> favoredBy;

    public StAppLocation(
            Long locationId,
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
            String owner){
        this.locationId = locationId;
        this.city = city;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeLocation = typeLocation;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.sellsFood = sellsFood;
        this.sellsDrinks = sellsDrinks;
        this.description = description;
        this.reviewAmount = reviewAmount;
        this.reviewAverage = reviewAverage;
        this.owner = new User(owner);
    }

    public StAppLocation() {

    }

    public StAppLocation(Long locationId) {
        this.locationId = locationId;
    }

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


    public List<User> getFavoredBy() {
        return favoredBy;
    }
    public void setFavoredBy(List<User> favoredBy) {
        this.favoredBy = favoredBy;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    public List<Rating> getRatings() {
        return ratings;
    }
    public void addRating(Rating rating){this.ratings.add(rating);}
    public void removeRating(Rating rating){this.ratings.remove(rating);}

    public List<Tag> getTags() {
        return tags;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public void addTag(Tag tag){this.tags.add(tag);}
    public void removeTag(Tag tag){this.tags.remove(tag);}

    public List<PictureDetails> getPictures() {
        return pictures;
    }
    public void setPictures(List<PictureDetails> pictures) {
        this.pictures = pictures;
    }
    public void addPicture(PictureDetails picture){
        this.pictures.add(picture);
    }
}
