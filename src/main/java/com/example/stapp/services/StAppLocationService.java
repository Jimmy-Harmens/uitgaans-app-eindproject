package com.example.stapp.services;

import com.example.stapp.dtos.RatingRequestDto;
import com.example.stapp.dtos.RequestDto;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.dtos.UserDto;
import com.example.stapp.models.*;
import com.example.stapp.repositories.FavoritesRepository;
import com.example.stapp.repositories.RatingRepository;
import com.example.stapp.repositories.StAppLocationRepository;
import com.example.stapp.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class StAppLocationService {

    @Autowired
    private StAppLocationRepository locationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private FavoritesRepository favoritesRepository;
    @Autowired
    private TagRepository tagRepository;

    public StAppLocationDto getById(Long id){
        return fromStAppLocation(locationRepository.findByLocationId(id));
    }

    public List<StAppLocationDto> getLocationsInCity(String city) {
        List<StAppLocationDto> locations = new ArrayList<>();
        List<StAppLocation> modelList = locationRepository.findByCity(city);
        for (StAppLocation location: modelList){
            locations.add(fromStAppLocation(location));
        }
        return locations;
    }

    public StAppLocationDto fromStAppLocation(StAppLocation stAppLocation){

        var dto = new StAppLocationDto();

        dto.setCity(stAppLocation.getCity());
        dto.setLocationName(stAppLocation.getLocationName());
        dto.setLatitude(stAppLocation.getLatitude());
        dto.setLongitude(stAppLocation.getLongitude());
        dto.setOwner(stAppLocation.getOwner().getUsername());
        dto.setTypeLocation(stAppLocation.getTypeLocation());
        dto.setOpeningTime(stAppLocation.getOpeningTime());
        dto.setClosingTime(stAppLocation.getClosingTime());
        dto.setSellsFood(stAppLocation.isSellsFood());
        dto.setSellsDrinks(stAppLocation.isSellsDrinks());
        dto.setDescription(stAppLocation.getDescription());
        dto.setReviewAmount(stAppLocation.getReviewAmount());
        dto.setReviewAverage(stAppLocation.getReviewAverage());
        dto.setRecommendationScore(0);
        dto.setLocationId(stAppLocation.getLocationId());
        dto.setRatings(stAppLocation.getRatings());
        dto.setTags(stAppLocation.getTags());
        dto.setPictures(stAppLocation.getPictures());

        return dto;
    }

    public Long createlocation(StAppLocationDto locationDto) {
        StAppLocationDto locationToSave = new StAppLocationDto();
        locationToSave.setTypeLocation(locationDto.getTypeLocation());
        locationToSave.setLocationName(locationDto.getLocationName());
        locationToSave.setSellsFood(locationDto.isSellsFood());
        locationToSave.setSellsDrinks(locationDto.isSellsDrinks());
        locationToSave.setDescription(locationDto.getDescription());
        locationToSave.setRatings(new ArrayList<>());
        locationToSave.setReviewAmount(0);
        locationToSave.setReviewAverage(0);
        locationToSave.setClosingTime(locationDto.getClosingTime());
        locationToSave.setOpeningTime(locationDto.getOpeningTime());
        locationToSave.setCity(locationDto.getCity());
        locationToSave.setLatitude(locationDto.getLatitude());
        locationToSave.setLongitude(locationDto.getLongitude());
        locationToSave.setOwner(locationDto.getOwner());
        for(Tag tag : locationDto.getTags()){
            if(!tagRepository.existsById(tag.getTag())){
                tagRepository.save(new Tag(tag.getTag()));
            }
        }
        locationToSave.setTags(locationDto.getTags());
        StAppLocation newLocation = locationRepository.save(toStAppLocation(locationToSave));
        return newLocation.getLocationId();
    }

    public StAppLocation toStAppLocation(StAppLocationDto locationDto) {

        var location = new StAppLocation();

        location.setCity(locationDto.getCity());
        location.setLocationName(locationDto.getLocationName());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        location.setOwner(userService.toUser(userService.getUser(locationDto.getOwner()))); //deze gaat fout (Niet Meer!!)
        location.setTypeLocation(locationDto.getTypeLocation());
        location.setOpeningTime(locationDto.getOpeningTime());
        location.setClosingTime(locationDto.getClosingTime());
        location.setSellsFood(locationDto.isSellsFood());
        location.setSellsDrinks(locationDto.isSellsDrinks());
        location.setDescription(locationDto.getDescription());
        location.setReviewAmount(locationDto.getReviewAmount());
        location.setReviewAverage(locationDto.getReviewAverage());
        location.setLocationId(locationDto.getLocationId());
        location.setRatings(locationDto.getRatings());
        location.setFavoredBy(new ArrayList<>());
        location.setTags(locationDto.getTags());
        location.setPictures(locationDto.getPictures());

        return location;
    }

    public int updateRatings(RatingRequestDto rating){
        double ratingSum = 0;
        int ratingAmount = 0;
        for(Rating locationRating : getById(rating.getLocationId()).getRatings()){
            ratingAmount++;
            ratingSum = ratingSum + locationRating.getRating();
        }
        if(ratingAmount == 0){
            ratingAmount++;
        }
        double ratingAverage = ratingSum / ratingAmount;
        StAppLocation location = locationRepository.findByLocationId(rating.getLocationId());
        location.setReviewAmount(ratingAmount);
        location.setReviewAverage(ratingAverage);
        StAppLocation stAppLocation = locationRepository.save(location);

        return stAppLocation.getReviewAmount();
    }

    public void removeRating(StAppLocation location, Rating rating){
        location.removeRating(rating);
        locationRepository.save(location);
    }

    //De belangrijkste methode in de app, hoofdfunctionaliteit.
    public List<StAppLocationDto> getRecommendations(RequestDto requestData){
        List<StAppLocationDto> recommendations = new ArrayList<>();
        UserDto targetUser = userService.getUser(requestData.getUsername());
        List<StAppLocationDto> availableLocations = getLocationsInCity(requestData.getCity());
        List<Rating> ratings = ratingRepository.findByGebruiker(userService.toUser(userService.getUser(requestData.getUsername())));
        List<StAppLocation> favorites = userService.getUser(requestData.getUsername()).getFavorites();

        for (StAppLocationDto location : availableLocations){

            //berekeningen voor de score wat betreft de sluitingstijden
            int tijdMinuten = Integer.parseInt(java.time.LocalTime.now().toString().substring(0,2)) * 60 + Integer.parseInt(java.time.LocalTime.now().toString().substring(3,5));
            double heel = location.getClosingTime() - location.getOpeningTime();
            double deel = location.getClosingTime() - tijdMinuten;
            double deelDoorHeel = deel / heel;
            double timeScore = 0;
            if(deelDoorHeel < 1){
                timeScore = Math.sqrt((-100 * (deelDoorHeel)) + 100) * Math.sqrt((deelDoorHeel) * 100) * 0.2;
            }


            //berekeningen voor de score wat betreft de afstand van de gebruiker tot de locatie
            double lonUser = Math.toRadians(requestData.getLongitude());
            double latUser = Math.toRadians(requestData.getLatitude());
            double lonLocation = Math.toRadians(location.getLongitude());
            double latLocation = Math.toRadians(location.getLatitude());

            double distanceLatitude = latLocation - latUser;
            double distanceLongitude = lonLocation - lonUser;

            double distanceInKm = (2 * Math.asin(Math.sqrt(Math.pow(Math.sin(distanceLatitude / 2),2) + Math.cos(latUser) * Math.cos(latLocation) * Math.pow(Math.sin(distanceLongitude / 2),2)))) * 6371;
            double distanceScore = 5 - distanceInKm;

            location.setDistance((double) Math.round(distanceInKm * 100) / 100);

            //berekeningen voor de score wat betreft de beoordeling van de gebruiker
            //en de gemiddelde beoordeling van de locatie
            float userRating = 0;
            for (Rating rating: ratings){
                if (rating.getLocatie().getLocationName().equals(location.getLocationName())){
                    userRating = rating.getRating();
                }
            }
            double reviewScore = location.getReviewAverage();
            if (userRating != 0){reviewScore = (reviewScore * 2 + userRating) / 3;}

            //als de gebruiker de locatie heeft opgeslagen als favoriet krijgt deze een aanbevelingsbonus.
            float favoriteScore = 0;
            for (StAppLocation favoriteLocation : favorites){
                if (fromStAppLocation(favoriteLocation).getLocationId() == location.getLocationId()){
                    favoriteScore += 5;
                }
            }

            //als de gebruiker aangeeft dat deze honger heeft en de stApp locatie serveert eten, krijgt deze een kleine bonus
            float hungerScore = 0;
            if (requestData.isHeeftHonger() && location.isSellsFood()){
                hungerScore += 3;
            }else if (requestData.isHeeftHonger() && !location.isSellsFood()){
                hungerScore -= 3;
            }

            //hetzelfde geldt voor dorst
            float thirstScore = 0;
            if (requestData.isHeeftDorst() && location.isSellsDrinks()){
                thirstScore += 3;
            }else if (requestData.isHeeftDorst() && !location.isSellsDrinks()){
                thirstScore -= 3;
            }

            location.setRecommendationScore(timeScore * (distanceScore + reviewScore + favoriteScore + hungerScore + thirstScore));
            recommendations.add(location);
        }

        return recommendations;
    }

    public void addRating(Rating rating) {
        StAppLocation location = locationRepository.findByLocationId(rating.getLocatie().getLocationId());
        location.addRating(rating);
        locationRepository.save(location);
    }

    public List<StAppLocationDto> getFavorites(String userName) {
        User user = userService.toUser(userService.getUser(userName));
        List<StAppLocationDto> returnLocations = new ArrayList<>();
        for(StAppLocation favorite : user.getFavorites()){
            returnLocations.add(fromStAppLocation(favorite));
        }
        return returnLocations;
    }

    public List<StAppLocationDto> getOwned(String username) {
        User user = userService.toUser(userService.getUser(username));
        List<StAppLocationDto> returnLocations = new ArrayList<>();
        for(StAppLocation favorite : user.getOwnedLocation()){
            returnLocations.add(fromStAppLocation(favorite));
        }
        return returnLocations;
    }

    public boolean deleteLocation(Long locationId, String username) {
        StAppLocation locationToDelete = toStAppLocation(getById(locationId));
        if(locationToDelete.getOwner().getUsername().equalsIgnoreCase(username)){
            locationRepository.delete(locationToDelete);
            return true;
        }else{
            return false;
        }
    }

    public void addPicture(PictureDetails details, Long locationId) {
        StAppLocation location = locationRepository.findByLocationId(locationId);
        location.addPicture(details);
        locationRepository.save(location);
    }

    @PostConstruct
    public void hardcodeLocations(){
        createlocation(new StAppLocationDto(
                1L,
                "Amsterdam",
                "Club Up",
                52.363800252511375,
                4.883625525271591,
                "Club",
                660,
                1380,
                false,
                true,
                "Een gezellige club bij leidseplein. Altijd goede muziek en leuke mensen. Kom clubben!",
                0,
                0,
                "Johnny",
                "Cocktails",
                "DJ-muziek",
                "Dansen",
                "High-end"
        ));
        createlocation(new StAppLocationDto(
                2L,
                "Amsterdam",
                "Pathe Tuschinski",
                52.36653558052355,
                4.894682885437269,
                "Bioscoop",
                600,
                1320,
                true,
                true,
                "De oudste bios in amsterdam",
                0,
                0,
                "Johnny",
                "Klassiek",
                "Cocktails",
                "Dansen",
                "High-end"
        ));
        createlocation(new StAppLocationDto(
                3L,
                "Amsterdam",
                "The Bulldog The First Coffeeshop",
                52.37366690191021,
                4.897717139666394,
                "coffeeshop",
                480,
                1440,
                false,
                false,
                "Een goede en oude koffieshop.",
                0,
                0,
                "Johnny",
                "Coffee shop",
                "Koffie",
                "Wiet",
                "Terras"
        ));
        createlocation(new StAppLocationDto(
                4L,
                "Amsterdam",
                "Molly Malone's Irish Pub",
                52.37604348478926,
                4.901255983292625,
                "Kroeg",
                900,
                1440,
                true,
                true,
                "Een lekkere en gezellige Irish pub",
                0,
                0,
                "Johnny",
                "Iers",
                "Gezellig",
                "Lekker eten",
                "Terras"
        ));
    }
}
