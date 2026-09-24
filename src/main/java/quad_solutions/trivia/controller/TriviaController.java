package quad_solutions.trivia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quad_solutions.trivia.response.TriviaResponse;
import quad_solutions.trivia.response.TriviaResponseEdited;
import quad_solutions.trivia.service.TriviaService;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
public class TriviaController {
    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

//    @GetMapping("/get-questions")
//    public TriviaResponseEdited getFiveQuestions() {
//        return triviaService.getQuestions(5);
//    }

    @GetMapping("/get-questions")
    public TriviaResponseEdited getQuestions(@RequestParam(defaultValue = "5") int amount) {
        return triviaService.getQuestions(amount);
    }
}
