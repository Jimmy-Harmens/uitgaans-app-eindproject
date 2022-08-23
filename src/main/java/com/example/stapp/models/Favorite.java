package com.example.stapp.models;

import javax.persistence.*;

@Entity
public class Favorite {
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
}
