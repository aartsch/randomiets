package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.validator.internal.constraintvalidators.hv.ru.INNValidator;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public static Feedback invalid(String attempt, String wordToGuess) {
        List<Mark> marks = Collections.nCopies(wordToGuess.length(), Mark.INVALID);
        return new Feedback(attempt, marks);
    }

    public boolean isWordGuessed() {
        return marks.stream()
                .allMatch(Mark.CORRECT::equals);
    }

    public boolean isAttemptInvalid() {
        return marks
                .stream()
                .anyMatch(Mark.INVALID::equals);
    }

    public String giveHint(String previousHint, String wordToGuess, List<Mark> marks) {
        StringBuilder result = new StringBuilder();

        // G R O E P
        // G E N E N
        // G . . E .

        for (int i = 0; i < marks.size(); i++) {
            if (marks.get(i).equals(Mark.CORRECT)) {
                result.append(wordToGuess.charAt(i));
            } else {
                result.append(previousHint.charAt(i));
            }
        }
        return result.toString();
    }

//    public String baseHint(String wordToGuess) {
//        StringBuilder result = new StringBuilder();
//
//        result.append(wordToGuess.charAt(0));
//        for (int i = 1; i < wordToGuess.length(); i++) {
//            result.append(".");
//        }
//
//        return result.toString();
//    }


        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }
}
