package checker;

import graph.AdjMapsChessDigraph;
import player.BotPlayer;
import player.Player;
import player.RealPlayer;



public class Board {
    private AdjMapsChessDigraph digraph;
    private Player firstPlayer = new RealPlayer("Аркадий", true);
    private Player secondPlayer = new BotPlayer("Антон", false);
    private Maps maps = new Maps();

    public AdjMapsChessDigraph getDigraph() {
        return digraph;
    }

    public void setDigraph(AdjMapsChessDigraph digraph) {
        this.digraph = digraph;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Maps getMaps() {
        return maps;
    }

    public void setMaps(Maps maps) {
        this.maps = maps;
    }

    @Override
    public String toString() {

        String[] b = new String[32];
        int counter = -1;

        for (int i = 1; i < 9; i++) {
            for (char j = 'a'; j < 'i'; j++) {

                Figure f = maps.getFigures().get(j + Integer.toString(i));

                if (i % 2 != 0) {
                    if ((j - 'a') % 2 == 0) {
                        counter++;
                        if(f == null){
                            b[counter] = " ";
                        }else if(firstPlayer.getCells().get(f) != null){
                            if(f instanceof Checker){
                                b[counter] = "\u001B[31m" + "c" + "\u001B[0m";
                            }else {
                                b[counter] = "\u001B[31m" + "k" + "\u001B[0m";
                            }
                        }else if(secondPlayer.getCells().get(f) != null){
                            if(f instanceof Checker){
                                b[counter] = "\u001B[34m" + "c" + "\u001B[0m";
                            }else {
                                b[counter] = "\u001B[34m" + "k" + "\u001B[0m";
                            }
                        }
                    }
                } else {
                    if ((j - 'a') % 2 != 0) {
                        counter++;
                        if(f == null){
                            b[counter] = " ";
                        }else if(firstPlayer.getCells().get(f) != null){
                            if(f instanceof Checker){
                                b[counter] = "\u001B[31m" + "c" + "\u001B[0m";
                            }else {
                                b[counter] = "\u001B[31m" + "k" + "\u001B[0m";
                            }
                        }else if(secondPlayer.getCells().get(f) != null){
                            if(f instanceof Checker){
                                b[counter] = "\u001B[34m" + "c" + "\u001B[0m";
                            }else {
                                b[counter] = "\u001B[34m" + "k" + "\u001B[0m";
                            }
                        }
                    }
                }
            }
        }

        return "  ABCDEFGH\n" +
                "8 S" + b[28] + "S" + b[29] + "S" + b[30] + "S" + b[31] + "\n" +
                "7 " + b[24] + "S" + b[25] + "S" + b[26] + "S" + b[27] + "S\n" +
                "6 S" + b[20] + "S" + b[21] + "S" + b[22] + "S" + b[23] + "\n" +
                "5 " + b[16] + "S" + b[17] + "S" + b[18] + "S" + b[19] + "S\n" +
                "4 S" + b[12] + "S" + b[13] + "S" + b[14] + "S" + b[15] + "\n" +
                "3 " + b[8] + "S" + b[9] + "S" + b[10] + "S" + b[11] + "S\n" +
                "2 S" + b[4] + "S" + b[5] + "S" + b[6] + "S" + b[7] + "\n" +
                "1 " + b[0] + "S" + b[1] + "S" + b[2] + "S" + b[3] + "S\n" +
                "  ABCDEFGH\n";
    }
}
