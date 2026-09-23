package quad_solutions.trivia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import quad_solutions.trivia.TriviaResponse;
import quad_solutions.trivia.TriviaApplication;
import quad_solutions.trivia.service.TriviaService;

@RestController
public class TriviaController {
    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @GetMapping("/get-questions")
    public TriviaResponse getFiveQuestions() {
        return triviaService.getQuestions(5);
    }
}
