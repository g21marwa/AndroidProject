package com.example.dynamicgraphics.Arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Paddle {
   private final float viewWidth;
   private final float paddleWidth;
   private final float paddleHeight;
   private final float[] pos = {0,0};
   public Paddle(float width, float height){
      viewWidth = width;
      paddleWidth = width / 5;
      paddleHeight = 30;
      pos[0] = (width/2) - (paddleWidth/2);
      pos[1] = height - paddleHeight - 7;
   }

   public void draw(Canvas c, Paint p){
      p.setColor(Color.parseColor("#00ffff"));
      c.drawRect(pos[0], pos[1], pos[0]+paddleWidth, pos[1]+paddleHeight, p);
   }

   public void move(float posX){
      float checkLeftBorder = posX-paddleWidth/2;
      float checkRightBorder = posX+paddleWidth/2;
      if(checkLeftBorder < 0){
         pos[0] = 0;
      }
      else if(checkRightBorder > viewWidth){
         pos[0] = viewWidth-paddleWidth;
      }
      else{
         pos[0] = posX - paddleWidth/2;
      }
   }

   public void ballCollision(Ball ball){
      float[] ballPos = ball.getPos();
      float ballRadius = ball.getRadius();
      if(ballPos[0]+ballRadius > pos[0] && ballPos[0]-ballRadius < pos[0]+paddleWidth){
         if(ballPos[1]+ballRadius > pos[1]){
            if(ballPos[1] > pos[1]){
               float[] fixedPos = {ballPos[0], pos[1]-ballRadius*2};
               ball.setPos(fixedPos);
               ballPos = fixedPos;
            }
            float[] middleOfPaddle = {pos[0]+paddleWidth/2, pos[1]};
            double theta = calcHitAngle(ballPos, middleOfPaddle);
            double[] tempDirection = {-Math.sin(theta), -Math.cos(theta)};
            ball.setDirection(tempDirection);

         }
      }
   }

   private double calcHitAngle(float[] ball, float[] paddle){
      double dx = paddle[0] - ball[0];
      double dy = paddle[1] - ball[1];
      return Math.atan2(dx, dy);
   }
}
