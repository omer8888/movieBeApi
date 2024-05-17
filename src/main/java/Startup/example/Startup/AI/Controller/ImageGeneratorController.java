package Startup.example.Startup.AI.Controller;

import Startup.example.Startup.AI.ImageRequest;
import Startup.example.Startup.AI.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/ai")
public class ImageGeneratorController {

    @Autowired
    private RestTemplate restTemplate;


    private static final String OPEN_AI_URL = "https://api.openai.com/v1/images/generations";

    @PostMapping("/generate-image")
    public String generateImage(@RequestBody ImageRequest imageRequest) {
        ImageResponse imageResponse = restTemplate.postForObject(OPEN_AI_URL, imageRequest, ImageResponse.class);
        if (imageResponse != null) {
            return imageResponse.getData().get(0).get("url");
        }
        return null;
    }


}
