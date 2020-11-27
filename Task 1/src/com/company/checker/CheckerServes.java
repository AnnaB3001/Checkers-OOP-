package checker;

import graph.AdjMapsChessDigraph;
import graph.GraphServes;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckerServes {
    public static boolean checkMoveFigure(String move, AdjMapsChessDigraph digraph, Player attackPlayer, Player defendPlayer, Maps maps) {
        move = move.toLowerCase();
        String[] moves = move.split(" ");
        if (moves[0].charAt(0) < 'a' || moves[0].charAt(0) > 'h' || moves[1].charAt(0) < 'a' || moves[1].charAt(0) > 'h' ||
                moves[0].charAt(1) < 49 || moves[0].charAt(1) > 56 || moves[1].charAt(1) < 49 || moves[1].charAt(1) > 56) {

            System.out.println("You have selected a non-existing coordinate.");
            return false;
        }

        Figure figure = attackPlayer.getFigures().get(moves[0]);

        if (figure == null) {
            System.out.println("Is not you figure.");
            return false;
        }

        if (maps.getFigures().get(moves[1]) != null) {
            System.out.println("Figure already standing here.");
            return false;
        }

        if (figure instanceof Checker) {
            return checkCheckerMove(moves, digraph, attackPlayer, defendPlayer, maps);
        }
        if (figure instanceof King) {
            return checkMoveKing(moves, digraph, attackPlayer, defendPlayer, maps);
        }

        return false;
    }

    private static boolean checkMoveKing(String[] moves, AdjMapsChessDigraph digraph, Player attackPlayer, Player defendPlayer, Maps maps) {
        String bufferedNowStay;
        List<Figure> deathFigure = new ArrayList<>();

        for (Map.Entry<Direction, String> entry : digraph.getDirections(moves[0]).entrySet()) {
            Direction direction = entry.getKey();
            bufferedNowStay = moves[0];
            do {

                if (defendPlayer.getFigures().get(digraph.getCell(bufferedNowStay, direction)) != null) {
                    String lastPosition = attack(deathFigure, bufferedNowStay, moves[1], digraph, attackPlayer, defendPlayer, maps, null);
                    if (lastPosition != null) {
                        String move = moves[0] + " " + lastPosition;
                        moveFigure(move, deathFigure, attackPlayer, defendPlayer, maps);
                        return true;
                    }
                }

                bufferedNowStay = digraph.getCell(bufferedNowStay, direction);
                if (bufferedNowStay == null) {
                    break;
                }
                if (bufferedNowStay.equals(moves[1])) {
                    moveFigure(moves[0] + " " + moves[1], deathFigure, attackPlayer, defendPlayer, maps);
                    return true;
                }

            } while (maps.getFigures().get(bufferedNowStay) == null);
        }

        return false;
    }

    private static boolean checkCheckerMove(String[] moves, AdjMapsChessDigraph digraph, Player attackPlayer, Player defendPlayer, Maps maps) {
        Direction left = attackPlayer.isMoveUp() ? Direction.UP_LEFT : Direction.DOWN_LEFT;
        Direction right = attackPlayer.isMoveUp() ? Direction.UP_RIGHT : Direction.DOWN_RIGHT;

        List<Figure> deathFigure = new ArrayList<>();

        for (Map.Entry<Direction, String> entry : digraph.getDirections(moves[0]).entrySet()) {
            if (entry.getKey() == left || entry.getKey() == right) {
                if (entry.getValue().equals(moves[1])) {
                    moveFigure(moves[0] + " " + moves[1], deathFigure, attackPlayer, defendPlayer, maps);
                    return true;
                }
            }
        }

        String lastPosition = attack(deathFigure, moves[0], moves[1], digraph, attackPlayer, defendPlayer, maps, null);
        if (lastPosition != null) {
            String move = moves[0] + " " + lastPosition;
            moveFigure(move, deathFigure, attackPlayer, defendPlayer, maps);
            checkerToKing(lastPosition, attackPlayer, maps);
            return true;
        }
        return false;
    }


    public static String attack(List<Figure> deathFigure, String nowStay, String attackStay, AdjMapsChessDigraph digraph, Player attackPlayer, Player defendPlayer, Maps maps, Direction direction) {

        for (Map.Entry<Direction, String> entry : digraph.getDirections(nowStay).entrySet()) {

            if (Direction.getOppositeDirection(entry.getKey()) == direction) {
                continue;
            }

            if (defendPlayer.getFigures().get(entry.getValue()) != null) {
                String checkStay = digraph.getDirections(entry.getValue()).get(entry.getKey());
                if(checkStay != null) {
                    if (maps.getFigures().get(checkStay) == null) {
                        String lastPosition = attack(deathFigure, checkStay, attackStay, digraph, attackPlayer, defendPlayer, maps, entry.getKey());
                        if (lastPosition != null) {
                            deathFigure.add(defendPlayer.getFigures().get(entry.getValue()));
                            return lastPosition;
                        }
                    }
                }
            }
        }

        if (nowStay.equals(attackStay) || maps.getFigures().get(nowStay) == null) {
            return nowStay;
        }
        return null;
    }


    public static void moveFigure(String move, List<Figure> deathList, Player attackPlayer, Player defenderPlayer, Maps maps) {

        move = move.toLowerCase();
        String[] moves = move.split(" ");

        for (Figure corpse : deathList) {
            String cell = defenderPlayer.getCells().get(corpse);
            defenderPlayer.getCells().remove(corpse);
            defenderPlayer.getFigures().remove(cell);
            maps.getCells().remove(corpse);
            maps.getFigures().remove(cell);
        }

        Figure figure = attackPlayer.getFigures().get(moves[0]);
        String cell = attackPlayer.getCells().get(figure);

        attackPlayer.getFigures().remove(cell);
        attackPlayer.getCells().remove(figure);
        maps.getCells().remove(figure);
        maps.getFigures().remove(cell);

        attackPlayer.getFigures().put(moves[1], figure);
        attackPlayer.getCells().put(figure, moves[1]);
        maps.getFigures().put(moves[1], figure);
        maps.getCells().put(figure, moves[1]);
    }

    public static void checkerToKing(String cell, Player player, Maps maps){
        if(cell.charAt(1) == '1' || cell.charAt(1) == '8'){
            Figure figure = player.getFigures().get(cell);
            Figure king = new King();
            player.getFigures().put(cell, king);
            player.getCells().remove(figure);
            player.getCells().put(king, cell);
            maps.getFigures().put(cell, king);
            maps.getCells().remove(figure);
            maps.getCells().put(king, cell);
        }
    }

    public static void game(Board board){
        create(board);
        String move;
        System.out.println(board.toString());
        int counter = 0;
        while (board.getFirstPlayer().getFigures().size() > 0 && board.getSecondPlayer().getFigures().size() > 0){
            move = board.getFirstPlayer().playersMove(board.getSecondPlayer(), board.getDigraph(), board.getMaps());
            CheckerServes.checkMoveFigure(move, board.getDigraph(), board.getFirstPlayer(), board.getSecondPlayer(), board.getMaps());
            counter++;
            System.out.println(board.toString());
            if(!(board.getFirstPlayer().getFigures().size() > 0 && board.getSecondPlayer().getFigures().size() > 0)){
                break;
            }
            move = board.getSecondPlayer().playersMove(board.getFirstPlayer(), board.getDigraph(), board.getMaps());
            CheckerServes.checkMoveFigure(move, board.getDigraph(), board.getSecondPlayer(), board.getFirstPlayer(), board.getMaps());
            counter++;
            System.out.println(board.toString());
        }
        System.out.println("Было сделано: " + counter + " ходов.");
    }

    public static void create(Board board) {

        List<String> cellList = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            for (char j = 'a'; j < 'i'; j++) {
                if (i % 2 != 0) {
                    if ((j - 'a') % 2 == 0) {
                        cellList.add(j + Integer.toString(i));
                    }
                } else {
                    if ((j - 'a') % 2 != 0) {
                        cellList.add(j + Integer.toString(i));
                    }
                }
            }
        }

        String bufferCell;
        Figure bufferFigure;

        for (int i = 0; i < 12; i++) {
            bufferCell = cellList.get(i);
            bufferFigure = new Checker();

            board.getMaps().getFigures().put(bufferCell, bufferFigure);
            board.getMaps().getCells().put(bufferFigure, bufferCell);
            board.getFirstPlayer().getFigures().put(bufferCell, bufferFigure);
            board.getFirstPlayer().getCells().put(bufferFigure, bufferCell);
        }

        for (int i = 0; i < 12; i++) {
            bufferCell = cellList.get(i + 20);
            bufferFigure = new Checker();

            board.getMaps().getFigures().put(bufferCell, bufferFigure);
            board.getMaps().getCells().put(bufferFigure, bufferCell);
            board.getSecondPlayer().getFigures().put(bufferCell, bufferFigure);
            board.getSecondPlayer().getCells().put(bufferFigure, bufferCell);
        }

        board.setDigraph(GraphServes.createGraphBoard(cellList));
    }
}
