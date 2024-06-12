package Startup.example.Startup.images;

import Startup.example.Startup.AwsS3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/image")
@RestController
public class ImageController {


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

    @PostMapping("/image-to-base64")
    public ResponseEntity<String> imageToBase64(@RequestBody String imageUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
            if (imageBytes == null) {
                return ResponseEntity.status(404).body("Image not found");
            }
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String mimeType = "image/png"; // Adjust this if needed
            String base64ImageString = "data:" + mimeType + ";base64," + base64Image;
            return ResponseEntity.ok(base64ImageString);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to fetch image: " + e.getMessage());
        }
    }

}