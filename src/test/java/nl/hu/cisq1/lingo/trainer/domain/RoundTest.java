package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
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

}