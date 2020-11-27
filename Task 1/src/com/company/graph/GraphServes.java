package graph;

import java.util.List;

public class GraphServes {
    public static AdjMapsChessDigraph createGraphBoard(List<String> cellList) {
        AdjMapsChessDigraph digraph = new AdjMapsChessDigraph();


        boolean[] buffer = new boolean[4];

        for (int i = 0; i < 32; i++) {

            buffer[0] = true;
            buffer[1] = true;
            buffer[2] = true;
            buffer[3] = true;

            checkDirection(buffer, i);

            addCell(cellList, buffer, i, digraph);
        }

        return digraph;
    }

    private static void addCell(List<String> cells, boolean[] buffer, int i, AdjMapsChessDigraph digraph) {
        int upLeft = (i) / 4 % 2 != 0 ? 4 : 3;
        int upRight = i / 4 % 2 != 0 ? 5 : 4;
        int downLeft = i / 4 % 2 != 0 ? 4 : 5;
        int downRight = i / 4 % 2 != 0 ? 3 : 4;
        if (buffer[0]) {
            digraph.addEdge(cells.get(i), cells.get(i + upLeft));
        }
        if (buffer[1]) {
            digraph.addEdge(cells.get(i), cells.get(i + upRight));
        }
        if (buffer[2]) {
            digraph.addEdge(cells.get(i), cells.get(i - downRight));
        }
        if (buffer[3]) {
            digraph.addEdge(cells.get(i), cells.get(i - downLeft));
        }
    }

    private static void checkDirection(boolean[] buffer, int i) {
        if (checkLeftBorder(i + 1)) {
            buffer[0] = false;
            buffer[3] = false;
        }

        if (checkRightBorder(i + 1)) {
            buffer[1] = false;
            buffer[2] = false;
        }

        if (i + 1 > 28) {
            buffer[0] = false;
            buffer[1] = false;
        }

        if (i + 1 < 5) {
            buffer[2] = false;
            buffer[3] = false;
        }
    }

    private static boolean checkLeftBorder(int i) {
        return (i - 1) % 8 == 0;
    }

    private static boolean checkRightBorder(int i) {
        return i % 8 == 0;
    }
}
