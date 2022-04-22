package com.example.dynamicgraphics.FlappyBird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Obstacle {
   private final int viewHeight;
   private final float[] pos1 = {0,0};
   private final float[] pos2 = {0,0};
   private final float width = 80;
   //end for first part and start for second part
   private final float end;
   private boolean awardedPoints = false;
   public Obstacle(int height, int width){
      viewHeight = height;
      float minGap = viewHeight / 3.0f;
      pos1[0] = width + 50;
      pos2[0] = width + 50;
      Random random = new Random();
      float gap = minGap + random.nextFloat() * ((viewHeight / 3.0f) - minGap);
      end = gap + random.nextFloat() * ((viewHeight- gap)- gap);
      float start = end + gap;
      pos2[1] = start;
   }

   public void draw(Canvas c, Paint p){
      p.setColor(Color.parseColor("#00ff00"));
      c.drawRect(pos1[0], pos1[1], pos1[0]+width, end, p);
      c.drawRect(pos2[0], pos2[1], pos2[0]+width, viewHeight, p);
   }

   public void move(){
      int speed = 10;
      pos1[0] -= speed;
      pos2[0] -= speed;
   }
   public boolean hasPassed(float[] birdPos, float radius){
      if(!awardedPoints){
         if(birdPos[0]-radius > pos1[0]+width){
            awardedPoints = true;
            return true;
         }
      }
      return false;
   }
   public boolean collided(float[] birdPos, float radius){
      if(birdPos[0]+radius > pos1[0] && birdPos[0]-radius < pos1[0]+width){
         if(birdPos[1]+radius > pos1[1] && birdPos[1]-radius < pos1[1]+end){
            return true;
         }
         else return birdPos[1] + radius > pos2[1] && birdPos[1] - radius < pos2[1] + (viewHeight - (viewHeight - pos2[1]));
      }
      return false;
   }
}
