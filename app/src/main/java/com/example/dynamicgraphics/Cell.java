package com.example.dynamicgraphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class Cell{
   private boolean alive = false;
   private boolean nextAlive = false;
   private int pos[] = {0,0};
   private int size = 32;
   private Canvas c;
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
      c = getCanvas;
      paint.setStyle(Paint.Style.FILL_AND_STROKE);
      if(alive ||tempAlive){
         paint.setColor(Color.parseColor("#00ff00"));
      }
      else{
         paint.setColor(Color.parseColor("#000000"));
      }
      c.drawRect(new Rect(pos[0], pos[1],pos[0]+size, pos[1]+size), paint);
   }
   //during pause
   public void changeAlive(){
      alive = !alive;
      nextAlive = alive;
      printNeighbours();
   }
   public void calcAlive(){
      Log.d("asd33", "ddd");
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
   //for started application
   public void setAlive(){
      nextAlive = !alive;
   }

   public boolean getAlive(){
      return alive;
   }
   public void change(){
      alive = nextAlive;
   }
   public void printNeighbours(){
      Log.d("asd4", "x: " + pos[0]/size + " y: " + pos[1]/size);
      for(int i = 0; i < neighbours.size(); i++){
         Log.d("asd2", "x: " + neighbours.get(i).getPos()[0]/size + " y: " + neighbours.get(i).getPos()[1]/size);
      }
   }

   public int[] getPos() {
      return pos;
   }

   public void setTempAlive(boolean tempAlive) {
      this.tempAlive = tempAlive;
   }

   public boolean isTempAlive() {
      return tempAlive;
   }

   public void insertTempAlive(){
      alive = true;
      nextAlive = true;
   }
}
