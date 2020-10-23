package brykhanova.gameobjects;

public class Checker {
    private Player player;
    private Color color;
    private CheckerType type;

    public Checker(Player player, Color color, CheckerType type) {
        this.player = player;
        this.color = color;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public CheckerType getType() {
        return type;
    }

    public void setType(CheckerType type) {
        this.type = type;
    }
}
