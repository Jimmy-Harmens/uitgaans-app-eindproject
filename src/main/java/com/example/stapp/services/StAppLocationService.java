package com.example.stapp.services;

import com.example.stapp.dtos.RequestDto;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.dtos.UserDto;
import com.example.stapp.models.Favorite;
import com.example.stapp.models.Rating;
import com.example.stapp.models.StAppLocation;
import com.example.stapp.models.User;
import com.example.stapp.repositories.FavoritesRepository;
import com.example.stapp.repositories.RatingRepository;
import com.example.stapp.repositories.StAppLocationRepository;
import com.example.stapp.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    public StAppLocationDto getById(long id){
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

        return dto;
    }

    public String createlocation(StAppLocationDto locationDto) {
        StAppLocation newLocation = locationRepository.save(toStAppLocation(locationDto));
        return newLocation.getLocationName();
    }

    public StAppLocation toStAppLocation(StAppLocationDto locationDto) {

        var location = new StAppLocation();

        location.setCity(locationDto.getCity());
        location.setLocationName(locationDto.getLocationName());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        location.setOwner(userService.toUser(userService.getUser(locationDto.getOwner()))); //deze gaat fout
        location.setTypeLocation(locationDto.getTypeLocation());
        location.setOpeningTime(locationDto.getOpeningTime());
        location.setClosingTime(locationDto.getClosingTime());
        location.setSellsFood(locationDto.isSellsFood());
        location.setSellsDrinks(locationDto.isSellsDrinks());
        location.setDescription(locationDto.getDescription());
        location.setReviewAmount(locationDto.getReviewAmount());
        location.setReviewAverage(locationDto.getReviewAverage());
        location.setRatings(new HashSet<>());
        location.setFavoredBy(new HashSet<>());

        return location;
    }

    public List<StAppLocationDto>     getRecommendations(RequestDto requestData){
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
}
