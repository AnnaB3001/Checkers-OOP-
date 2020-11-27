package graph;


import checker.Direction;

import java.util.*;

public class AdjMapsChessDigraph {

    private Map<String, Map<Direction, String>> diagonal = new HashMap<>();

    private int vCountDiagonal;
    private int eCountDiagonal;

    private static Iterable<Integer> nullIterable = new Iterable<Integer>() {
        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Integer next() {
                    return null;
                }
            };
        }
    };

    public Map<Direction, String> getDirections(String v){
        return diagonal.get(v);
    }
    public String getCell(String v, Direction direction){
        return diagonal.get(v).get(direction);
    }


    public int getVCountDiagonal() {
        return vCountDiagonal;
    }

    public int getECountDiagonal() {
        return eCountDiagonal;
    }

    public void addEdge(String v1, String v2) {

        //if (!isAdj(v1, v2)) {

        if (diagonal.get(v1) == null) {

            diagonal.put(v1, new HashMap<>());
            vCountDiagonal++;
        }

        Direction d = learnDirection(v1, v2);

        diagonal.get(v1).put(d, v2);
        eCountDiagonal++;
        //}
    }

    private Direction learnDirection(String v1, String v2){
        if(v1.charAt(1) < v2.charAt(1)){
            if(v1.charAt(0) < v2.charAt(0)){
               return Direction.UP_RIGHT;
            }else {
                return Direction.UP_LEFT;
            }
        }else {
            if(v1.charAt(0) < v2.charAt(0)){
                return Direction.DOWN_RIGHT;
            }else {
                return Direction.DOWN_LEFT;
            }
        }
    }
}
