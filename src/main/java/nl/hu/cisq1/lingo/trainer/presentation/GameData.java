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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
