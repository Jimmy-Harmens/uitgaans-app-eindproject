package com.example.stapp.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apikey;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "locationId")
    )
    private List<StAppLocation> favorites = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ratings",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "locationId")
    )
    private Set<StAppLocation> rated = new HashSet<>();

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "gebruiker")
    List<Rating> ratings;

    @OneToMany(mappedBy = "owner")
    Set<StAppLocation> ownedLocation;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() { return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getApikey() { return apikey; }
    public void setApikey(String apikey) { this.apikey = apikey; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}

    public List<StAppLocation> getFavorites(){return favorites;}
    public void addFavorite(StAppLocation location){this.favorites.add(location);}
    public void removeFavorite(StAppLocation location){this.favorites.remove(location);}

    public Set<Authority> getAuthorities() { return authorities; }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    public List<Rating> getRatings(){return ratings;}
    public void addRating(Rating rating){this.ratings.add(rating);}
    public void removeRating(Rating rating){this.ratings.add(rating);}

}