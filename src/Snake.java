import java.util.*;

public class Snake {
    private List<SnakeSegment> snakeSegments;
    private Direction direction;

    public Snake() {
        snakeSegments = new ArrayList<>();

        int startX = (BoardModel.BOARD_WIDTH - 1) / 2;
        int startY = (BoardModel.BOARD_HEIGHT - 1) / 2;
        // Head
        snakeSegments.add(new SnakeSegment(startX, startY, true));
        //Body
        snakeSegments.add(new SnakeSegment(startX - 1, startY, false));
        snakeSegments.add(new SnakeSegment(startX - 2, startY, false));

        direction = Direction.RIGHT;
    }

    public List<SnakeSegment> getSegments() {
        return snakeSegments;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void move() {

        // Get the head segment
        SnakeSegment head = snakeSegments.get(0);

        // Calculate the new head position
        int newHeadX = head.getX();
        int newHeadY = head.getY();
        switch (direction) {
            case UP:
                newHeadY--;
                break;
            case DOWN:
                newHeadY++;
                break;
            case LEFT:
                newHeadX--;
                break;
            case RIGHT:
                newHeadX++;
                break;
        }

        // Create new head and add it to the front
        SnakeSegment newHead = new SnakeSegment(newHeadX, newHeadY, true);
        snakeSegments.add(0, newHead);

        // Remove the tail segment
        snakeSegments.remove(snakeSegments.size() - 1);
    }

    public void grow() {

        // Get the tail
        SnakeSegment tail = snakeSegments.get(snakeSegments.size() - 1);

        // Calculate the position of the new segment based on the direction of the tail
        int newSegmentX = tail.getX();
        int newSegmentY = tail.getY();
        switch (direction) {
            case UP:
                newSegmentY++;
                break;
            case DOWN:
                newSegmentY--;
                break;
            case LEFT:
                newSegmentX++;
                break;
            case RIGHT:
                newSegmentX--;
                break;
        }

        // create a new body segment and add it to the end of the segments list
        SnakeSegment newSnakeSegment = new SnakeSegment(newSegmentX, newSegmentY, false);
        snakeSegments.add(newSnakeSegment);
    }
}


class SnakeSegment  {
    private int x;
    private int y;
    private boolean isHead;

    public SnakeSegment(int x, int y, boolean isHead) {
        this.x = x;
        this.y = y;
        this.isHead = isHead;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

