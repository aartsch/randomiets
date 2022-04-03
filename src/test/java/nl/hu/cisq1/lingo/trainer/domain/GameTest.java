package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.ActionNotAllowedException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    static Game game = null;

    @Test
    @DisplayName("Starting a game starts a new round")
    void doesStartGameStartNewRound() {
        game.startGame("groep");

        Round lastRound = game.getLastRound();
        Round expectedRound = new Round("groep",5);


        assertEquals(expectedRound.getFeedbacks(), lastRound.getFeedbacks());
    }

    @Test
    @DisplayName("Is game state equal to playing after starting the game")
    void isGameStatePlayingAfterStarting() {
        game.startGame("groep");

        assertEquals(GameStatus.PLAYING, game.getGameState());
    }

    @Test
    @DisplayName("Able to start a new round when gamestate is equal to roundwon")
    void succesfullyStartingNewRound() {
        game.startGame("groep");

        game.guess("groep");

        game.startNewRound("hater");

        Round round = game.getLastRound();
        Round expectedRound = new Round("hater", 5);

        assertEquals(expectedRound.getWordToGuess(), round.getWordToGuess());
    }

    @Test
    @DisplayName("game throws an exception if a new round cant get started")
    void startingNewRoundIsNotAllowed() {
        Exception exception = assertThrows(ActionNotAllowedException.class, () -> {
            game.startGame("groep");
            game.startNewRound("groepjes");
        });

        assertEquals("Cant start a new round, start a new game" , exception.getMessage());
    }

    @Test
    @DisplayName("game throws an exception if you try to guess when gamestate is not playing")
    void guessingWhenGameStateNotPlaying() {
        Exception exception = assertThrows(ActionNotAllowedException.class, () -> {
            game.startGame("groep");
            game.guess("graag");
            game.guess("graag");
            game.guess("graag");
            game.guess("graag");
            game.guess("graag");

            game.guess("graag");
        });

        assertEquals("Cant guess, start a new game" , exception.getMessage());
    }

    @Test
    @DisplayName("is the word guessed correctly")
    void isWordGuessed() {
        game.startGame("groep");

        game.guess("groep");

        assertEquals(GameStatus.ROUNDWON, game.getGameState());
    }

    @Test
    @DisplayName("is the word guessed correctly")
    void isGameOver() {
        game.startGame("groep");

        game.guess("graag");
        game.guess("graag");
        game.guess("graag");
        game.guess("graag");
        game.guess("graag");

        assertEquals(GameStatus.LOST, game.getGameState());
    }

    @ParameterizedTest
    @DisplayName("Does calculate score return the right score")
    @MethodSource("scoreSamples")
    void calculateScore(Round round, int expectedScore) {
        Game game1 = new Game(0,0, GameStatus.PLAYING, null);

        game1.calculateScore(round);

        assertEquals(expectedScore, game1.getScore());
    }

    static Stream<Arguments> scoreSamples() {
        return Stream.of(
                Arguments.of(new Round("groep", 5), 30),
                Arguments.of(new Round("groep", 4),25),
                Arguments.of(new Round("groep", 3),20),
                Arguments.of(new Round("groep", 2),15),
                Arguments.of(new Round("groep", 1),10)
        );
    }

    @Test
    @DisplayName("does score go up when word is guessed correctly")
    void scoreAfterCorrectGuess() {
        List<Round> rounds = new ArrayList<>();

        Round round = new Round("groep", 5);
        rounds.add(round);

        Game game1 = new Game(0,0, GameStatus.PLAYING, rounds);

        game1.guess("groep");

        assertEquals(30 , game1.getScore() );
    }

    @Test
    @DisplayName("game is equal to another game")
    void equals() {
        List<Round> rounds = new ArrayList<>();

        Round round = new Round("groep",5);
        Round round2 = new Round("groek" ,5);
        rounds.add(round);
        rounds.add(round2);

        Game game1 = new Game(0,0, GameStatus.PLAYING, rounds );
        Game game2= new Game(0,0, GameStatus.PLAYING, rounds );

        assertEquals(game1, game2);
    }


    @Test
    @DisplayName("hashcode is equal to another hashcode")
    void testHashCode() {
        List<Round> rounds = new ArrayList<>();

        Round round = new Round("groep",5);
        rounds.add(round);


        Game game1 = new Game(0,0, GameStatus.PLAYING, rounds);
        Game game2= new Game(0,0, GameStatus.PLAYING, rounds);

        Map<Game, String> map = new HashMap<>();
        map.put(game1, "game");
        assertEquals("game", map.get(game2));
    }

    @Test
    public void simpleEqualsContract() {
        EqualsVerifier.simple().forClass(Game.class).verify();
    }

    @BeforeAll
    public static void setUp() {
        game = new Game();
    }

    @AfterAll
    public static void tearDown() {
        game = null;
    }



}