package com.example.nashm.snakesandladders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class playActivity extends AppCompatActivity {

    private GameView gameView;
    private int playerMove=0;
    private int yourMove;
    private int AIMove=0;
    private TextView whosMove;
    private TextView moveView;
    private double difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(R.layout.activity_play);
        FrameLayout surface = (FrameLayout)findViewById(R.id.frame);
        surface.addView(gameView);  //add opengl surface to framelayout


        whosMove = (TextView) findViewById(R.id.whosMove);
        moveView = (TextView) findViewById(R.id.moveID);
        yourMove =0;
        difficulty=0;

    }

    @Override protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    @Override protected void onResume(){
        super.onResume();
        gameView.resume();
    }

    public void enemyRollDice(){
        if(gameView.AI.extraMove)
            gameView.AI.extraMove = false;
        whosMove.setText("It's Comp's turn");
        moveView.setVisibility(View.VISIBLE);
        int nextLadder = gameView.board.findNextLadder(gameView.player.getPlayerMove());
        yourMove = gameView.AI.compRollAndMove(gameView, nextLadder, difficulty);

        if(gameView.AI.getPlayerMove()==99)
            moveView.setText("Boohoo! You've lost");
        else if(yourMove>0)
            moveView.setText("Comp's dice rolled a " + yourMove);
        else if(yourMove==0)
            moveView.setText("Comp can't move right now");
        moveView.postDelayed(new Runnable() {
            public void run() {
                moveView.setVisibility(View.INVISIBLE);
                //conditions where you get to roll dice again
                if(yourMove==6 || gameView.AI.extraMove){
                    enemyRollDice();
                }
            }
        }, 3000);
        whosMove.setText("It's your turn");
    }

    public void rollDice(View view){
        //if the player had an extra move (which is the current move now) make it false
        if(gameView.player.extraMove)
            gameView.player.extraMove = false;

        //set indicator text to this
        whosMove.setText("It's your turn");
        moveView.setVisibility(View.VISIBLE);
        yourMove = gameView.player.rollAndMove(gameView);

        //if the player wins the stage
        if(gameView.player.getPlayerMove() == 99){
            difficulty+=0.2;
            moveView.setText("Congratulations! You've completed the stage. Your score was "+gameView.score);
            gameView.reset();
        }
        //if yourmove was 0 i.e. if player is stuck or if near 100th box but roll more than 100
        else if(yourMove>0)
            moveView.setText("Your dice rolled a " + yourMove);
        else if(yourMove==0)
            moveView.setText("You can't move right now");

        //make text appear for a while then disappear
        moveView.postDelayed(new Runnable() {
            public void run() {
                moveView.setVisibility(View.INVISIBLE);
                //conditions where you get to roll dice again (meaning computer can't roll)
                if (yourMove != 6 && !gameView.player.extraMove) {
                    enemyRollDice();
                }
            }
        }, 3000);
        whosMove.setText("It's Comp's turn");
    }
}
