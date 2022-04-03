package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.WordDoesNotExistException;
import nl.hu.cisq1.lingo.trainer.presentation.GameData;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TrainerServiceTest {
    private static List<Round> rounds = new ArrayList<>();
    private static Game game = new Game(0, 0, GameStatus.PLAYING, rounds);
    private static long id = 0;

    @Test
    @DisplayName("starting a game starts a new round")
    void startGame() {
        WordRepository mockRepository = mock(WordRepository.class);
        when(mockRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("groep")));

        WordService wordService = new WordService(mockRepository);

        GameRepository repository = mock(GameRepository.class);

        TrainerService trainerService = new TrainerService(repository, wordService);
        GameData gameData = trainerService.startGame();

        assertEquals("g....", gameData.getHint());
        assertEquals(0, gameData.getId());
        assertEquals(GameStatus.PLAYING, gameData.getStatus());
        assertEquals(0, gameData.getScore());
    }

    @Test
    @DisplayName("throws exception if the word in the attempt does not exist")
    void wordDoesNotExist() {
        WordRepository mockRepository = mock(WordRepository.class);
        when(mockRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("groep")));

        GameRepository repository = mock(GameRepository.class);

        WordService wordService = new WordService(mockRepository);

        TrainerService trainerService = new TrainerService(repository, wordService);

        when(repository.save(new Game()))
                .thenReturn(game);

        when(repository.findById(id))
                .thenReturn(Optional.of(game));

        GameData gameData = trainerService.startGame();

        game.getRounds().add(new Round(wordService.provideRandomWord(5), gameData.getAttemptsLeft()));

        assertThrows(
                WordDoesNotExistException.class,
                () -> trainerService.guess(id, "klose")
        );
    }

    @Test
    @DisplayName("throws exception if game is not found with id")
    void gameWithIdDoesNotExist() {
        WordRepository mockRepository = mock(WordRepository.class);
        when(mockRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("groep")));

        GameRepository repository = mock(GameRepository.class);

        WordService wordService = new WordService(mockRepository);

        TrainerService trainerService = new TrainerService(repository, wordService);

        assertThrows(
                GameNotFoundException.class,
                () -> trainerService.guess(id, "klose")
        );
        assertThrows(
                GameNotFoundException.class,
                () -> trainerService.startNewRound(id)
        );

    }

}