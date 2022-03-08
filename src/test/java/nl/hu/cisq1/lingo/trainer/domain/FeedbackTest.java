package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed() {
        String attempt = "groep";
        String wordToGuess = "groep";
        List<Mark> marks = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT);

        Feedback feedback = new Feedback(attempt, marks );

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsNotGuessed() {
        String attempt = "groeb";
        String wordToGuess = "groep";
        List<Mark> marks = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT);

        Feedback feedback = new Feedback(attempt, marks );

        assertTrue(!feedback.isWordGuessed());
    }

    @Test
    @DisplayName("attempt is invalid if a letter is marked invalid")
    void attemptIsInvalid() {
        String attempt = "groepen";
        String wordToGuess = "groep";

        Feedback feedback = Feedback.invalid(attempt, wordToGuess);

        assertTrue(feedback.isAttemptInvalid());
    }

    @ParameterizedTest
    @MethodSource("hintExamples")
    @DisplayName("give hint based on previous hint and word to guess")
    void giveHint(String previousHint, String wordToGuess, String attempt, List<Mark> marks, String exptectedHint) {
        Feedback feedback = new Feedback(attempt, marks);

        String nextHint = feedback.giveHint(previousHint, wordToGuess, marks);

        assertEquals(exptectedHint, nextHint);

    }

    static Stream<Arguments> hintExamples() {
        return Stream.of(
                Arguments.of("g....", "groep", "genen", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT), "g..e."),
                Arguments.of("g..e.", "groep", "gedoe", List.of(Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT), "g..e.")
        );
    }


    @Test
    @DisplayName("feedback is equal to other feedback")
    void equals() {
        String attempt = "woord";

        List<Mark> marks1 = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT);
        List<Mark> marks2 = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT);

        Feedback feedback1  = new Feedback(attempt, marks1 );
        Feedback feedback2  = new Feedback(attempt, marks2 );

        assertEquals(feedback1, feedback2);
    }
}