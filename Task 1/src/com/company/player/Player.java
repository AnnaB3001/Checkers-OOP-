package player;

import checker.Figure;
import checker.Maps;
import graph.AdjMapsChessDigraph;

import java.util.HashMap;
import java.util.Map;

public abstract class Player {
    private String name;
    private Map<Figure, String> cells = new HashMap<>();
    private Map<String, Figure> figures = new HashMap<>();
    protected boolean moveUp;

    public Player(String name, boolean moveUp) {
        this.name = name;
        this.moveUp = moveUp;
    }

    public abstract String playersMove(Player opponent, AdjMapsChessDigraph digraph, Maps maps);

    public String getName() {
        return name;
    }

    public Map<Figure, String> getCells() {
        return cells;
    }

    public Map<String, Figure> getFigures() {
        return figures;
    }

    public boolean isMoveUp() {
        return moveUp;
    }
}
