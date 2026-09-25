package quad_solutions.trivia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnswerServiceTest {
    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        answerService = new AnswerService();
    }

    @Test
    void testStoreAnswerSuccess() {
        UUID questionID = UUID.randomUUID();
        String answer = "Correct";

        answerService.store(questionID, answer);
        String foundAnswer = answerService.getAnswer(questionID);

        assertEquals(answer, foundAnswer);
    }

    @Test
    void testGetAnswerIdNotFound () {
        UUID questionID = UUID.randomUUID();
        String answer = answerService.getAnswer(questionID);

        assertNull(answer);
    }
}
