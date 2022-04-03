package nl.hu.cisq1.lingo.trainer.application;

import lombok.extern.slf4j.Slf4j;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.GameData;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrainerServiceIntegrationTest {
    @Autowired
    private TrainerService trainerService;

    @Autowired
    private GameRepository repository;

    //TODO fix that word with length of 5 can not be found
//    @Test
////    @DisplayName("Starting a new game starts a new round")
////    void startNewGame() {
////        GameData gameData = trainerService.startGame();
////
////        assertEquals(GameStatus.PLAYING, gameData.getStatus());
////        assertEquals(0, gameData.getScore());
////        assertEquals(5, gameData.getHint().length());
////        assertEquals(5, gameData.getAttemptsLeft());
////    }


}