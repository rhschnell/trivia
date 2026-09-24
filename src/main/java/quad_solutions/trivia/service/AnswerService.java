package quad_solutions.trivia.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AnswerService {

    public final Map<UUID, String> correctAnswers = new HashMap<>();

    public void store(UUID id, String answer) {
        correctAnswers.put(id, answer);
    }

    public String getAnswer(UUID id) {
        return correctAnswers.get(id);
    }
}
