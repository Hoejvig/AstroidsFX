package app;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ScoreClient {

    private static final String BASE_URL = "http://localhost:8081/api/score";

    private final RestTemplate restTemplate;

    public ScoreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getScore() {
        try {
            String score = restTemplate.getForObject(BASE_URL, String.class);
            return score != null ? Integer.parseInt(score.trim()) : 0;
        } catch (RestClientException | NumberFormatException exception) {
            return 0;
        }
    }

    public void reset() {
        try {
            restTemplate.postForObject(BASE_URL + "/reset", null, Void.class);
        } catch (RestClientException ignored) {
        }
    }
}