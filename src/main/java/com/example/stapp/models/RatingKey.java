package com.example.stapp.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
class RatingKey implements Serializable {
    @Column(name = "username")
    String username;

    @Column(name = "locationId")
    Long locationId;

    public RatingKey(String username, Long locationId) {
        this.username = username;
        this.locationId = locationId;
    }

    public RatingKey() {

    }
}
