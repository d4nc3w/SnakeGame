import java.util.List;
import java.util.Random;

public class Apple {
    private int x;
    private int y;

    public Apple(int maxX, int maxY, List<SnakeSegment> snakeSnakeSegments) {
        generateApple(maxX, maxY, snakeSnakeSegments);
    }

    void generateApple(int maxX, int maxY, List<SnakeSegment> snakeSnakeSegments) {
        Random random = new Random();
        boolean validPosition = false;

        while (!validPosition) {
            validPosition = true;
            x = random.nextInt(maxX);
            y = random.nextInt(maxY);

            // Check if the generated position conflicts with snake
            for (SnakeSegment snakeSegment : snakeSnakeSegments) {
                if (snakeSegment.getX() == x && snakeSegment.getY() == y) {
                    validPosition = false;
                    break;
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}