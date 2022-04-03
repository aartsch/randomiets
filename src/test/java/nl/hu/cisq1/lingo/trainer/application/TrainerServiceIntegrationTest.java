package nl.hu.cisq1.lingo.trainer.application;

import lombok.extern.slf4j.Slf4j;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.GameData;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrainerServiceIntegrationTest {
    private static final String RANDOM_WORD_5 = "groep";
    private static final String RANDOM_WORD_6 = "school";
    private static final String RANDOM_WORD_7 = "student";


    @Autowired
    private TrainerService trainerService;

    @Autowired
    private GameRepository repository;

    @Autowired
    private WordService wordService;

    @Autowired
    private WordRepository wordRepository;

    //TODO fix that word with length of 5 can not be found
    @Test
    @DisplayName("Starting a new game starts a new round")
    void startNewGame() {
        GameData gameData = trainerService.startGame();

        assertEquals(GameStatus.PLAYING, gameData.getStatus());
        assertEquals(0, gameData.getScore());
        assertEquals(5, gameData.getHint().length());
        assertEquals(5, gameData.getAttemptsLeft());
    }


    @Test
    @DisplayName("Correctly guessing a 5 letter word")
    void guess5LetterWord() {
        GameData startedGameData = trainerService.startGame();

        GameData gameData = trainerService.guess(startedGameData.getId(), "groep");

        assertEquals(GameStatus.ROUNDWON, gameData.getStatus());
        assertEquals(30, gameData.getScore());
        assertEquals(5, gameData.getHint().length());
        assertEquals(5, gameData.getAttemptsLeft());
    }

    @Test
    @DisplayName("Correctly guessing a 6 letterered word")
    void guess6LetterWord() {
        GameData startedGameData = trainerService.startGame();

        trainerService.guess(startedGameData.getId(), "groep");
        trainerService.startNewRound(startedGameData.getId());
        GameData gameData = trainerService.guess(startedGameData.getId(), "school");

        assertEquals(GameStatus.ROUNDWON, gameData.getStatus());
        assertEquals(60, gameData.getScore());
        assertEquals(6, gameData.getHint().length());
        assertEquals(5, gameData.getAttemptsLeft());
    }

    @Test
    @DisplayName("Correctly guessing a 7 letterered word")
    void guess7LetterWord() {
        GameData startedGameData = trainerService.startGame();

        trainerService.guess(startedGameData.getId(), "groep");
        trainerService.startNewRound(startedGameData.getId());
        trainerService.guess(startedGameData.getId(), "school");
        trainerService.startNewRound(startedGameData.getId());
        GameData gameData = trainerService.guess(startedGameData.getId(), "student");



        assertEquals(GameStatus.ROUNDWON, gameData.getStatus());
        assertEquals(90, gameData.getScore());
        assertEquals(7, gameData.getHint().length());
        assertEquals(5, gameData.getAttemptsLeft());
    }

    @Test
    @DisplayName("After correctly guessing a 7 letter word the word goes back to 5 letters")
    void After7LetteredWordGoesBackTo5Letter() {
        GameData startedGameData = trainerService.startGame();

        trainerService.guess(startedGameData.getId(), "groep");
        trainerService.startNewRound(startedGameData.getId());
        trainerService.guess(startedGameData.getId(), "school");
        trainerService.startNewRound(startedGameData.getId());
        trainerService.guess(startedGameData.getId(), "student");
        GameData gameData = trainerService.startNewRound(startedGameData.getId());

        assertEquals(GameStatus.PLAYING, gameData.getStatus());
        assertEquals(90, gameData.getScore());
        assertEquals(5, gameData.getHint().length());
        assertEquals(5, gameData.getAttemptsLeft());
    }



    @BeforeEach
    void loadTestData() {
        // Load test fixtures into test database before each test case
        wordRepository.deleteAll();
        repository.deleteAll();
        wordRepository.save(new Word(RANDOM_WORD_5));
        wordRepository.save(new Word(RANDOM_WORD_6));
        wordRepository.save(new Word(RANDOM_WORD_7));
    }

    @AfterEach
    void clearTestData() {
        // Remove test fixtures from test database after each test case
        wordRepository.deleteAll();
        repository.deleteAll();
    }


}