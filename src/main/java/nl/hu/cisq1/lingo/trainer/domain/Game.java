package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Game {
    private long id;
    private GameStatus gameStatus;
    private int score;
    private List<Mark> rounds;

    public Game() {
    }

    public void startGame(String wordToGuess) {
        Round round = new Round(wordToGuess, 5);

    }

    public int calculateScore(int score) {

        return score;
    }

}
