package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private long id;
    private String wordToGuess;
    private int attemptsLeft;
    private GameStatus gameState;

    public Round(String wordToGuess, int attemptsLeft) {
        this.wordToGuess = wordToGuess;
        this.attemptsLeft = attemptsLeft;
    }

    // should probably use new Round() in the game class instead of this function
//    public Round createRound(String wordToGuess) {
//
//        Round round = new Round(wordToGuess, 5);
//
//        return round;
//    }

    public String startRound() {
        StringBuilder result = new StringBuilder();

        result.append(wordToGuess.charAt(0));
        for (int i = 1; i < wordToGuess.length(); i++) {
            result.append(".");
        }

        // everything with gameState should be moved to the Game class
         this.gameState = GameStatus.PLAYING;

        return result.toString();
    }

    public String guessWord(String attempt) {
        // everything with gameState should be moved to the Game class
        List<Mark> marks = generateMarks(this.wordToGuess, attempt);

        Feedback feedback = new Feedback(attempt, marks);

        int attemptsLeft = this.attemptsLeft;

        if(feedback.isAttemptInvalid()) {
            Feedback.invalid(attempt, this.wordToGuess);
            attemptsLeft = attemptsLeft -1;
        } else if (feedback.isWordGuessed()){
            this.gameState = GameStatus.ROUNDWON;
        } else {
            attemptsLeft = attemptsLeft -1;
        }

        if (attemptsLeft == 0) {
            this.gameState = GameStatus.LOST;
        }

        return feedback.giveHint(".....", this.wordToGuess, generateMarks(this.wordToGuess, attempt) );
    }

    public List<Mark> generateMarks(String wordToGuess, String attempt) {

        List<Mark> marks = new ArrayList<>();

        for (int i = 0; i < wordToGuess.length(); i++) {
            // check if letter is in the wordToGuess at the right place
            if (wordToGuess.toCharArray()[i] == attempt.toCharArray()[i]) {
                marks.add(Mark.CORRECT);
            // check if letter is in the word to guess
            } else if (wordToGuess.indexOf(attempt.toCharArray()[i]) >= 0) {
                marks.add(Mark.PRESENT);
            // check if letter is absent
            } else {
                marks.add(Mark.ABSENT);
            }
        }
        return marks;
    }

}
