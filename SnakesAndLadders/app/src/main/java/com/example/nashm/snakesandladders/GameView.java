package com.example.nashm.snakesandladders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Created by nashm on 11/03/2017.
 */
public class GameView extends SurfaceView implements Runnable {
    volatile boolean playing;
    private Thread gameThread = null;

    public Player player;
    public Player AI;
    public Board board;
    public Game game;
    public int score;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    public GameView(Context context){
        super(context);

        player = new Player(context, R.drawable.player1);
        AI = new Player(context, R.drawable.player2);
        board = new Board(context);
        game = new Game(context);

        surfaceHolder = getHolder();
        paint = new Paint();


    }

    @Override public void run(){
        while(playing){
            update();
            draw();
            control();
        }
    }

    private void update(){

        player.update();
    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
//            canvas.drawColor(Color.BLACK);

            canvas.drawBitmap(
                    game.getBitmap(),
                    game.getX(),
                    game.getY(),
                    paint
            );

            canvas.drawBitmap(
                    board.getBitmap(),
                    board.getX(),
                    board.getY(),
                    paint
            );

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint
            );

            canvas.drawBitmap(
                    AI.getBitmap(),
                    AI.getX(),
                    AI.getY(),
                    paint
            );

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false;
        try {
            gameThread.join(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void reset(){
        player.reset();
        AI.reset();
        score+=1000;;
    }

}
