package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.WordDoesNotExistException;
import nl.hu.cisq1.lingo.trainer.presentation.GameData;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TrainerService {
    private final GameRepository gameRepository;
    private final WordService wordService;

    public TrainerService(GameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public GameData startGame() {
        String wordToGuess = this.wordService.provideRandomWord(5);

        Game game = new Game();
        game.startGame(wordToGuess);
        this.gameRepository.save(game);

        return new GameData(
                game.getId(),
                game.getGameState(),
                game.getScore(),
                game.getLastRound().getAttemptsLeft(),
                game.getLastRound().getHint()
        );
    }

    public GameData guess(long id, String attempt) {
        Game game = this.findGameById(id);

        game.guess(attempt);

        this.gameRepository.save(game);

        if(!this.wordService.wordExists(attempt)) {
            throw new WordDoesNotExistException("word does not exist");
        }

        return new GameData(
                game.getId(),
                game.getGameState(),
                game.getScore(),
                game.getLastRound().getAttemptsLeft(),
                game.getLastRound().getHint()
        );
    }

    public GameData startNewRound(long id) {
        Game game = this.findGameById(id);

        int previousWordToGuessLenght = game.getLastRound().getWordToGuess().length();

        if(previousWordToGuessLenght < 7) {
            String wordToGuess = this.wordService.provideRandomWord(previousWordToGuessLenght + 1);
            game.startNewRound(wordToGuess);
        } else {
            String wordToGuess = this.wordService.provideRandomWord(5);
            game.startNewRound(wordToGuess);
        }

        return new GameData(
                game.getId(),
                game.getGameState(),
                game.getScore(),
                game.getLastRound().getAttemptsLeft(),
                game.getLastRound().getHint()
        );
    }

    private Game findGameById(long id) {
        return this.gameRepository
                .findById(id)
                .orElseThrow(() -> new GameNotFoundException("game met id " + id + " niet gevonden."));
    }
}
