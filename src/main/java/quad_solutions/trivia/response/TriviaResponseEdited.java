package quad_solutions.trivia.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public record TriviaResponseEdited (List<EditedQuestion> results) {
        public record EditedQuestion(
                String category,
                String type,
                String difficulty,
                String question,
                List<String> answers,
                @JsonIgnore String correctAnswer,
                UUID id
        ) {
        }
}
