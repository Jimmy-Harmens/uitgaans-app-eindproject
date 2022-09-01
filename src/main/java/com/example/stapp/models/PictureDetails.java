package com.example.stapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PictureDetails {
    @Id
    private String filename;
    @Column
    private String contentType;
    @Column
    private String Url;
    @Column
    private String roleInApp;

    @JsonIgnore
    @ManyToOne
    StAppLocation locatie;

    public PictureDetails(String filename, String contentType, String url, String roleInApp, StAppLocation locatie){
        this.filename = filename;
        this.contentType = contentType;
        this.Url = url;
        this.roleInApp = roleInApp;
        this.locatie = locatie;
    }

    public PictureDetails(String filename, String contentType, String url, String roleInApp){
        this.filename = filename;
        this.Url = url;
        this.roleInApp = roleInApp;
        this.contentType = contentType;
    }

    public  PictureDetails(){
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getRoleInApp() {
        return roleInApp;
    }

    public void setRoleInApp(String roleInApp) {
        this.roleInApp = roleInApp;
    }

    public StAppLocation getLocatie() {
        return locatie;
    }

    public void setLocatie(StAppLocation locatie) {
        this.locatie = locatie;
    }
}
