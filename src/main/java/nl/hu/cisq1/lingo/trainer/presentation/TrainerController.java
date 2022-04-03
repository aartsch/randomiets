package nl.hu.cisq1.lingo.trainer.presentation;


import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.ActionNotAllowedException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.WordDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/lingo")
public class TrainerController {
    private TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping(path = "/game")
    public GameData StartGame() {
        return this.service.startGame();
    }

    @PostMapping("/game/{id}")
    public GameData guess(@PathVariable int id, @RequestParam String attempt) {
        try {
            return this.service.guess(id, attempt);
        } catch (ActionNotAllowedException | WordDoesNotExistException actionNotAllowed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (GameNotFoundException gameNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/game/round/{id}")
    public GameData startNewRound(@PathVariable int id) {
        try {
            return this.service.startNewRound(id);
        } catch (ActionNotAllowedException actionNotAllowed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (GameNotFoundException gameNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
