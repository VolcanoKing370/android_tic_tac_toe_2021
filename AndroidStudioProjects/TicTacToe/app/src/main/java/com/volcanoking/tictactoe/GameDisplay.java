package com.volcanoking.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);
        Button playAgainBtn = findViewById(R.id.playAgain);
        Button homeBtn = findViewById(R.id.home);
        TextView playerTurn = findViewById(R.id.turnDisplay);

        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");
        if (playerNames != null){
            playerTurn.setText((playerNames[0]) + "'s Turn!");
        }

        board = findViewById(R.id.ticTacToeBoard);

        board.setupGame(playAgainBtn, homeBtn, playerTurn, playerNames);
    }

    public void playAgainBtnClicked(View view){
        board.resetGame();
        board.invalidate();
    }

    public void homeBtnClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}