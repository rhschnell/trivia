package quad_solutions.trivia.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import quad_solutions.trivia.response.TriviaResponse;
import quad_solutions.trivia.response.TriviaResponse.Question;
import quad_solutions.trivia.response.TriviaResponseEdited;
import quad_solutions.trivia.response.TriviaResponseEdited.EditedQuestion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TriviaServiceTest {
    @Mock
    private RestClient restClient;

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private TriviaService triviaService;

    private void mockRestClientResponse(TriviaResponse response) {
        RestClient.RequestHeadersUriSpec uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headersSpec = mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(TriviaResponse.class)).thenReturn(response);
    }

    @Test
    void testGetQuestionsSuccess() {
        Question question = new TriviaResponse.Question(
             "General Knowledge",
             "any",
             "medium",
             "Test question",
             "Correct",
             List.of("Incorrect1", "Incorrect2", "Incorrect3")
        );

        TriviaResponse mockResponse = new TriviaResponse(0, List.of(question));

        mockRestClientResponse(mockResponse);

        TriviaResponseEdited response = triviaService.getQuestions(1, "11", "medium", "any");

        assertNotNull(response);
        assertEquals(1, response.results().size());
        verify(answerService, times(1)).store(any(), eq("Correct"));
    }

    @Test
    void testGetQuestionsThrowsResponseCode1() {
        TriviaResponse mockResponse = new TriviaResponse(1, List.of());
        mockRestClientResponse(mockResponse);

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
            () -> triviaService.getQuestions(10, "10", "any", "any"));

        assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        assertEquals("There werent enough questions available with these criteria. Try changing some parameters", e.getReason());
    }

    @Test
    void testTransformQuestionSuccess() {
        Question question = new TriviaResponse.Question(
                "General Knowledge",
                "any",
                "medium",
                "Test question",
                "Correct",
                List.of("Incorrect1", "Incorrect2", "Incorrect3")
        );

        EditedQuestion result = triviaService.transformQuestion(question);

        assertEquals("General Knowledge", result.category());
        assertEquals("Correct", result.correctAnswer());
        assertEquals(4, result.answers().size());
        verify(answerService).store(any(), eq("Correct"));
    }
}
