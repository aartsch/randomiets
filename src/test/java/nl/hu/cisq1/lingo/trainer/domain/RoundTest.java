package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("Start round returns correct hint")
    void isBaseHintCorrect() {
        String wordToGuess = "groep";

        Round round = new Round(wordToGuess, 5);

        String startRound = round.getHint();

        assertEquals("g....", startRound);
    }

    @ParameterizedTest
    @MethodSource("markExamples")
    @DisplayName("Check if the generated marks are correct")
    void checkGeneratedMarks(String wordToGuess, String attempt, List<Mark> marks) {
        Round round = new Round(wordToGuess, 5);

        List<Mark> generatedMarks = round.generateMarks(wordToGuess, attempt);


        assertEquals(marks, generatedMarks);
    }

    static Stream<Arguments> markExamples() {
        return Stream.of(
                Arguments.of("groep", "groes", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT)),
                Arguments.of("groep", "porgi", List.of(Mark.PRESENT, Mark.PRESENT, Mark.PRESENT, Mark.PRESENT, Mark.ABSENT)),
                Arguments.of("groep", "groep", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)),
                Arguments.of("groep", "slaak", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT))
        );
    }

    @Test
    @DisplayName("Check if the feedback after a guess is correct")
    void correctFeedbackGuessWord() {
        String wordToGuess = "groep";
        String attempt = "groes";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);

        String result = round.getHint();

        assertEquals("groe.", result);
    }

    @Test
    @DisplayName("check if the word is guessed")
    void isWordGuessed() {
        String wordToGuess = "groep";
        String attempt = "groep";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);

        boolean wordGuessed =  round.isWordGuessed();

        assertTrue(wordGuessed);
    }

    @Test
    @DisplayName("check if the word is not guessed")
    void isWordNotGuessed() {
        String wordToGuess = "groep";
        String attempt = "graag";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);

        boolean wordGuessed =  round.isWordGuessed();

        assertFalse(wordGuessed);
    }

    @Test
    @DisplayName("check if the round is over")
    void isRoundOver() {
        String wordToGuess = "groep";
        String attempt = "groek";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);
        round.guessWord(attempt);
        round.guessWord(attempt);
        round.guessWord(attempt);
        round.guessWord(attempt);

        boolean roundOver =  round.isGameOver();

        assertTrue(roundOver);
    }

    @Test
    @DisplayName("check if the round is not over")
    void isRoundNotOver() {
        String wordToGuess = "groep";
        String attempt = "groek";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);


        boolean roundNotOver =  !round.isGameOver();

        assertTrue(roundNotOver);
    }

    @Test
    @DisplayName("attempts left based on amount of times guessed")
    void attemptsBasedOnGuesses() {
        String wordToGuess = "groep";
        String attempt = "groek";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);
        round.guessWord(attempt);
        round.guessWord(attempt);

        int attemptsLeft = round.getAttemptsLeft();

        assertEquals(2, attemptsLeft);
    }

    @Test
    @DisplayName("check if size of feedback equal to attemptsleft")
    void compareFeedbackToAttemptsLeft() {
        String wordToGuess = "groep";
        String attempt = "groek";

        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);
        round.guessWord(attempt);

        int attemptsLeft = round.getAttemptsLeft();
        List<Feedback> feedbacks = round.getFeedbacks();

        assertEquals(5 - feedbacks.size(), attemptsLeft);
    }

    @Test
    @DisplayName("Does the attemptsleft go down if the feedback is invalid")
    void checkIfFeedbackIsInvalidAffectsAttempts() {
        String wordToGuess = "groep";
        String attempt = "groekss";
        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);

        int attemptsLeft = round.getAttemptsLeft();

        assertEquals(4, attemptsLeft);
    }

    @Test
    @DisplayName("Does the attemptsleft go down if the feedback is invalid")
    void checkIfFeedbackIsInvalid() {
        String wordToGuess = "groep";
        String attempt = "groeksiii";
        Round round = new Round(wordToGuess, 5 );

        round.guessWord(attempt);

        Feedback feedback = round.lastFeedback();


        Feedback expectedFeedback = new Feedback(attempt, (List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));


        assertEquals(expectedFeedback.getMarks(), feedback.getMarks());
    }

    @Test
    @DisplayName("game is equal to another game")
    void equals() {
        String attempt = "woord";

        List<Mark> marks1 = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT);

        Feedback feedback1  = new Feedback(attempt, marks1 );

        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedback1);

        Round round1 = new Round("groep",5, "g....", feedbacks );
        Round round2 = new Round("groep",5, "g....", feedbacks);

        assertEquals(round1, round2);
    }

    @Test
    @DisplayName("hashcode is equal to another hashcode")
    void testHashCode() {
        String attempt = "woord";

        List<Mark> marks1 = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT);

        Feedback feedback1  = new Feedback(attempt, marks1 );

        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedback1);

        Round round1 = new Round("groep",5, "g....", feedbacks );
        Round round2 = new Round("groep",5, "g....", feedbacks);

        Map<Round, String> map = new HashMap<>();
        map.put(round1, "round");
        assertEquals("round", map.get(round2));
    }

    @Test
    public void simpleEqualsContract() {
        EqualsVerifier.simple().forClass(Round.class).verify();
    }

}