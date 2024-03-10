import java.awt.*;

public interface GameView {
    void updateCellColor(int x, int y, Color color);
    void updateScoreLabel(int score);
}
