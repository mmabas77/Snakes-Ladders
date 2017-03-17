package com.example.nashm.snakesandladders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by nashm on 11/03/2017.
 */
public class Board {
    private Bitmap bitmap;
    private int x;
    private int y;
    private ArrayList<Integer> ladders;
    private ArrayList<Integer> snakes;
    public Box[] boxes;
    private int size;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Board(Context context){
        x = 40;
        y = 200;
        size = 100;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.board);
        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
        snakes = new ArrayList<Integer>();
        ladders = new ArrayList<Integer>();

        boxes = new Box[size];
        for(int i=0; i<size; i++){
            boxes[i] = new Box();
        }

        int[] coordinates={40,1100};
        boolean right = true;

        for(int i =0; i< 100; i++){
            for (int co=0; co<2; co+=2){
                if(i%10==0){
                    System.out.println("do nothing");
                }
                else if(right==true){
                    coordinates[0]+=size;
                }
                else if(right==false){
                    coordinates[0]-=size;
                }
//                boxes[i].setCoord(coordinates);
                this.boxes[i].coord[0]= coordinates[0];
                this.boxes[i].coord[1]=coordinates[1];
                this.boxes[i].index = i;
            }
            if((i+1)%10==0 && i>0){
                coordinates[1]-=size;
                right = !right;
            }
        }
        //defining ladders
        boxes[5].setPortalTo(boxes[68]);
        boxes[7].setPortalTo(boxes[44]);
        boxes[11].setPortalTo(boxes[50]);
        boxes[26].setPortalTo(boxes[74]);
        boxes[36].setPortalTo(boxes[83]);
        boxes[41].setPortalTo(boxes[78]);
        ladders.add(5);
        ladders.add(7);
        ladders.add(11);
        ladders.add(26);
        ladders.add(36);
        ladders.add(41);
        ladders.add(99); //add the last box as a ladder to increase chances of reaching it during computers difficulty
        //defining snakes
        boxes[40].setPortalTo(boxes[2]);
        boxes[64].setPortalTo(boxes[14]);
        boxes[54].setPortalTo(boxes[12]);
        boxes[73].setPortalTo(boxes[27]);
        boxes[88].setPortalTo(boxes[31]);
        boxes[82].setPortalTo(boxes[38]);
        boxes[95].setPortalTo(boxes[44]);
        snakes.add(40);
        snakes.add(64);
        snakes.add(54);
        snakes.add(73);
        snakes.add(88);
        snakes.add(82);
        snakes.add(95);
    }

    public void update(){

    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX(){
        return x;
    }

    public boolean findInLadders(int x){
        if(ladders.contains(x))
            return true;
        return false;
    }
    public boolean findInSnakes(int x){
        if(snakes.contains(x))
            return true;
        return false;
    }
    public int getY(){
        return y;
    }

    public int findNextLadder(int n){
        for (int i =0; i< ladders.size(); i++){
            if (ladders.get(i)>n){
                return ladders.get(i);
            }
        }
        return 0;
    }

    public int findNextSnake(int n){
        for (int i =0; i< snakes.size(); i++){
            if (snakes.get(i)>n){
                return snakes.get(i);
            }
        }
        return 0;
    }
}

