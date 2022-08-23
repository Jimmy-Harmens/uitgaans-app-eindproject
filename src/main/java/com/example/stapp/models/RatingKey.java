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
}
