package com.example.dynamicgraphics.Arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Block {
   private final float[] pos;
   private final float blockWidth;
   private final float blockHeight;
   boolean hit = false;
   public Block(float width, float height, float[] setPos){
      blockWidth = width;
      blockHeight = height;
      pos = setPos;
   }

   public void draw(Canvas c, Paint p){
      if(!hit){
         p.setColor(Color.parseColor("#0000ff"));
      }
      else{
         p.setColor(Color.parseColor("#000000"));
      }
      c.drawRect(pos[0], pos[1], pos[0]+blockWidth, pos[1]+blockHeight, p);
   }

   public boolean collision(Ball ball){
      if(!hit) {
         float[] ballPos = ball.getPos();
         float ballRadius = ball.getRadius();
         if (ballPos[0] + ballRadius > pos[0] && ballPos[0] - ballRadius < pos[0] + blockWidth) {
            if (ballPos[1] + ballRadius > pos[1] && ballPos[1] - ballRadius < pos[1] + blockHeight) {
               hit = true;
               double[] ballDirection = ball.getDirection();
               //top hit
               //right hit
               //bottom hit
               //right hit
               if(ballDirection[1] > 0){
                  if(ballPos[1]+ballRadius/2 < pos[1]){
                     //top hit
                     ballDirection[1] = -ballDirection[1];
                  }
                  else{
                     //left hit
                     ballDirection[0] = -ballDirection[0];
                  }
               }
               else{
                  if(ballPos[1]-ballRadius/2 > pos[1]){
                     //bottom hit
                     ballDirection[1] = -ballDirection[1];
                  }
                  else{
                     //left hit
                     ballDirection[0] = -ballDirection[0];
                  }
               }
               ball.setDirection(ballDirection);
               return true;
            }
         }
      }
      return false;
   }
   public void reset(){
      hit = false;
   }
}
