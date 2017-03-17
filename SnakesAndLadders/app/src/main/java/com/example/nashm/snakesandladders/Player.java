package com.example.nashm.snakesandladders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;

/**
 * Created by nashm on 11/03/2017.
 */
public class Player {
    //Player variables
    private Bitmap bitmap;
    private int x;
    private int y;
    private int yourMove;
    private int playerMove;         //current position of player with respect to the board i.e. 3 means 3rd box
    private boolean canPlayerMove;  //boolean if the player can move or not i.e. after being stuck on snake
    public boolean extraMove;       //boolean if player gets extra move i.e. after hitting a ladder

    //player contructor
    public Player(Context context, int draw){
        x = 40;
        y = 1100;
        playerMove=0;
        canPlayerMove=true;

        //bitmaps to draw the player
        bitmap = BitmapFactory.decodeResource(context.getResources(), draw);
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

    }

    public void update(){

    }
    //generate a random number between 1 and 6
    public int randomNumber(){
        Random rand = new Random();
        int  diceNumber = rand.nextInt(6) + 1;
        return diceNumber;
    }

    //generate a biased random number for the computer with higher difficulties
    /*chances = difficulty. greater "chances" increases the chances of the number occuring
    * the function adds the desired number to an array along with number 1-6 n times (based on chances) and then randomly
    * picks a number from this array*/
    public int biasedRandomNumber(int bias, double chances){
        ArrayList<Integer> bin = new ArrayList<Integer>();
        Random x = new Random();
        System.out.println(Math.ceil(2*chances));
        for(int i =1; i <7; i++){
            bin.add(i);
        }

        for(int j=0; j<Math.ceil(chances*10); j++){
            System.out.println(j);
            bin.add(bias);
        }

        int numChosen = bin.get(x.nextInt(bin.size()-1)+1);
        return numChosen;
    }

    //function for rolling computer's dice
    public int compRollAndMove(GameView gameView, int nextLadder ,double difficulty){
        yourMove = biasedRandomNumber(nextLadder, difficulty);
        //if the player can't move and doesn't play a 6, or if the player is near the end and rolls a dice that results in his postion
        //at more than 100
        if (!canPlayerMove && yourMove != 6 || yourMove+playerMove>99){
            yourMove =0;
        }
        //if the player can't move and rolls a 6
        else if(!canPlayerMove && yourMove==6)
            canPlayerMove = true;
        //if the player rolls a 6 he gets an extra move
        else if(yourMove==6){
            extraMove = true;
        }
        playerMove += yourMove;

        Box currentBox = new Box();
        //set currentbox to the player position after rolling the dice
        //this is to get the coordinates of the box in order to move the player
        currentBox = gameView.board.boxes[playerMove];
        //if the box contains a portal i.e. ladder or snake
        if(currentBox.portalTo != null){
            //if the player hits a snake, he cannot move
            if(gameView.board.findInSnakes(playerMove)){
                canPlayerMove = false;
            }
            //if the player hits a ladder he gets an extra move
            else if(gameView.board.findInLadders(playerMove) ){
                extraMove = true;
            }
            //set player position to current
            currentBox = currentBox.portalTo;
            playerMove = currentBox.index;
        }

        int[] coordinates = currentBox.getCoord();
        //set coordinates of th player
        this.x=coordinates[0];
        this.y=coordinates[1];

        //return current position box number
        return yourMove;
    }
    public int rollAndMove(GameView gameView){
        yourMove = randomNumber();
        if (!canPlayerMove && yourMove != 6 || playerMove+yourMove>99){
            yourMove =0;
        }
        else if(!canPlayerMove && yourMove==6)
            canPlayerMove = true;
        else if(yourMove==6){
            extraMove = true;
        }
        playerMove += yourMove;

        Box currentBox = new Box();
        currentBox = gameView.board.boxes[playerMove];
        if(currentBox.portalTo != null){
            if(gameView.board.findInSnakes(playerMove)){
                canPlayerMove = false;
            }
            else if(gameView.board.findInLadders(playerMove) ){
                extraMove = true;
            }
            currentBox = currentBox.portalTo;
            playerMove = currentBox.index;
        }

        int[] coordinates = currentBox.getCoord();

        this.x=coordinates[0];
        this.y=coordinates[1];

        return yourMove;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getPlayerMove(){return playerMove;}

    public void setCanPlayerMove(boolean x){
        canPlayerMove = x;
    }

    public boolean getCanPlayerMove(){
        return canPlayerMove;
    }

    public void reset(){
        canPlayerMove=true;
        x=40;
        y=1100;
    }

    public void setX(int i){
        x = i;
    }
    public void setY(int i){
        y = i;
    }
}

