package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TrainerService {
    private final GameRepository gameRepository;
    private WordService wordService;

    public TrainerService(GameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public void startGame() {
        Game game = new Game();

        String wordToGuess = this.wordService.provideRandomWord(5);

        game.startGame(wordToGuess);

        this.gameRepository.save(game);
    }

    public void guess(long id, String attempt) {
        Game game = gameRepository.findById(id);

        game.guess(attempt);
    }

    public void startNewRound(long id) {
        Game game = gameRepository.findById(id);

        int previousWordToGuessLenght = game.getLastRound().getWordToGuess().length();

        if(previousWordToGuessLenght < 7) {
            String wordToGuess = this.wordService.provideRandomWord(previousWordToGuessLenght + 1);
            game.startNewRound(wordToGuess);
        } else {
            String wordToGuess = this.wordService.provideRandomWord(5);
            game.startNewRound(wordToGuess);
        }
    }
}
