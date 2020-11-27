package checker;

public enum Direction {
    UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT;

    public static Direction getOppositeDirection(Direction direction){
        switch (direction) {
            case UP_RIGHT:
                return DOWN_LEFT;
            case DOWN_LEFT:
                return UP_RIGHT;
            case UP_LEFT:
                return DOWN_RIGHT;
            case DOWN_RIGHT:
                return UP_LEFT;
        }
        return null;
    }
}
