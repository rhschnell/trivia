package quad_solutions.trivia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import quad_solutions.trivia.TriviaResponse;

@Service
public class TriviaService {
    private final RestClient restClient;

    public TriviaService(RestClient restClient) {
        this.restClient = restClient;
    }

    // Method to get n questions, without category, difficulty etc
    public TriviaResponse getQuestions(int amount) {
        return restClient.
                get()
                .uri("/api.php?amount={amount}", amount)
                .retrieve()
                .body(TriviaResponse.class);
    }
}
