package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerTest {
    private static List<Round> rounds = new ArrayList<>();
    private static Game game = new Game(0, 0, GameStatus.PLAYING, rounds);
    private static long id = 100;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @Autowired
    private GameRepository repository;

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

    @Test
    @DisplayName("Cant start a new round if game is not started")
    void startNewRoundGameNotFoundException() throws Exception {
        when(wordService.provideRandomWord(5))
                .thenReturn("groep");

        game.getRounds().add(new Round(wordService.provideRandomWord(5), 5));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/round/0");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}


