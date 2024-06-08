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

    @PostMapping("/generate-image-by-prompt")
    public String generateImage(@RequestParam String prompt, @RequestParam String bodypart, @RequestParam String colorStyle, @RequestParam String gender) {

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
            return imageResponse.getData().get(0).get("url");
        }

        return null;
    }


}
