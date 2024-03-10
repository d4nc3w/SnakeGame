import java.util.*;

public class BoardModel {
    public static final int BOARD_WIDTH = 25;
    public static final int BOARD_HEIGHT = 16;
    public static final int EMPTY_CELL = 0;
    public static final int SNAKE = 1;
    public static final int FOOD = 2;
    private int[][] board;
    private int appleX;
    private int appleY;
    private List<SnakeSegment> snakeSnakeSegments;

    public BoardModel() {
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        initializeBoard();
        appleX = -1;
        appleY = -1;
        snakeSnakeSegments = new ArrayList<>();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = EMPTY_CELL;
            }
        }
    }

    public int[][] getState() {
        return board;
    }

    public void setApplePosition(int x, int y) {
        appleX = x;
        appleY = y;
    }

    public void setSnakeSegments(List<SnakeSegment> snakeSegments) {
        snakeSnakeSegments = snakeSegments;
    }
}

