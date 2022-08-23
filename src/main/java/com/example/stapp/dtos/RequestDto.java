package com.example.stapp.dtos;

public class RequestDto {
    String city;
    double latitude;
    double longitude;
    String username;
    boolean heeftHonger;
    boolean heeftDorst;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHeeftHonger() {
        return heeftHonger;
    }

    public void setHeeftHonger(boolean heeftHonger) {
        this.heeftHonger = heeftHonger;
    }

    public boolean isHeeftDorst() {
        return heeftDorst;
    }

    public void setHeeftDorst(boolean heeftDorst) {
        this.heeftDorst = heeftDorst;
    }
}
