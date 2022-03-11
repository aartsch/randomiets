package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private long id;
    private int score = 0;
    private GameStatus gameState;
    private List<Round> rounds = new ArrayList<>();

    public Game() {
    }

    public void startGame(String wordToGuess) {
        Round round = new Round(wordToGuess, 5);
        this.gameState = GameStatus.PLAYING;

        this.rounds.add(round);
    }

    public void startNewRound(String wordToGuess) {
        Round round = new Round(wordToGuess, 5);

        this.rounds.add(round);
    }


    private void guess(String attempt, String wordToGuess) {
        Round lastRound = getLastRound();

        lastRound.guessWord(attempt);

        if (lastRound.getFeedbacks().get(lastRound.getFeedbacks().size()-1).isWordGuessed()) {
            this.gameState = GameStatus.ROUNDWON;
            calculateScore(lastRound);
        } else if (lastRound.isGameOver()) {
            this.gameState = GameStatus.LOST;
        }
    }

    public void calculateScore(Round round) {
        this.score = this.score + 5*round.getAttemptsLeft()+5;
    }

    private Round getLastRound() {
        Round last = rounds.get(rounds.size()-1);

        return last;
    }

}
