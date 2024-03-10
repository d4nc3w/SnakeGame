import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.List;

public class Controller {
    private BoardModel boardModel;
    private Snake snake;
    private Apple apple;
    protected boolean isGameOver;
    private KeyboardHandler keyboardHandler;
    private GameView gameView;
    private int score;

    public Controller() {
        boardModel = new BoardModel();
        snake = new Snake();
        apple = new Apple(BoardModel.BOARD_WIDTH, BoardModel.BOARD_HEIGHT, snake.getSegments());
        isGameOver = false;
        keyboardHandler = new KeyboardHandler();
        score = 0;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void start() {
        Gameplay gameplay = new Gameplay(this);
        Thread gameThread = new Thread(gameplay);
        gameThread.start();
    }

    protected void updateGame() {
        Direction direction = keyboardHandler.getDirection();

        // current state of the board
        int[][] state = boardModel.getState();

        // Clear the board
        List<SnakeSegment> snakeSegments = snake.getSegments();
        for (SnakeSegment snakeSegment : snakeSegments) {
            int x = snakeSegment.getX();
            int y = snakeSegment.getY();
            if (x >= 0 && x < BoardModel.BOARD_WIDTH && y >= 0 && y < BoardModel.BOARD_HEIGHT) {
                state[y][x] = BoardModel.EMPTY_CELL;
            }
        }

        snake.setDirection(direction);
        snake.move();

        // get the new head position
        SnakeSegment head = snakeSegments.get(0);
        int newHeadX = head.getX();
        int newHeadY = head.getY();

        // check collision with apple
        if (newHeadX >= 0 && newHeadX < BoardModel.BOARD_WIDTH && newHeadY >= 0 && newHeadY < BoardModel.BOARD_HEIGHT){
            if (state[newHeadY][newHeadX] == BoardModel.FOOD) {
                score++;
                snake.grow();
                apple.generateApple
                        (BoardModel.BOARD_WIDTH - 1, BoardModel.BOARD_HEIGHT - 1, snake.getSegments());
            }
        }

        // Snake collided with itself
        for (int i = 1; i < snakeSegments.size(); i++) {
            SnakeSegment snakeSegment = snakeSegments.get(i);
            if (snakeSegment.getX() == newHeadX && snakeSegment.getY() == newHeadY) {
                isGameOver = true;
                return;
            }
        }

        // Snake collided with the wall
        if (newHeadX < 0 || newHeadX >= BoardModel.BOARD_WIDTH || newHeadY < 0 || newHeadY >= BoardModel.BOARD_HEIGHT) {
            isGameOver = true;
            return;
        }

        // update Snake on the board
        for (SnakeSegment snakeSegment : snakeSegments) {
            int x = snakeSegment.getX();
            int y = snakeSegment.getY();

            if (x >= 0 && x < BoardModel.BOARD_WIDTH && y >= 0 && y < BoardModel.BOARD_HEIGHT) {
                state[y][x] = BoardModel.SNAKE;
            }
        }

        // set the apple
        boardModel.setApplePosition(apple.getX(), apple.getY());
        state[apple.getY()][apple.getX()] = BoardModel.FOOD;

        // update the snake
        boardModel.setSnakeSegments(snakeSegments);
        updateView();
    }

    protected void updateView() {
        int[][] state = boardModel.getState();

        // Render the snake and apple on the view
        for (int row = 0; row < BoardModel.BOARD_HEIGHT; row++) {
            for (int col = 0; col < BoardModel.BOARD_WIDTH; col++) {
                int cellValue = state[row][col];
                Color color = null;

                if (cellValue == BoardModel.SNAKE) {
                    color = Color.GREEN;
                } else if (cellValue == BoardModel.FOOD) {
                    color = Color.RED;
                }

                gameView.updateCellColor(col, row, color);
            }
        }

        gameView.updateScoreLabel(score);
    }


    protected void handleGameOver() {
        String nickname = showNicknameDialog();
        if (nickname != null && !nickname.isEmpty()) {
            Player player = new Player(nickname, score);
            player.writeToFile();
            ScorePanel scorePanel = new ScorePanel(this);
            scorePanel.loadTopPlayers();
        }
    }

    private String showNicknameDialog() {
        return JOptionPane.showInputDialog("Enter Your Nickname:");
    }

    public KeyAdapter keyListener(){
        return keyboardHandler;
    }
}
