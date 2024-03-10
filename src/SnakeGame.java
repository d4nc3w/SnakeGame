import javax.swing.*;

public class SnakeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            BoardVisual boardVisual = new BoardVisual(controller);
            controller.setGameView(boardVisual);
            controller.start();
        });
    }
}
