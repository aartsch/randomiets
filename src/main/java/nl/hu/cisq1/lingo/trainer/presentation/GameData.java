package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameData {
    private long id;
    private GameStatus status;
    private int score;
    private int attemptsLeft;
    private String hint;

    public GameData(long id, GameStatus status, int score, int attemptsLeft, String hint) {
        this.id = id;
        this.status = status;
        this.score = score;
        this.attemptsLeft = attemptsLeft;
        this.hint = hint;
    }
}
