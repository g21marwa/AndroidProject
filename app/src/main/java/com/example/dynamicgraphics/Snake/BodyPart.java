package com.example.dynamicgraphics.Snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

class BodyPart {
   private final int radius = 60;
   private float[] pos = {0,0};
   private BodyPart following;
   private double directionAngle = 0.0f;
   private double targetDirection = 0.0f;
   private boolean hasPlacedInBody = false;
   public BodyPart(int width, int height, BodyPart myFollowing){
      Random random = new Random();
      pos[0] = random.nextInt(width-radius)+radius;
      pos[1] = random.nextInt(height-radius)+radius;
      if(myFollowing != null){
         following = myFollowing;
      }
   }

   public void setPos(float[] pos) {
      this.pos = pos;
   }

   public float[] getPos() {
      return pos;
   }

   public boolean collided(float[] snakePos){
      if(snakePos[0]+radius > pos[0]-radius && snakePos[0]-radius < pos[0]+radius){
         return snakePos[1] + radius > pos[1] - radius && snakePos[1] - radius < pos[1] + radius;
      }
      return false;
   }

   public void draw(Canvas c, Paint p){
      p.setColor(Color.parseColor("#00ff00"));
      c.drawCircle(pos[0], pos[1], radius, p);
   }

   public void move(){
      double speed = 5;
      double rad = directionAngle * Math.PI/180;
      double rad2 = targetDirection * Math.PI/180;
      double differenceAngle = Math.atan2(Math.sin(rad2-rad), Math.cos(rad2-rad));
      if(differenceAngle <= 0) {
         directionAngle -= Math.PI;
      }
      else{
         directionAngle += Math.PI ;
      }
      double rad3 = directionAngle * Math.PI/180;
      if(following != null){
         if(!following.collided(pos)){
            pos[0] -= Math.sin(rad3) * speed;
            pos[1] -= Math.cos(rad3) * speed;
         }
         else{
            hasPlacedInBody = true;
         }
      }
      else {
         pos[0] -= Math.sin(rad3) * speed;
         pos[1] -= Math.cos(rad3) * speed;
      }
   }

   public void setDirection(float[] gesturePos){
      double dx;
      double dy;

      if(following != null){
         double followingDirection = following.getDirectionAngle() * Math.PI/180;
         dx = pos[0] - (following.getPos()[0] - ((radius) * Math.sin(followingDirection)));
         dy = pos[1] - (following.getPos()[1] - ((radius) * Math.cos(followingDirection)));
      }
      else{
         dx = pos[0] - gesturePos[0];
         dy = pos[1] - gesturePos[1];
      }
      double theta = Math.atan2(dx, dy);
      targetDirection = theta * 180/Math.PI;

   }

   public double getDirectionAngle() {
      return directionAngle;
   }
   public void setFollowing(BodyPart follow){
      following = follow;
   }
   public boolean isHasPlacedInBody(){
      return hasPlacedInBody;
   }

   public int getRadius() {
      return radius;
   }
}
