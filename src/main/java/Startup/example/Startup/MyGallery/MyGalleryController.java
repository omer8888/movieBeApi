package Startup.example.Startup.MyGallery;

import Startup.example.Startup.AwsS3.S3Service;
import Startup.example.Startup.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/MyGallery")
@RestController
public class MyGalleryController {

    @Autowired
    S3Service s3Service;

    @GetMapping("/GetImagesUrls/{AccountId}")
    private ResponseEntity<List<String>> fetchImagesUrlsByAccountId(@PathVariable String AccountId) {
        return new ResponseEntity<>(s3Service.fetchImagesUrlsByAccountId(AccountId), HttpStatus.OK);
    }

}