package com.example.stapp.services;

import com.example.stapp.StAppApplication;
import com.example.stapp.dtos.RatingRequestDto;
import com.example.stapp.dtos.StAppLocationDto;
import com.example.stapp.models.Rating;
import com.example.stapp.models.StAppLocation;
import com.example.stapp.models.Tag;
import com.example.stapp.models.User;
import com.example.stapp.repositories.*;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class StAppLocationServiceTest {

    @Mock
    private StAppLocationRepository locationRepository;
    @Mock
    private UserService userService;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private FavoritesRepository favoritesRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StAppLocationService stAppLocationService;

    @Test
    void getById() {
        StAppLocation stAppLocation= new StAppLocation(
                1L,
                "Amsterdam",
                "Lego land",
                52.37166206406551,
                4.8952483363809405,
                "Club",
                480,
                1200,
                true,
                false,
                "Een leuke plek",
                69,
                4.2,
                "Jimmy"
                );

        when(locationRepository.findByLocationId(1L)).thenReturn(stAppLocation);

        StAppLocationDto stAppLocation1 = stAppLocationService.getById(1L);

        assertEquals(stAppLocation.getLocationId(), stAppLocation1.getLocationId());
        assertEquals(stAppLocation.getLocationName(), stAppLocation1.getLocationName());
        assertEquals(stAppLocation.getOwner().getUsername(), stAppLocation1.getOwner());
        assertEquals(stAppLocation.getLatitude(), stAppLocation1.getLatitude());
    }

    @Test
    void getLocationsInCity() {
        List<StAppLocation> citiesInAmsterdam = new ArrayList<>();
        StAppLocation stAppLocation= new StAppLocation(
                1L,
                "Amsterdam",
                "Lego land",
                52.37166206406551,
                4.8952483363809405,
                "Club",
                480,
                1200,
                true,
                false,
                "Een leuke plek",
                69,
                4.2,
                "Jimmy"
        );
        citiesInAmsterdam.add(stAppLocation);
        when(locationRepository.findByCity("Amsterdam")).thenReturn(citiesInAmsterdam);

        List<StAppLocationDto> result = stAppLocationService.getLocationsInCity("Amsterdam");

        assertEquals(result.get(0).getLocationName(), stAppLocation.getLocationName());
    }

    @Test
    void fromStAppLocation() {
        StAppLocation stAppLocation= new StAppLocation(
                1L,
                "Amsterdam",
                "Lego land",
                52.37166206406551,
                4.8952483363809405,
                "Club",
                480,
                1200,
                true,
                false,
                "Een leuke plek",
                69,
                4.2,
                "Jimmy"
        );

        StAppLocationDto locationDto = stAppLocationService.fromStAppLocation(stAppLocation);

        assertEquals(locationDto.getLocationName(), stAppLocation.getLocationName());
    }

//    @Test
//    void createlocation() {
//        StAppLocationDto locationDto = new StAppLocationDto(
//                4L,
//                "Amsterdam",
//                "Lego land",
//                52.37166206406551,
//                4.8952483363809405,
//                "Club",
//                480,
//                1200,
//                true,
//                false,
//                "Een leuke plek",
//                69,
//                4.2,
//                "Jimmy"
//        );
//        locationDto.setTags(new ArrayList<>());
//        StAppLocationDto locationToSave = new StAppLocationDto();
//        locationToSave.setTypeLocation(locationDto.getTypeLocation());
//        locationToSave.setLocationName(locationDto.getLocationName());
//        locationToSave.setSellsFood(locationDto.isSellsFood());
//        locationToSave.setSellsDrinks(locationDto.isSellsDrinks());
//        locationToSave.setDescription(locationDto.getDescription());
//        locationToSave.setRatings(new ArrayList<>());
//        locationToSave.setReviewAmount(0);
//        locationToSave.setReviewAverage(0);
//        locationToSave.setClosingTime(locationDto.getClosingTime());
//        locationToSave.setOpeningTime(locationDto.getOpeningTime());
//        locationToSave.setCity(locationDto.getCity());
//        locationToSave.setLatitude(locationDto.getLatitude());
//        locationToSave.setLongitude(locationDto.getLongitude());
//        locationToSave.setOwner(locationDto.getOwner());
//        for(Tag tag : locationDto.getTags()){
//            if(!tagRepository.existsById(tag.getTag())){
//                tagRepository.save(new Tag(tag.getTag()));
//            }
//        }
//
//        when(locationRepository.save(stAppLocationService.toStAppLocation(locationToSave))).thenReturn(stAppLocationService.toStAppLocation(locationToSave));
//
//        Long expectedId = 4L;
//        Long actualId = stAppLocationService.createlocation(locationDto);
//
//        assertEquals(expectedId, actualId);
//    }

    @Test
    void toStAppLocation() {
        StAppLocationDto locationDto = new StAppLocationDto(
                4L,
                "Amsterdam",
                "Lego land",
                52.37166206406551,
                4.8952483363809405,
                "Club",
                480,
                1200,
                true,
                false,
                "Een leuke plek",
                69,
                4.2,
                "Jimmy"
        );

//        when(userRepository.findById("Jimmy")).thenReturn(java.util.Optional.of(new User("Jimmy")));

        String expected = locationDto.getLocationName();
        String actual = stAppLocationService.toStAppLocation(locationDto).getLocationName();

        assertEquals(expected, actual);
    }

//    @Test
//    void updateRatings() {
//        StAppLocation stAppLocation= new StAppLocation(
//                1L,
//                "Amsterdam",
//                "Lego land",
//                52.37166206406551,
//                4.8952483363809405,
//                "Club",
//                480,
//                1200,
//                true,
//                false,
//                "Een leuke plek",
//                69,
//                4.2,
//                "Jimmy"
//        );
//        RatingRequestDto ratingRequestDto = new RatingRequestDto();
//
//        when(stAppLocationService.updateRatings(ratingRequestDto)).thenReturn(70);
//
//        int expected = 70;
//        int actual = stAppLocationService.updateRatings(ratingRequestDto);
//
//        assertEquals(expected, actual);
//    }

//    @Test
//    void removeRating() {
//        StAppLocation stAppLocation = new StAppLocation(
//                1L,
//                "Amsterdam",
//                "Lego land",
//                52.37166206406551,
//                4.8952483363809405,
//                "Club",
//                480,
//                1200,
//                true,
//                false,
//                "Een leuke plek",
//                69,
//                4.2,
//                "Jimmy"
//        );
//        Rating rating = new Rating("Einstein", 1L, 4);
//        stAppLocationService.addRating(rating);
//    }

    @Test
    void getRecommendations() {
    }

    @Test
    void addRating() {
    }

    @Test
    void getFavorites() {
    }

    @Test
    void getOwned() {
    }

    @Test
    void deleteLocation() {
    }

    @Test
    void addPicture() {
    }
}