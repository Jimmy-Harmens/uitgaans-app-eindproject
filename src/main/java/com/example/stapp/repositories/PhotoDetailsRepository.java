package com.example.stapp.repositories;

import com.example.stapp.models.PictureDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoDetailsRepository extends JpaRepository<PictureDetails, String> {
}
