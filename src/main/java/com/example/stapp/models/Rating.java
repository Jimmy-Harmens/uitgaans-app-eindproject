package com.example.stapp.models;

import javax.persistence.*;

@Entity
public class Rating {
    @EmbeddedId
    RatingKey id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    User gebruiker;

    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "locationId")
    StAppLocation locatie;

    int rating;

    public Rating(User gebruiker, StAppLocation location, int rating){
        this.gebruiker = gebruiker;
        this.locatie = location;
        this.rating = rating;
        this.id = new RatingKey();
    }

    public Rating() {

    }

    public User getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(User gebruiker) {
        this.gebruiker = gebruiker;
    }

    public StAppLocation getLocatie() {
        return locatie;
    }

    public void setLocatie(StAppLocation locatie) {
        this.locatie = locatie;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
