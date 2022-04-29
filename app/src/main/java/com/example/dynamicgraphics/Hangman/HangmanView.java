package com.example.dynamicgraphics.Hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class HangmanView extends View {
   public HangmanView(Context context, AttributeSet attr){
      super(context, attr);
   }
   private Paint p;
   private boolean hasLoaded;
   private float viewWidth, viewHeight;
   private int numGuesses = 0;
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if(hasLoaded) {

         canvas.drawArc(0, viewHeight-viewHeight/6, viewWidth, viewHeight+viewHeight/4, 180, 180, true, p);
         if(numGuesses > 0)
            canvas.drawLine(viewWidth/2-viewWidth/4, viewHeight-viewHeight/6, viewWidth/2+viewWidth/4, viewHeight-viewHeight/6, p);
         if(numGuesses > 1)
            canvas.drawLine(viewWidth/2, viewHeight-viewHeight/6, viewWidth/2, viewHeight/4, p);
         if(numGuesses > 2)
            canvas.drawLine(viewWidth/2, viewHeight/4, viewWidth/2+viewWidth/4, viewHeight/4, p);
         if(numGuesses > 3)
            canvas.drawLine(viewWidth/2+viewWidth/4, viewHeight/4, viewWidth/2+viewWidth/4, viewHeight/3, p);
         if(numGuesses > 4)
            canvas.drawCircle(viewWidth/2+viewWidth/4, viewHeight/3+viewWidth/14, viewWidth/14, p);
         if(numGuesses > 5)
            canvas.drawLine(viewWidth/2+viewWidth/4, viewHeight/3+(viewWidth/14)*2, viewWidth/2+viewWidth/4, viewHeight/2+viewHeight/10, p);
         if(numGuesses > 6)
            canvas.drawLine(viewWidth/2+viewWidth/4, viewHeight/2+viewHeight/10, viewWidth/2+viewWidth/3, viewHeight/2+viewHeight/6, p);
         if(numGuesses > 7)
            canvas.drawLine(viewWidth/2+viewWidth/4, viewHeight/2+viewHeight/10, viewWidth/2+viewWidth/6, viewHeight/2+viewHeight/6, p);
         if(numGuesses > 8)
            canvas.drawLine(viewWidth/2+viewWidth/4, viewHeight/3+(viewWidth/14)*3, viewWidth/2+viewWidth/3, viewHeight/3+(viewWidth/14)*2, p);
         if(numGuesses > 9)
            canvas.drawLine(viewWidth/2+viewWidth/4, viewHeight/3+(viewWidth/14)*3, viewWidth/2+viewWidth/6, viewHeight/3+(viewWidth/14)*2, p);

      }
      invalidate();
   }
   @Override
   public void onWindowFocusChanged(boolean hasWindowFocus) {
      super.onWindowFocusChanged(hasWindowFocus);
      if(!hasLoaded) {
         hasLoaded = true;
         p = new Paint();
         p.setStyle(Paint.Style.STROKE);
         p.setColor(Color.parseColor("#ffffff"));
         p.setStrokeWidth(20);
         viewHeight = getMeasuredHeight();
         viewWidth = getMeasuredWidth();

      }

   }

   @Override
   protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      super.onLayout(changed, left, top, right, bottom);
      viewWidth = getWidth();
      viewHeight = getHeight();
      Log.d("asd", "asd" + viewWidth + " : " + viewHeight);
   }

   public boolean wrong(){
      numGuesses++;
      if(numGuesses >= 10){
         return true;
      }
      return false;
   }
}
