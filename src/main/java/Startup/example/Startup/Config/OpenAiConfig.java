package Startup.example.Startup.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class OpenAiConfig {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String OPENAI_KEY = dotenv.get("OPENAI_KEY");

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + OpenAiConfig.OPENAI_KEY);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

}
