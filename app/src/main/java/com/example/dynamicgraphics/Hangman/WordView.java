package com.example.dynamicgraphics.Hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class WordView extends View {
   private String word;
   private boolean hasLoaded;
   private Paint p;
   private float viewHeight;
   private float startPos;
   public WordView(Context context, AttributeSet attr){
      super(context, attr);
   }

   @Override
   public void onWindowFocusChanged(boolean hasWindowFocus) {
      super.onWindowFocusChanged(hasWindowFocus);
      if(!hasLoaded){
         hasLoaded = true;
         p = new Paint();
         startPos = getY();
         Log.d("asd", "pos " + startPos);
         invalidate();
      }
   }
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if(hasLoaded){
         float nextPos = 0;
         for(int i = 0; i < word.length(); i++){
            p.setTextSize(200.0f);
            p.setStrokeWidth(1f);
            p.setStyle(Paint.Style.FILL_AND_STROKE);
            p.setColor(Color.parseColor("#ffffff"));
            String letter =  String.valueOf(word.charAt(i));
            canvas.drawText(letter, nextPos, 150, p);
            Rect bounds = new Rect();
            p.getTextBounds(letter, 0, letter.length(), bounds);
            int width = bounds.width();
            Paint.FontMetrics fm = p.getFontMetrics();
            float height = fm.descent - fm.ascent;
            float thisPos = nextPos;
            nextPos += p.measureText(letter);
            canvas.drawRect(thisPos, height, thisPos+width,height+10, p);


         }
      }
      invalidate();
   }

   public void setWord(String myWord){
      word = myWord.toLowerCase();
   }
}
