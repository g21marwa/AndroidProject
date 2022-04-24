package com.example.dynamicgraphics.Arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Ball {
   private final float radius = 30;
   private float[] pos = {0.0f,0.0f};
   private final float viewHeight;
   private final float viewWidth;
   private double[]  direction = {0,-1};
   private float speed = 20;
   public Ball(float width, float height){
      viewHeight = height;
      viewWidth = width;
      pos[0] = width/2;
      pos[1] = height/2+200;
   }

   public void draw(Canvas c, Paint p){
      p.setColor(Color.parseColor("#00ff00"));
      c.drawCircle(pos[0], pos[1], radius, p);
   }

   public void move(){
      pos[0] += direction[0] * speed;
      pos[1] += direction[1] * speed;
   }

   public boolean checkCollisionBorder(){
      if(pos[1] >= viewHeight){
         return true;
      }
      if(pos[1] <= 0){
         direction[1] = -direction[1];
      }
      if(pos[0] <= 0){
         direction[0] = -direction[0];
      }
      else if(pos[0] >= viewWidth){
         direction[0] = -direction[0];
      }
      return false;
   }

   public void setDirection(double[] direction) {
      this.direction = direction;
   }

   public float[] getPos() {
      return pos;
   }

   public float getRadius() {
      return radius;
   }

   public void setPos(float[] pos) {
      this.pos = pos;
   }

   public double[] getDirection() {
      return direction;
   }
   public void increaseSpeed (){
      speed += 5;
   }
}
