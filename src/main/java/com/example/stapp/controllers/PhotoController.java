package com.example.stapp.controllers;

import com.example.stapp.models.PictureDetails;
import com.example.stapp.services.PictureService;
import com.example.stapp.services.StAppLocationService;
import com.example.stapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("")
public class PhotoController {
    Base64.Decoder decoder = Base64.getUrlDecoder();

    @Autowired
    PictureService pictureService;

    @Autowired
    StAppLocationService locationService;

    @Autowired
    UserService userService;

    @PostMapping("/picture/location")
    public ResponseEntity<Object> addLocationPicture(@RequestParam Long locationId,
                                             @RequestParam String roleInApp,
                                             @RequestBody MultipartFile file) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        PictureDetails details = pictureService.saveLocationPicture(file, url, roleInApp, locationId);

        locationService.addPicture(details, locationId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/picture/user")
    public ResponseEntity<Object> addProfilePicture(@RequestBody MultipartFile file,
                                                    @RequestHeader String Authorization) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String username ="";
        String[] chunks = Authorization.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        String[] fields = payload.split(",");
        for(String field : fields){
            if(field.contains("sub")){
                username = field.split(":")[1];
                username = username.substring(1,username.length()-1);
                break;
            }
        }
        PictureDetails details = pictureService.saveUserPicture(file, url, "avatar");

        userService.addProfilePicture(details, username);

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = pictureService.downLoadFile(fileName);

//        this mediaType decides which type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types
        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }
}
