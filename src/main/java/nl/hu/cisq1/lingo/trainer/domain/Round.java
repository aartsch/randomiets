package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue
    private long id;
    private String wordToGuess;
    private int attemptsLeft;
    private String hint;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks = new ArrayList<>();

    public Round(String wordToGuess, int attemptsLeft) {
        this.wordToGuess = wordToGuess;
        this.attemptsLeft = attemptsLeft;
        this.hint = this.giveInitialHint(wordToGuess);
    }

    public Round() {
    }

    public void guessWord(String attempt) {
        // everything with gameState should be moved to the Game class
        List<Mark> marks = generateMarks(this.wordToGuess, attempt);

        Feedback feedback = new Feedback(attempt, marks);

        feedbacks.add(feedback);

        // could change the condition in the if statement to private method
        if(attempt.length() > this.wordToGuess.length()) {
            Feedback invalidFeedback = Feedback.invalid(attempt, this.wordToGuess);
            feedbacks.set(feedbacks.size()-1, invalidFeedback);
            this.attemptsLeft = this.attemptsLeft -1;
        } else if (feedback.isWordGuessed()){

        } else {
            this.attemptsLeft = this.attemptsLeft -1;
        }

        this.hint = feedback.giveHint(this.hint, this.wordToGuess, generateMarks(this.wordToGuess, attempt) );
    }

    public boolean isWordGuessed() {
        if(this.attemptsLeft == 0) {
            return false;
        }

        Feedback feedback = lastFeedback();

        return feedback.isWordGuessed();

    }

    public boolean isGameOver() {
        return (!isWordGuessed() && this.attemptsLeft == 0);
    }

    public Feedback lastFeedback() {
        Feedback feedback = feedbacks.get(feedbacks.size()-1);

        return feedback;
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

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}
