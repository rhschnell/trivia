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
import java.util.UUID;

@Service
public class TriviaService {
    private final RestClient restClient;
    private final AnswerService answerService;

    public TriviaService(RestClient restClient, AnswerService answerService) {
        this.restClient = restClient;
        this.answerService = answerService;
    }

    // Method to get n questions, without category, difficulty etc
    public TriviaResponseEdited getQuestions(int amount, String category, String difficulty, String type) {
        StringBuilder url = new StringBuilder("/api.php?amount=" + amount);

        if (category != null && !category.equals("any")) {
            url.append("&category=").append(category);
        }
        if (difficulty != null && !difficulty.equals("any")) {
            url.append("&difficulty=").append(difficulty);
        }
        System.out.println(type);
        if (type != null && !type.equals("any")) {
            url.append("&type=").append(type);
        }

        System.out.println(url);
        TriviaResponse question = restClient.
                get()
                .uri(String.valueOf(url))
                .retrieve()
                .body(TriviaResponse.class);
        TriviaResponseEdited result = new TriviaResponseEdited(question.results().stream().map(this::transformQuestion).toList());

        return result;

    }

    public EditedQuestion transformQuestion(Question question) {
        List<String> answers = new ArrayList<>(question.incorrect_answers());
        answers.add(question.correct_answer());
        Collections.shuffle(answers);

        UUID id = UUID.randomUUID();
        answerService.store(id, question.correct_answer());

        return new EditedQuestion(
                question.category(),
                question.type(),
                question.difficulty(),
                question.question(),
                answers,
                question.correct_answer(),
                id
        );
    }
}
