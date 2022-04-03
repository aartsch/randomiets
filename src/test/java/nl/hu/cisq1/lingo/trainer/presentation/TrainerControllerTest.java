package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @Test
    @DisplayName("start a new game")
    void startNewGame() throws Exception {
        when(wordService.provideRandomWord(5))
                .thenReturn("groep");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.status", is("PLAYING")))
//                .andExpect(jsonPath("$.feedback", hasSize(0)))
                .andExpect(jsonPath("$.hint", hasLength(5)))
                .andExpect(jsonPath("$.attemptsLeft", is(5)));
    }


//    @Test
//    @DisplayName("guess the word")
//    void guessWord() throws Exception {
//        when(wordService.provideRandomWord(5))
//                .thenReturn("groep");
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/lingo/game/{0}")
//                .param("attempt", "groep");
//
//        mockMvc.perform(request)
//                .andExpect(jsonPath("$.status", is("PLAYING")));
//
//    }
}
