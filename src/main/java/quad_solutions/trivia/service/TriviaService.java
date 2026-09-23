package quad_solutions.trivia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import quad_solutions.trivia.response.TriviaResponse;
import quad_solutions.trivia.response.TriviaResponse.Question;
import quad_solutions.trivia.response.TriviaResponseEdited.EditedQuestion;
import quad_solutions.trivia.response.TriviaResponseEdited;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TriviaService {
    private final RestClient restClient;

    public TriviaService(RestClient restClient) {
        this.restClient = restClient;
    }

    // Method to get n questions, without category, difficulty etc
    public TriviaResponseEdited getQuestions(int amount) {
        TriviaResponse question = restClient.
                get()
                .uri("/api.php?amount={amount}", amount)
                .retrieve()
                .body(TriviaResponse.class);
        TriviaResponseEdited result = new TriviaResponseEdited(question.results().stream().map(this::transformQuestion).toList());

        return result;

    }

    public EditedQuestion transformQuestion(Question question) {
        List<String> answers = new ArrayList<>(question.incorrect_answers());
        answers.add(question.correct_answer());
        Collections.shuffle(answers);

        return new EditedQuestion(
                question.category(),
                question.type(),
                question.difficulty(),
                question.question(),
                answers
        );
    }
}
