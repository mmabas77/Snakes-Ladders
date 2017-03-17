package com.example.nashm.snakesandladders;

/**
 * Created by nashm on 12/03/2017.
 */
public class Box{
    public int[] coord;
    private boolean isPortal;
    public Box portalTo;
    public int index;

    Box(){
        coord=new int[2];
        isPortal=false;
        portalTo = null;
        index =0;
    }

    public void setCoord(int[] c){
        coord = c;
    }

    public void setPortalTo(Box x){
        portalTo = x;
    }

    public int[] getCoord(){
        return coord;
    }
}
