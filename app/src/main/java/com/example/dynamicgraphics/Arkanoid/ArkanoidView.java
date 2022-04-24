package com.example.dynamicgraphics.Arkanoid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.dynamicgraphics.R;

import java.util.ArrayList;

public class ArkanoidView extends View {
   private boolean hasLoaded;
   private TextView scoreText;
   private TextView overlayText;
   private boolean gameOver;
   private Paint paint ;
   private Ball ball;
   private Paddle paddle;
   private ArrayList<Block> blocks;
   private int points = 0;
   private int numHits = 0;
   public ArkanoidView(Context context, AttributeSet attr){
      super(context, attr);
   }
   @SuppressLint("DrawAllocation")
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if (hasLoaded) {

         paddle.draw(canvas, paint);
         if(numHits == 1){
            for(int i = 0 ; i < blocks.size();i++) {
               blocks.get(i).reset();
            }
            ball.increaseSpeed();
            numHits = 0;
         }
         for(int i = 0 ; i < blocks.size();i++){
            blocks.get(i).draw(canvas, paint);
            if(blocks.get(i).collision(ball)){
               points += 10;
               numHits++;
               String myScore = "Score: " + points;
               scoreText.setText(myScore);
            }
         }
         ball.draw(canvas, paint);
         if(!gameOver){
            ball.move();
            paddle.ballCollision(ball);
            if(ball.checkCollisionBorder()){
               gameOver = true;
               overlayText.setVisibility(View.VISIBLE);
            }

         }
      }
      invalidate();
   }
   @Override
   public void onWindowFocusChanged(boolean hasWindowFocus) {
      super.onWindowFocusChanged(hasWindowFocus);
      //we only want to init everything on the first time we gain focus.
      if(!hasLoaded) {
         initVariables();
         invalidate();
      }
   }

   @SuppressLint("ClickableViewAccessibility")
   @Override
   public boolean onTouchEvent(MotionEvent m) {

      switch (m.getAction()) {
         case MotionEvent.ACTION_DOWN:
            if(gameOver){
               overlayText.setVisibility(View.INVISIBLE);
               reset();
            }
            else{
               paddle.move(m.getX());
            }
            return true;
         case MotionEvent.ACTION_MOVE:
            if(!gameOver){
               paddle.move(m.getX());
            }
            return true;
      }
      return true;
   }

   private void initVariables(){
      gameOver = true;
      hasLoaded = true;
      int viewHeight = getHeight();
      int viewWidth = getWidth();
      int[][] blockShape = {
              {0, 0, 0, 0, 1, 0, 0, 0, 0},
              {0, 0, 0, 1, 1, 1, 0, 0, 0},
              {0, 0, 1, 1, 1, 1, 1, 0, 0},
              {0, 1, 1, 1, 1, 1, 1, 1, 0},
              {1, 1, 1, 1, 1, 1, 1, 1, 1},
              {1, 1, 1, 1, 1, 1, 1, 1, 1},
              {0, 1, 1, 1, 1, 1, 1, 1, 0},
              {0, 0, 1, 1, 1, 1, 1, 0, 0},
              {0, 0, 0, 1, 1, 1, 0, 0, 0},
              {0, 0, 0, 0, 1, 0, 0, 0, 0},
      } ;

      blocks = new ArrayList<>();
      float blockWidth = viewWidth / 9.0f;
      for(int i = 0; i < 10; i++) {
         for (int j = 0; j < 9; j++) {
            if(blockShape[i][j] == 1){
               float blockHeight = 80;
               float[] tempPos = {j * blockWidth, (viewHeight / 20.0f) + i * blockHeight};
               blocks.add(new Block(blockWidth, blockHeight, tempPos));
            }

         }
      }


      paint = new Paint();
      scoreText = this.getRootView().findViewById(R.id.arkanoid_score);
      overlayText = this.getRootView().findViewById(R.id.arkanoid_overlay);

      paddle = new Paddle(viewWidth, viewHeight);
      ball = new Ball(viewWidth, viewHeight);
   }
   private void reset(){
      initVariables();
      overlayText.setVisibility(View.INVISIBLE);
      String myText = "Score: 0";
      scoreText.setText(myText);
      gameOver = false;

   }
}
