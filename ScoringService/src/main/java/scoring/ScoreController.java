package scoring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreController {

    private final ScoreState scoreState;

    public ScoreController(ScoreState scoreState) {
        this.scoreState = scoreState;
    }

    @GetMapping(value = "/api/score", produces = "text/plain")
    public String getScore() {
        return String.valueOf(scoreState.getScore());
    }

    @PostMapping(value = "/api/score/add/{points}", produces = "text/plain")
    public String addPoints(@PathVariable("points") int points) {
        return String.valueOf(scoreState.addPoints(points));
    }

    @PostMapping("/api/score/reset")
    public void resetScore() {
        scoreState.reset();
    }
}