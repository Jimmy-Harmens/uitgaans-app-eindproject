package com.example.stapp.services;

import com.example.stapp.models.PictureDetails;
import com.example.stapp.models.StAppLocation;
import com.example.stapp.repositories.PhotoDetailsRepository;
import com.example.stapp.repositories.StAppLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PictureService {
    @Autowired
    private PhotoDetailsRepository photoDetailsRepository;

    @Autowired
    private StAppLocationRepository locationRepository;

    @Value("${my.upload_location}")
    private Path fileStoragePath;

    public PictureDetails saveLocationPicture(MultipartFile file, String url, String roleInApp, Long locationId){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }

        StAppLocation location = locationRepository.findByLocationId(locationId);

        PictureDetails details = new PictureDetails(fileName, file.getContentType(), url, roleInApp, location);

        photoDetailsRepository.save(details);

        return details;
    }

    public PictureDetails saveUserPicture(MultipartFile file, String url, String roleInApp){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }

        PictureDetails details = new PictureDetails(fileName, file.getContentType(), url, roleInApp);

        photoDetailsRepository.save(details);

        return details;
    }

    public Resource downLoadFile(String fileName) {

        Path path = Paths.get(String.valueOf(fileStoragePath)).toAbsolutePath().resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

}
