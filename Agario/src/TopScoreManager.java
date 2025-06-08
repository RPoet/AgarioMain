import java.util.*;

public class TopScoreManager {
    private static final List<PlayerRecord> topScores = new ArrayList<>();

    public static void addScore(String name, int score) {
        topScores.add(new PlayerRecord(name, score));
        topScores.sort((a, b) -> Integer.compare(b.score, a.score));
        if (topScores.size() > 10) topScores.remove(10);
    }

    public static List<PlayerRecord> getTopScores() {
        return topScores;
    }

    public static class PlayerRecord {
        public final String name;
        public final int score;

        public PlayerRecord(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}