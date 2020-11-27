package player;

import checker.Maps;
import graph.AdjMapsChessDigraph;

public class RealPlayer extends Player {

    public RealPlayer(String name, boolean moveUp) {
        super(name, moveUp);
    }

    @Override
    public String playersMove(Player opponent, AdjMapsChessDigraph digraph, Maps maps) {
        return PlayerServes.moveRealPlayer();
    }
}
