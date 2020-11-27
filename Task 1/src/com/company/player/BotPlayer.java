package player;

import checker.Maps;
import graph.AdjMapsChessDigraph;

public class BotPlayer extends Player{

    public BotPlayer(String name, boolean moveUp) {
        super(name, moveUp);
    }

    @Override
    public String playersMove(Player opponent, AdjMapsChessDigraph digraph, Maps maps) {
        return PlayerServes.moveBot(this, opponent, maps, digraph);
    }
}
