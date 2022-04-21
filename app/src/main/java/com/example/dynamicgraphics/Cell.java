package com.example.dynamicgraphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class Cell{
   private boolean alive = false;
   private int pos[] = {0,0};
   private int size = 32;
   Canvas c;
   private ArrayList<Cell> neighbours;
   Cell(int posX, int posY, int getSize, boolean l){
      pos[0] = posX;
      pos[1] = posY;
      size = getSize;
      alive = l;
      neighbours = new ArrayList<>();
   }

   public void setNeighbours(ArrayList<Cell> cells){
      neighbours = cells;
   }

   public void draw(Canvas getCanvas, Paint paint){
      c = getCanvas;
      paint.setStyle(Paint.Style.FILL_AND_STROKE);
      if(alive){
         paint.setColor(Color.parseColor("#00ff00"));
      }
      else{
         paint.setColor(Color.parseColor("#000000"));
      }
      c.drawRect(new Rect(pos[0], pos[1],pos[0]+size, pos[1]+size), paint);
   }
   public void setAlive(){
      int numNeighboursAlive = 0;
      for(int i = 0; i < neighbours.size(); i++){
         if(neighbours.get(i).getAlive()){
            numNeighboursAlive++;
         }
      }
      if(!alive){
         if(numNeighboursAlive == 3){
            alive = true;
         }
      }
      else{
         if(numNeighboursAlive < 2 || numNeighboursAlive > 3){
            alive = false;
         }
      }

   }
   public boolean getAlive(){
      return alive;
   }


}
