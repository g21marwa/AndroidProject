package com.example.dynamicgraphics.FlappyBird;

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

import com.example.dynamicgraphics.R;

import java.util.ArrayList;

public class BirdView extends View {
   private int viewHeight, viewWidth;
   private int i = 0;
   private Bird bird;
   private boolean hasLoaded = false;
   private Bitmap frame ;
   private Rect bounds;
   private Paint paint ;
   private ArrayList<Obstacle> obstacles;
   private boolean gameOver = true;
   private int points;
   private TextView scoreText;
   private TextView overlayText;
   public BirdView(Context context, AttributeSet attr){
      super(context, attr);

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

   @SuppressLint("DrawAllocation")
   @Override
   protected void onDraw(Canvas canvas) {
      if(hasLoaded){
         // and then draw the bitmap on the view canvas
         canvas.drawBitmap(frame, null, bounds, null);
         bird.draw(canvas, paint);
         if(!gameOver)
            bird.move();
         if(bird.outOfBounds()){
            gameOver = true;
            overlayText.setVisibility(View.VISIBLE);
         }
         for (int i = 0; i < obstacles.size(); i++) {
            if(!gameOver) {
               obstacles.get(i).move();
               if(obstacles.get(i).hasPassed(bird.getPos(), bird.getSize())){
                  points += 10;
                  String myScore = "Score: " + points;
                  Log.d("asd", scoreText.getText().toString());
                  scoreText.setText(myScore);
               }
            }
            obstacles.get(i).draw(canvas, paint);
            if (obstacles.get(i).collided(bird.getPos(), bird.getSize())) {
               gameOver = true;
               overlayText.setVisibility(View.VISIBLE);
               break;
            }
         }
         //the speed of every frame(just how many times the function has run)
         if (i < 120) {
            i++;
         } else {
            obstacles.add(new Obstacle(viewHeight, viewWidth));
            i = 0;
         }
         invalidate();
      }
   }

   @SuppressLint("ClickableViewAccessibility")
   @Override
   public boolean onTouchEvent(MotionEvent m) {
      if(m.getAction() == MotionEvent.ACTION_DOWN){
         if(!gameOver)
            bird.flyUp();
         else{
            Log.d("asd", "ddd");
            reset();
         }

      }
      return true;
   }
   private void initVariables(){
      scoreText = this.getRootView().findViewById(R.id.bird_score);
      overlayText = this.getRootView().findViewById(R.id.bird_overlay);
      obstacles = new ArrayList<>();
      viewHeight = getHeight();
      points = 0;
      bird = new Bird(viewHeight);
      hasLoaded = true;
      viewWidth = getWidth();
      frame = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
      bounds = new Rect(0, 0, 0, 0);
      paint = new Paint();
   }
   private void reset(){
      overlayText.setVisibility(View.INVISIBLE);
      String myText = "Score: 0";
      scoreText.setText(myText);
      gameOver = false;
      initVariables();
   }
}
