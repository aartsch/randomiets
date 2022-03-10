package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private long id;
    private String wordToGuess;
    private int attemptsLeft;
    private String hint;
    private List<Feedback> feedbacks = new ArrayList<>();

    public Round(String wordToGuess, int attemptsLeft) {
        this.wordToGuess = wordToGuess;
        this.attemptsLeft = attemptsLeft;
        this.hint = this.giveInitialHint(wordToGuess);
    }

    public void guessWord(String attempt) {
        // everything with gameState should be moved to the Game class
        List<Mark> marks = generateMarks(this.wordToGuess, attempt);

        Feedback feedback = new Feedback(attempt, marks);

        this.feedbacks.add(feedback);

        int attemptsLeft = this.attemptsLeft;

        if(feedback.isAttemptInvalid()) {
            Feedback.invalid(attempt, this.wordToGuess);
            attemptsLeft = attemptsLeft -1;
        } else if (feedback.isWordGuessed()){
//            this.gameState = GameStatus.ROUNDWON;
        } else {
            attemptsLeft = attemptsLeft -1;
        }
        if (attemptsLeft == 0) {
//            this.gameState = GameStatus.LOST;
        }

        this.hint = feedback.giveHint(this.hint, this.wordToGuess, generateMarks(this.wordToGuess, attempt) );
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

    private String giveInitialHint(String wordToGuess) {
        StringBuilder result = new StringBuilder();

        result.append(wordToGuess.charAt(0));
        for (int i = 1; i < wordToGuess.length(); i++) {
            result.append(".");
        }
        // everything with gameState should be moved to the Game class

        return result.toString();
    }

    public String getHint() {
        return hint;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }
}
