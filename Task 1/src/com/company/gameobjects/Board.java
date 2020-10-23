package com.company.gameobjects;

public class Board {
    Checker[][] board = new Checker[8][8];
    Player player1;
    Player player2;

    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        placeCheckers();
    }

    private void placeCheckers(){
        board[0][0] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[0][2] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[0][4] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[0][6] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[1][1] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[1][3] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[1][5] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[1][7] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[2][0] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[2][2] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[2][4] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);
        board[2][6] = new Checker(player1,Color.WHITE,CheckerType.NORMAL);

        board[5][1] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[5][3] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[5][5] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[5][7] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[6][0] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[6][2] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[6][4] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[6][6] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[7][1] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[7][3] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[7][5] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);
        board[7][7] = new Checker(player2,Color.BLACK,CheckerType.NORMAL);

    }
}
