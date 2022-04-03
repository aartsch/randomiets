package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.ActionNotAllowedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private long id;
    private int score = 0;
    @Enumerated(EnumType.STRING)
    private GameStatus gameState;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Round> rounds = new ArrayList<>();

    public Game(long id, int score, GameStatus gameState, List<Round> rounds) {
        this.id = id;
        this.score = score;
        this.gameState = gameState;
        this.rounds = rounds;
    }

    public Game() {
    }

    public void startGame(String wordToGuess) {
        Round round = new Round(wordToGuess, 5);
        this.gameState = GameStatus.PLAYING;

        this.rounds.add(round);
    }

    public void startNewRound(String wordToGuess) {
        if(this.gameState != GameStatus.ROUNDWON) {
            throw new ActionNotAllowedException("Cant start a new round, start a new game");
        }

        Round round = new Round(wordToGuess, 5);
        this.gameState = GameStatus.PLAYING;

        this.rounds.add(round);
    }


    public void guess(String attempt) {
        if(this.gameState != GameStatus.PLAYING) {
            throw new ActionNotAllowedException("Cant guess, start a new game");
        }

        Round lastRound = getLastRound();

        lastRound.guessWord(attempt);

        if (lastRound.getFeedbacks().get(lastRound.getFeedbacks().size()-1).isWordGuessed()) {
            this.gameState = GameStatus.ROUNDWON;
            calculateScore(lastRound); }
        else if (lastRound.isGameOver()) {
            this.gameState = GameStatus.LOST;
        }
    }

    // was eerst private maar is nodig in TrainerService
    public Round getLastRound() {
        return rounds.get(rounds.size()-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return score == game.score && gameState == game.gameState && Objects.equals(rounds, game.rounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, gameState, rounds);
    }

    void calculateScore(Round round) {
        this.score = this.score + 5*round.getAttemptsLeft()+5;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public GameStatus getGameState() {
        return gameState;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
