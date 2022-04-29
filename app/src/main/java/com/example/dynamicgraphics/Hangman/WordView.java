package com.example.dynamicgraphics.Hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.provider.FontRequest;

import com.example.dynamicgraphics.R;

import java.util.ArrayList;

public class WordView extends View {
   private String word;
   private ArrayList<String> words;
   private boolean hasLoaded;
   private Paint p;
   private final float[] startPos = {0,0};
   private ArrayList<Character> letterGuesses;
   public WordView(Context context, AttributeSet attr){
      super(context, attr);
   }
   private String letter;
   private float letterHeight, letterWidth;
   @Override
   public void onWindowFocusChanged(boolean hasWindowFocus) {
      super.onWindowFocusChanged(hasWindowFocus);
      if(!hasLoaded){
         hasLoaded = true;
         Typeface mono = getResources().getFont(R.font.roboto);
         p = new Paint();
         p.setTypeface(mono);
         p.setTextSize(100.0f);
         p.setStrokeWidth(1f);
         p.setStyle(Paint.Style.FILL_AND_STROKE);
         p.setColor(Color.parseColor("#ffffff"));
         startPos[0] = getX();
         startPos[1] = getY();
         Paint.FontMetrics fm = p.getFontMetrics();
         letterHeight = fm.descent - fm.ascent;
         letter =  String.valueOf(word.charAt(0));
         words = new ArrayList<>();
         Rect bounds = new Rect();
         p.getTextBounds(letter, 0, letter.length(), bounds);
         letterWidth = p.measureText(letter);
         float viewWidth = getWidth();
         int numSplits = (int)(((letterWidth) * word.length() + word.length()*50) / (viewWidth))+1;
         int numLettersPerRow = (int)(viewWidth /(letterWidth+50));
         for(int i = 0; i < numSplits; i++){
            String wholeWord = word;
            if(i != numSplits-1){
               Log.d("asd" , "" + numLettersPerRow);
               words.add(wholeWord.substring((numLettersPerRow*i), (numLettersPerRow*i + numLettersPerRow)));
            }
            else{
               words.add(wholeWord.substring(numLettersPerRow*i));
            }
         }
         getLayoutParams().height = (int)(letterHeight+letterHeight*(numSplits-1)+60);
         setLayoutParams(getLayoutParams());
         invalidate();
      }
   }
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if(hasLoaded){
         float nextPos;
         for(int i = 0; i < words.size(); i++) {
            int numLetterTrimmed = 0;
            for(int j = 0; j < words.get(i).length(); j++) {
               letter = String.valueOf(words.get(i).charAt(j));


               if(letter.trim().length() > 0){
                  nextPos = (letterWidth * (j-numLetterTrimmed)) + (50 * (j-numLetterTrimmed));
                  canvas.drawText(letter, nextPos, letterHeight+letterHeight*i, p);
                  canvas.drawRect(nextPos, letterHeight+letterHeight*i+10, nextPos+letterWidth+10,letterHeight+letterHeight*i+20, p);
               }
               else{
                  if(j == 0){
                     numLetterTrimmed++;
                  }
               }
            }
         }
      }
      invalidate();
   }

   public void setWord(String myWord){
      word = myWord.toLowerCase();
   }
}
