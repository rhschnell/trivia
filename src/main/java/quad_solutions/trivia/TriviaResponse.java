package quad_solutions.trivia;

import java.util.List;

public record TriviaResponse(int response_code, List<Question> results) {
    public record Question(
            String category,
            String type,
            String difficulty,
            String question,
            String correct_answer,
            List<String> incorrect_answers
    ) {
    }
}
