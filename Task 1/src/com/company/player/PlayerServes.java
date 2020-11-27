package player;

import checker.*;
import graph.AdjMapsChessDigraph;

import java.util.*;

public class PlayerServes {

    public static String moveRealPlayer(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public static String moveBot(Player player, Player opponent, Maps maps, AdjMapsChessDigraph digraph) {

        if (!(player instanceof BotPlayer)) {
            return null;
        }

        List<MoveAndScore> listMoves = new ArrayList<>();

        String toGo = "";
        int maxScore = Integer.MIN_VALUE;

        for (Map.Entry<String, Figure> entry : player.getFigures().entrySet()) {
            if(entry.getValue() instanceof Checker){
                listMoves.add(checkMoveChecker(entry.getKey(), player, opponent, maps, digraph));
            }
            if(entry.getValue() instanceof King){
                listMoves.add(checkMoveKing(entry.getKey(), player, opponent, maps,digraph));
            }
        }

        for(MoveAndScore check :  listMoves){
            if(check.score > maxScore){
                maxScore = check.score;
                toGo = check.move;
            }
        }

        return toGo;
    }

    private static MoveAndScore checkMoveKing(String nowStay, Player player, Player opponent, Maps maps, AdjMapsChessDigraph digraph){
        MoveAndScore moveAndScore = new MoveAndScore();

        moveAndScore.score = 0;
        moveAndScore.move = nowStay + " ";
        List<Figure> deathFigure = new ArrayList<>();
        List<MoveAndScore> scoreList = new ArrayList<>();
        String bufferedNowStay;

        for (Map.Entry<Direction, String> entry : digraph.getDirections(nowStay).entrySet()) {
            Direction direction = entry.getKey();
            bufferedNowStay = nowStay;
            do {

                if (opponent.getFigures().get(digraph.getCell(bufferedNowStay, direction)) != null) {
                    String lastPosition = CheckerServes.attack(deathFigure, bufferedNowStay, nowStay, digraph, player, opponent, maps, null);
                    if (lastPosition != null) {
                        moveAndScore.move += lastPosition;
                        moveAndScore.score += deathFigure.size() * 10;
                        return moveAndScore;
                    }
                }

                MoveAndScore buffer = new MoveAndScore();
                buffer.move = nowStay + " " + bufferedNowStay;

                if(checkSave(bufferedNowStay, new ArrayList<>(Collections.singleton(entry.getKey())),
                        player, opponent, digraph)){
                    buffer.score += 5;
                }

                if(checkAttackFigureKing(bufferedNowStay, opponent, digraph, maps)){
                    buffer.score += 9;
                }

                scoreList.add(buffer);

                bufferedNowStay = digraph.getCell(bufferedNowStay, direction);

                if (bufferedNowStay == null) {
                    break;
                }

            } while (maps.getFigures().get(bufferedNowStay) == null);
        }


        MoveAndScore maxScore = new MoveAndScore();
        maxScore.score = Integer.MIN_VALUE;

        for(MoveAndScore checker : scoreList){
            if(checker.score > maxScore.score){
                maxScore = checker;
            }
        }
        return maxScore;
    }

    private static boolean checkAttackFigureKing(String nowStay, Player opponent, AdjMapsChessDigraph digraph, Maps maps){

        String bufferedNowStay;

        for (Map.Entry<Direction, String> entry : digraph.getDirections(nowStay).entrySet()) {
            Direction direction = entry.getKey();
            bufferedNowStay = nowStay;
            do {
                if(opponent.getFigures().get(digraph.getCell(bufferedNowStay, direction)) != null){
                    String bufferCell = digraph.getCell(bufferedNowStay, direction);
                    if(maps.getFigures().get(digraph.getCell(bufferCell, direction)) == null) {
                        return true;
                    }
                }

                bufferedNowStay = digraph.getCell(bufferedNowStay, direction);

                if (bufferedNowStay == null) {
                    break;
                }

            } while (maps.getFigures().get(bufferedNowStay) == null);
        }
        return false;
    }

    private static MoveAndScore checkMoveChecker(String nowStay, Player player, Player opponent, Maps maps, AdjMapsChessDigraph digraph) {
        MoveAndScore moveAndScore = new MoveAndScore();

        moveAndScore.score = 0;
        moveAndScore.move = nowStay + " ";

        List<Figure> deathFigure = new ArrayList<>();

        String lastPosition = CheckerServes.attack(deathFigure, nowStay, "", digraph, null, opponent, maps, null);

        if (lastPosition != null) {
            moveAndScore.score += deathFigure.size() * 10;
            moveAndScore.move += lastPosition;
            return moveAndScore;
        }

        Direction left = player.isMoveUp() ? Direction.UP_LEFT : Direction.DOWN_LEFT;
        Direction right = player.isMoveUp() ? Direction.UP_RIGHT : Direction.DOWN_RIGHT;

        List<Direction> directions = new ArrayList<>();
        directions.add(left);
        directions.add(right);

        int scoreLeft = 0;
        int scoreRight = 0;
        boolean isWhereToGo = false;

        for (Map.Entry<Direction, String> entry : digraph.getDirections(nowStay).entrySet()) {
            String cell = digraph.getCell(nowStay, entry.getKey());
            if (entry.getKey() == left || entry.getKey() == right) {
                if (player.getFigures().get(cell) == null) {
                    isWhereToGo = true;
                    moveAndScore.score++;
                    if (checkSave(nowStay, directions, player, opponent, digraph)) {
                        if(entry.getKey() == left) {
                            scoreLeft += 5;
                        }else {
                            scoreRight += 5;
                        }                    }
                    if(checkStand(nowStay, left, right, player, opponent, digraph, maps)){
                        if(entry.getKey() == left) {
                            scoreLeft += 3;
                        }else {
                            scoreRight += 3;
                        }
                    }
                }
            }

        }

        if(isWhereToGo){
            if(scoreLeft > scoreRight || maps.getFigures().get(digraph.getCell(nowStay, left)) == null){
                moveAndScore.score += scoreLeft;
                moveAndScore.move += digraph.getCell(nowStay, left);
            }else {
                moveAndScore.score += scoreRight;
                moveAndScore.move += digraph.getCell(nowStay, right);
            }
        }

        return moveAndScore;
    }

    public static boolean checkStand(String nowStay, Direction left, Direction right, Player player, Player opponent, AdjMapsChessDigraph digraph, Maps maps){
        for (Map.Entry<Direction, String> entry : digraph.getDirections(nowStay).entrySet()) {
            String cell = digraph.getCell(nowStay, entry.getKey());
            Direction reverseDirection = Direction.getOppositeDirection(entry.getKey());
            if(cell != null) {
                if (opponent.getFigures().get(digraph.getCell(nowStay, entry.getKey())) != null) {
                    String bufferCell = digraph.getCell(nowStay, reverseDirection);
                    if(bufferCell != null){
                        if(maps.getFigures().get(bufferCell) == null){
                            bufferCell = digraph.getCell(bufferCell, reverseDirection);
                            if(bufferCell != null){
                                if(player.getFigures().get(bufferCell) != null){
                                    bufferCell = digraph.getCell(bufferCell, reverseDirection);
                                    if(bufferCell != null) {
                                        if(player.getFigures().get(bufferCell) != null) {
                                            return true;
                                        }
                                    }else {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return false;
    }

    public static boolean checkSave(String nowStay, List<Direction> directions, Player player, Player opponent, AdjMapsChessDigraph digraph) {
        for (Map.Entry<Direction, String> entry : digraph.getDirections(nowStay).entrySet()) {
            String cell = digraph.getCell(nowStay, entry.getKey());
            if (directions.contains(entry.getKey())) {
                Direction direction = entry.getKey();
                if (player.getFigures().get(cell) != null) {
                    if (digraph.getDirections(cell).get(direction) != null) {
                        if (opponent.getFigures().get(digraph.getCell(cell, direction)) != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private static class MoveAndScore {
        int score;
        String move;
    }
}
