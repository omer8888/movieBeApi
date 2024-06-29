package Startup.example.Startup.AI.Controller;

import Startup.example.Startup.AI.ImageRequest;
import Startup.example.Startup.AI.ImageResponse;
import Startup.example.Startup.AwsS3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    //TODO: move logic to service
    @PostMapping("/generate-image-by-prompt")
    public ResponseEntity<String> generateImage(@RequestParam String isLoggedIn,
                                                @RequestParam String prompt,
                                                @RequestParam String bodypart,
                                                @RequestParam String colorStyle,
                                                @RequestParam String gender) {

        String genderText = gender == null ? "" : "my gender is : " + gender;
        String colorStyleText = colorStyle == null ? "" : "make sure the tattoo color is : " + colorStyle;
        String bodypartText =
                (bodypart == null || bodypart == "sketch") ?
                        "tattoo show be a professional sketch" : "make sure the tattoo is placed on the " + bodypart;

        ImageRequest imageRequest = new ImageRequest("dall-e-3", null, 1, "1024x1024");
        imageRequest.setPrompt(""
                + prompt
                + bodypartText
                + colorStyleText
                + genderText
                + " Realistic tattoo design generator, intricate and detailed ink work, feels like real ink tattoo and not image, with red near the tattoo like its still fresh made, feels like real ink tattoo and not image,, ultra-detailed, realistic ink work, monochromatic, artistic, creative, intricate linework, precise shading, captivating designs, professional, inviting ambiance, high quality, intricate details, sleek and modern aesthetic"
                + " Always mall size watermark bottom right: with the company name 'Made by Omer AI TATTOO' on the bottom left of the generated tattoo."
                + "The watermark should be in black and say 'Made by Omer AI TATTOO'.");

        ImageResponse imageResponse = restTemplate.postForObject(OPEN_AI_URL, imageRequest, ImageResponse.class);

        if (imageResponse != null && !imageResponse.getData().isEmpty()) {
            String imageUrl = imageResponse.getData().get(0).get("url");
            String S3url = s3Service.uploadImageToS3Bucket(String.valueOf(isLoggedIn), imageUrl);
            if (S3url != null) {
                return new ResponseEntity<>(S3url, HttpStatus.OK);
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate image");
    }

}
