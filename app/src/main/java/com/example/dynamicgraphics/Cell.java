package com.example.dynamicgraphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

class Cell{
   private boolean alive;
   private boolean nextAlive = false;
   private final int[] pos = {0,0};
   private final int size;
   private ArrayList<Cell> neighbours;
   private boolean tempAlive = false;
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
      paint.setStyle(Paint.Style.FILL_AND_STROKE);
      //if the cell is alive then we draw it. TempAlive is when we are placing a shape.
      if(alive || tempAlive){
         paint.setColor(Color.parseColor("#00ff00"));
      }
      else{
         paint.setColor(Color.parseColor("#000000"));
      }
      getCanvas.drawRect(new Rect(pos[0], pos[1],pos[0]+size, pos[1]+size), paint);
   }
   //change alive status when we are placing shapes
   public void changeAlive(){
      alive = !alive;
      nextAlive = alive;
   }
   public void calcAlive(){
      int numNeighboursAlive = 0;
      for(int i = 0; i < neighbours.size(); i++){
         if(neighbours.get(i).getAlive()){
            numNeighboursAlive++;
         }
      }
      if(!alive){
         if(numNeighboursAlive == 3){
            nextAlive = true;
         }
      }
      else{
         if(numNeighboursAlive < 2 || numNeighboursAlive > 3){
            nextAlive = false;
         }
      }
   }

   public boolean getAlive(){
      return alive;
   }
   //change alive status for the next frame
   public void change(){
      alive = nextAlive;
   }

   public void setTempAlive(boolean tempAlive) {
      this.tempAlive = tempAlive;
   }

   public void insertTempAlive(){
      alive = true;
      nextAlive = true;
   }
   public void reset(){
      alive = false;
      nextAlive = false;
      tempAlive = false;
   }
}
