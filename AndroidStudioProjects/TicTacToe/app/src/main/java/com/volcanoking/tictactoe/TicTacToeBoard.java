package com.volcanoking.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private boolean winningLine = false;

    private final Paint paint = new Paint();

    private GameCode game = new GameCode();

    private int cellSize = getWidth()/3;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor,0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor,0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor,0);

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimensions/3;

        setMeasuredDimension(dimensions, dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

        // drawX(canvas, 1, 1); // Test drawing "X" in the middle of teh board

        // drawO(canvas, 0,2); // Test drawing "O" on the top-right corner of the board
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            int r = (int) Math.ceil(y/cellSize);
            int c = (int) Math.ceil(x/cellSize);

            //Ensures that the game will disable when boolean winningLine is TRUE
            if(!winningLine) {
                if (game.updateGameBoard(r, c)) {
                    invalidate();

                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    // Switch player's turn after move
                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
            }
            invalidate();

            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for(int c = 1; c < 3; c++){
            canvas.drawLine(cellSize*c, 0, cellSize*c, canvas.getWidth(), paint);
        }

        for(int r = 1; r < 3; r++){
            canvas.drawLine(0, cellSize*r, canvas.getWidth(), cellSize*r, paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for(int r=0; r<3;r++){
            for(int c=0; c<3; c++){
                if(game.getGameBoard()[r][c] != 0){
                    if(game.getGameBoard()[r][c] == 1){
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int r, int c){
        paint.setColor(XColor);

        canvas.drawLine((float) ((c+1)*cellSize - cellSize*0.3),
                        (float) (r*cellSize + cellSize*0.3),
                        (float) (c*cellSize + cellSize*0.3),
                        (float) ((r+1)*cellSize - cellSize*0.3),
                        paint);
        canvas.drawLine((float) (c*cellSize + cellSize*0.3),
                        (float) (r*cellSize + cellSize*0.3),
                        (float) ((c+1)*cellSize - cellSize*0.3),
                        (float) ((r+1)*cellSize - cellSize*0.3),
                        paint);
    }

    private void drawO(Canvas canvas, int r, int c){
        paint.setColor(OColor);

        canvas.drawOval((float) (c*cellSize + cellSize*0.3),
                        (float) (r*cellSize + cellSize*0.3),
                        (float) ((c*cellSize+cellSize) - cellSize*0.3),
                        (float) ((r*cellSize+cellSize) - cellSize*0.3),
                        paint);
    }

    private void drawHorizontalLine(Canvas canvas, int r, int c){
        canvas.drawLine(c, r*cellSize + cellSize/2, cellSize*3,
                r*cellSize + cellSize/2, paint);
    }

    private void drawVerticalLine(Canvas canvas, int r, int c){
        canvas.drawLine(c*cellSize + cellSize/2, r, c*cellSize + cellSize/2,
                cellSize*3, paint);
    }

    private void drawPosDiagLine(Canvas canvas){
        canvas.drawLine(0, cellSize*3, cellSize*3,
                0, paint);
    }

    private void drawNegDiagLine(Canvas canvas){
        canvas.drawLine(0, 0, cellSize*3,
                cellSize*3, paint);
    }

    public void setupGame(Button playAgain, Button home, TextView playerDisplay, String[] names){
        game.setHomeBtn(home);
        game.setPlayAgainBtn(playAgain);
        game.setPlayerNames(names);
        game.setPlayerTurn(playerDisplay);
    }

    private void drawWinningLine(Canvas canvas){
        int r = game.getWinType()[0]; // Row
        int c = game.getWinType()[1]; // Column

        /*
        Determine line type (1: Horizontal
                             2: Vertical
                             3: Negative Slope
                             4: Positive Slope)
        */
        switch (game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, r, c);
                break;
            case 2:
                drawVerticalLine(canvas, r, c);
                break;
            case 3:
                drawNegDiagLine(canvas);
                break;
            case 4:
                drawPosDiagLine(canvas);
                break;
            case 5:
                break;
        }
    }

    public void resetGame(){
        game.reset();
        winningLine = false;
    }
}
