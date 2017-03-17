package com.example.nashm.snakesandladders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by nashm on 11/03/2017.
 */
public class Game {
    private Bitmap background;
    private int x;
    private int y;
    private int diceNumber;

    public Game(Context context){
        x = 0;
        y = 0;
        diceNumber=0;
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_gradient);

    }

    public void update(){
    }


    public Bitmap getBitmap(){
        return background;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}

