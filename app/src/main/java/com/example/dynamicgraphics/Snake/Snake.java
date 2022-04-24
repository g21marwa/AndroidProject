package com.example.dynamicgraphics.Snake;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

class Snake {
   private final ArrayList<BodyPart> body;
   private final int viewHeight;
   private final int viewWidth;

   public Snake(int width, int height){
      body = new ArrayList<>();
      body.add(new BodyPart(width, height, null));
      float[] pos = {width/2.0f, height/2.0f};
      body.get(0).setPos(pos);
      viewHeight= height;
      viewWidth = width;
   }

   public void draw(Canvas c, Paint p){
      for (int i = 0; i < body.size(); i++){
         body.get(i).draw(c, p);
      }
   }

   public float[] headPos(){
      return body.get(0).getPos();
   }

   public boolean isOutOfBounds(){
      if(body.get(0).getPos()[0]+body.get(0).getRadius() > viewWidth){
         return true;
      }
      else if(body.get(0).getPos()[0]-body.get(0).getRadius() < 0){
         return true;
      }
      if(body.get(0).getPos()[1]+body.get(0).getRadius() > viewHeight){
         return true;
      }
      else return body.get(0).getPos()[1] - body.get(0).getRadius() < 0;
   }

   public BodyPart getLastBodyPart(){
      return body.get(body.size()-1);
   }
   public boolean checkCollision(){
      //we don't need to check collision with the head or the first bodypart
      for(int i = 3; i < body.size(); i++){
         if(body.get(i).collided(headPos()) && body.get(i).isHasPlacedInBody()){
            return true;
         }
      }
      return false;
   }

   public void addBodyPart(BodyPart part){
      part.setDirection(body.get(body.size()-1).getPos());
      body.add(part);

   }

   public void move(){
      for(int i = 0; i < body.size(); i++){
         body.get(i).move();
         if(i > 0){
            body.get(i).setDirection(body.get(i-1).getPos());
         }
      }
   }

   public void setGesturePos(float[] gesturePos) {
      body.get(0).setDirection(gesturePos);

   }
}
