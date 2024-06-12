package Startup.example.Startup.AI.Controller;

import Startup.example.Startup.AI.ImageRequest;
import Startup.example.Startup.AI.ImageResponse;
import Startup.example.Startup.AwsS3.S3Service;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/ai")
public class ImageGeneratorController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private S3Service s3Service;

    private static final String OPEN_AI_URL = "https://api.openai.com/v1/images/generations";

    @PostMapping("/generate-image")
    public String generateImage(@RequestBody ImageRequest imageRequest) {
        ImageResponse imageResponse = restTemplate.postForObject(OPEN_AI_URL, imageRequest, ImageResponse.class);
        if (imageResponse != null) {
            return imageResponse.getData().get(0).get("url");
        }
        return null;
    }

    @PostMapping("/generate-image-by-prompt")
    public ResponseEntity<String> generateImage(@RequestParam String prompt, @RequestParam String bodypart, @RequestParam String colorStyle, @RequestParam String gender) {

        String genderText = bodypart==null?"":"my gender is : "+gender;
        String colorStyleText = colorStyle==null?"":"make sure the tattoo color is : "+colorStyle;
        String bodypartText = bodypart==null?"":"make sure the tattoo placed on the body part which is :"+bodypart;

        ImageRequest imageRequest = new ImageRequest("dall-e-3", null, 1, "1024x1024");
        imageRequest.setPrompt("Generate a highly realistic tattoo of "
                + prompt
                + bodypartText
                + colorStyleText
                + genderText
                + " The design should be suitable for a professional tattoo,"
                + " Ensure the tattoo has intricate details and shading to enhance the realism and lifelike appearance,"
                + " Always add a nice and very small watermark with the company name 'Made by Omer AI TATTOO' on the bottom left of the generated image."
                + "The watermark should be in black and say 'Made by Omer AI TATTOO'.");

        ImageResponse imageResponse = restTemplate.postForObject(OPEN_AI_URL, imageRequest, ImageResponse.class);

        if (imageResponse != null && !imageResponse.getData().isEmpty()) {
            String imageUrl = imageResponse.getData().get(0).get("url");
            return uploadImageToS3Bucket(imageUrl);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate image");
    }


    private ResponseEntity<String> uploadImageToS3Bucket(String imageUrl) {
        try {
            byte[] imageBytes = IOUtils.toByteArray(new URL(imageUrl));

            // Create a temporary file
            File tempFile = File.createTempFile("temp", ".png");
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(imageBytes);
            }

            // Upload the file to S3 and get the URL
            String s3Url = "https://ai-tattoo-s3-bucket.s3.amazonaws.com/" + s3Service.uploadFile("123123123", tempFile);

            // Delete the temporary file
            tempFile.delete();

            // Return the S3 URL
            return new ResponseEntity<>(s3Url, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download and upload image");
        }
    }
}
