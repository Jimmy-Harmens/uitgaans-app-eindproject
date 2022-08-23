package com.example.stapp.repositories;

import com.example.stapp.models.Favorite;
import com.example.stapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorite, String> {
    List<Favorite> findByGebruiker(User gebruiker);
}
