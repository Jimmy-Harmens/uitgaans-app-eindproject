package com.example.stapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    String tag;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    @Column
    List<StAppLocation> locations;

    public Tag(String tag){
        this.tag = tag;
        this.locations = new ArrayList<>();
    }
    public Tag(){
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<StAppLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<StAppLocation> locations) {
        this.locations = locations;
    }
}
