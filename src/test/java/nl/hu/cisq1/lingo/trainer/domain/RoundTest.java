package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("Start round returns correct hint")
    void isRoundStarted() {
        String wordToGuess = "groep";

        Round round = new Round(wordToGuess, 5);

        String startRound = round.startRound();

        assertEquals("g....", startRound);
    }

    @Test
    @DisplayName("Check if the generated marks are correct")
    void checkGeneratedMarks() {
        String wordToGuess = "groep";
        String attempt = "porge";

        Round round = new Round(wordToGuess, 5);

        List<Mark> generatedMarks = round.generateMarks(wordToGuess, attempt);

        List<Mark> marks = List.of(Mark.PRESENT, Mark.PRESENT, Mark.PRESENT, Mark.PRESENT, Mark.PRESENT);

        assertEquals(marks, generatedMarks);
    }

    @Test
    @DisplayName("Check if the feedback after a guess is correct")
    void correctFeedbackGuessWord() {
        String wordToGuess = "groep";
        String attempt = "groes";

        Round round = new Round(wordToGuess, 5);

        String result = round.guessWord(attempt);

        assertEquals("groe.", result);

    }

}