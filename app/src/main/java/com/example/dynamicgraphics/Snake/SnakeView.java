package com.example.dynamicgraphics.Snake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.dynamicgraphics.FlappyBird.Obstacle;
import com.example.dynamicgraphics.R;


public class SnakeView extends View {
   private boolean hasLoaded;
   private int viewHeight, viewWidth;
   private int i = 0;
   private Bitmap frame ;
   private Rect bounds;
   private Paint paint ;
   private Snake snake;
   private BodyPart snack;
   private boolean gameOver = true;
   private TextView scoreText;
   private TextView overlayText;
   private int score = 0;
   public SnakeView(Context context, AttributeSet attr){
      super(context, attr);
   }

   @SuppressLint("DrawAllocation")
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if(hasLoaded) {
         canvas.drawBitmap(frame, null, bounds, null);
         snake.draw(canvas, paint);

         snack.draw(canvas, paint);
         if(!gameOver) {
            snake.move();
            if (snake.checkCollision()) {
               overlayText.setVisibility(View.VISIBLE);
               gameOver = true;
            }
            if(snake.isOutOfBounds()){
               overlayText.setVisibility(View.VISIBLE);
               gameOver = true;
            }
            if (snack.collided(snake.headPos())) {
               snack.setFollowing(snake.getLastBodyPart());
               snake.addBodyPart(snack);
               score+=10;
               String myText = "Score: " + score;
               scoreText.setText(myText);
               snack = new BodyPart(viewWidth, viewHeight, null);
            }

            if (i < 120) {
               i++;
            } else {
               i = 0;
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
         i = 100;
         invalidate();
      }
   }

   @SuppressLint("ClickableViewAccessibility")
   @Override
   public boolean onTouchEvent(MotionEvent m) {

      switch (m.getAction()){

         case MotionEvent.ACTION_DOWN:
            if(gameOver){
               reset();
            }
            return true;
         case MotionEvent.ACTION_MOVE:
            float[] gesturePos = {m.getX(), m.getY()};

            snake.setGesturePos(gesturePos);
            return true;
      }
      return true;
   }
   private void initVariables(){
      hasLoaded = true;
      viewHeight = getHeight();
      viewWidth = getWidth();

      bounds = new Rect(0, 0, 0, 0);
      paint = new Paint();
      snake = new Snake(getWidth(), getHeight());
      snack = new BodyPart(viewWidth, viewHeight, null);
      scoreText = getRootView().findViewById(R.id.snake_score);
      overlayText = getRootView().findViewById(R.id.snake_overlay);
      frame = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
   }
   private void reset(){
      overlayText.setVisibility(View.INVISIBLE);
      String myText = "Score: 0";
      scoreText.setText(myText);
      gameOver = false;
      initVariables();
   }
}
