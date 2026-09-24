package quad_solutions.trivia.controller;

import org.springframework.web.bind.annotation.*;
import quad_solutions.trivia.response.TriviaResponse;
import quad_solutions.trivia.response.TriviaResponseEdited;
import quad_solutions.trivia.service.AnswerService;
import quad_solutions.trivia.service.TriviaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
public class TriviaController {
    private final TriviaService triviaService;
    private final AnswerService answerService;

    public TriviaController(TriviaService triviaService, AnswerService answerService) {
        this.triviaService = triviaService;
        this.answerService = answerService;
    }

//    @GetMapping("/get-questions")
//    public TriviaResponseEdited getFiveQuestions() {
//        return triviaService.getQuestions(5);
//    }

    @GetMapping("/get-questions")
    public TriviaResponseEdited getQuestions(
            @RequestParam(defaultValue = "5") int amount,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String type) {
        return triviaService.getQuestions(amount, category, difficulty, type);
    }

    @PostMapping("/checkanswers")
    public Map<UUID, String> getAnswers(@RequestBody List<UUID> questionIDs) {
        Map<UUID, String> result = new HashMap<>();
        for (UUID id : questionIDs) {
            result.put(id, answerService.getAnswer(id));
        }
        return result;
    }
}
