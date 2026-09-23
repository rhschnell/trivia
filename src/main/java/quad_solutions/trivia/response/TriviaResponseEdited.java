package quad_solutions.trivia.response;

import java.util.List;

public record TriviaResponseEdited (List<EditedQuestion> results) {
        public record EditedQuestion(
                String category,
                String type,
                String difficulty,
                String question,
                List<String> answers
        ) {
        }
}
