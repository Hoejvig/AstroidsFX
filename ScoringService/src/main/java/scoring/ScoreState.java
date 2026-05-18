package scoring;

import org.springframework.stereotype.Service;

@Service
public class ScoreState {

    private int score = 0;

    public synchronized int getScore() {
        return score;
    }

    public synchronized int addPoints(int points) {
        score += points;
        return score;
    }

    public synchronized void reset() {
        score = 0;
    }
}