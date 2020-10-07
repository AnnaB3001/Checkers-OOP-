package com.company;

import java.util.ArrayList;

public class Players {
    private static int numOfPlayers = 0;
    private String name;
    private List<Сheckers> сheckersInHand;

    public Players(String name) {
        numOfPlayers++;
        this.name = name;
        this.сheckersInHand = new ArrayList<Checkers>();
    }
}
