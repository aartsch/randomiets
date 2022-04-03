package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.ActionNotAllowedException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    @DisplayName("game is equal to another game")
    void equals() {
        List<Round> rounds = new ArrayList<>();

        Round round = new Round("groep",5);
        rounds.add(round);


        Game game1 = new Game(0,0, GameStatus.PLAYING, rounds );
        Game game2= new Game(0,0, GameStatus.PLAYING, rounds );
    }


    @Test
    @DisplayName("hashcode is equal to another hashcode")
    void testHashCode() {
        List<Round> rounds = new ArrayList<>();

        Round round = new Round("groep",5);
        rounds.add(round);


        Game game1 = new Game(0,0, GameStatus.PLAYING, rounds );
        Game game2= new Game(0,0, GameStatus.PLAYING, rounds );

        Map<Game, String> map = new HashMap<>();
        map.put(game1, "feedback");
        assertEquals("feedback", map.get(game2));
    }

    @Test
    public void simpleEqualsContract() {
        EqualsVerifier.simple().forClass(Feedback.class).verify();
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