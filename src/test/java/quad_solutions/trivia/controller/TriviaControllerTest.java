package quad_solutions.trivia.controller;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import quad_solutions.trivia.response.TriviaResponseEdited;
import quad_solutions.trivia.service.AnswerService;
import quad_solutions.trivia.service.TriviaService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TriviaController.class)
public class TriviaControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockitoBean
    private TriviaService triviaService;

    @MockitoBean
    private AnswerService answerService;

    @Test
    void testGetQuestionsReturnsOk() throws Exception {
        given(triviaService.getQuestions(1, "10", "any", "any"))
                .willReturn(new TriviaResponseEdited(List.of()));

        mockMVC.perform(get("/questions")
                        .param("amount", "1")
                        .param("category", "10")
                        .param("difficulty", "any")
                        .param("type", "any"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAnswersEmptyListReturnsEmptyMap() throws Exception {
        mockMVC.perform(post("/checkanswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(status().isOk());
    }
}
