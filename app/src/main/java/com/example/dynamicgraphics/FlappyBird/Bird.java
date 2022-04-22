package com.example.dynamicgraphics.FlappyBird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Bird {
   private final float[] pos = {50,0};
   private final float size = 30.0f;
   private float destination;
   private final float viewHeight;
   Bird(float height){
      pos[1] = height/2;
      destination = pos[1];
      viewHeight = height;
   }


   public void draw(Canvas c, Paint p){
      p.setColor(Color.parseColor("#000000"));
      c.drawCircle(pos[0], pos[1], size, p);
   }

   public void flyUp(){
      destination = pos[1] - (viewHeight/20);
   }
   public void move(){
      float speed = 10;
      if (pos[1] > destination) {
         pos[1] -= speed;
         if (pos[1] < destination) {
            pos[1] = destination;
         }
      } else {
         pos[1] += speed;
         if (pos[1] > destination) {
            float gravity = 10;
            destination += gravity * 2;
         }
      }
   }
   public boolean outOfBounds(){
      if(pos[1] <= 0 ) return true;
      else return pos[1] >= viewHeight;
   }

   public float[] getPos(){
      return pos;
   }

   public float getSize() {
      return size;
   }
}
