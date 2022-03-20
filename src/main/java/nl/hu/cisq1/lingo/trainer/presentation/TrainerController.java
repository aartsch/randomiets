package nl.hu.cisq1.lingo.trainer.presentation;


import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo")
public class TrainerController {
    private TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public GameData StartGame() {
            GameData gameData = this.service.startGame();
            return gameData;
    }

    @PostMapping("/game/{id}")
    public GameData guess(@PathVariable int id, @RequestBody String attempt) {
        return this.service.guess(id, attempt);
    }

    @PostMapping("/game/{id}")
    public GameData startNewRound(@PathVariable int id) {
        return this.service.startNewRound(id);
    }

}
