package com.volcanoking.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameCode {
    private int [][] gameBoard;

    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;
    private String[] playerNames = {"Player 1", "Player 2"};

    private int[] winType = {-1, -1, -1}; // 1st element --> row; 2nd element --> column; 3rd element -> line type

    private int player = 1;

    GameCode(){
        gameBoard = new int[3][3];
        for(int r=0; r<3;r++){
            for(int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }
    }
    // Getter function
    public int[][] getGameBoard(){
        return gameBoard;
    }

    public boolean updateGameBoard(int r, int c){
        if(gameBoard[r-1][c-1] == 0){
            gameBoard[r-1][c-1] = player;

            if(player==1){
                playerTurn.setText((playerNames[1] + "'s Turn!"));
            } else {
                playerTurn.setText((playerNames[0] + "'s Turn!"));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean winnerCheck(){

        boolean isWinner = false;

        // Horizontal check(—)
        for(int r=0; r<3; r++){
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0) {
                isWinner = true;
                winType = new int[] {r, 0, 1};
            }
        }

        // Vertical check (|)
        for(int c=0; c<3; c++){
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] &&
                    gameBoard[0][c] != 0) {
                isWinner = true;
                winType = new int[] {0, c, 2};
            }
        }

        // m=-1 Diagonal check (\)
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0) {
            isWinner = true;
            winType = new int[] {2, 2, 3};
        }

        // m=+1 Diagonal check (/)
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0) {
            isWinner = true;
            winType = new int[] {0, 2, 4};
        }

        int boardFilled = 0;

        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                if(gameBoard[r][c] != 0){
                    boardFilled+=1;
                }
            }
        }

        if(isWinner){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player-1] + " Won!!!!"));
            return true;
        }
        else if(boardFilled == 9){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!!!");
            winType = new int[] {0, 2, 5};
            return true;
        }
        else {
            return false;
        }
    }

    public void reset(){
        for(int r=0; r<3;r++){
            for(int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }
        setPlayer(1);
        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        playerTurn.setText((playerNames[0] + "'s turn"));
    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setPlayAgainBtn(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public int getPlayer(){
        return player;
    }

    public int[] getWinType() {
        return winType;
    }
}
