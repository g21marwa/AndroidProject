package com.example.dynamicgraphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class DynamicView extends View implements View.OnTouchListener {
    private int i = 0;
    private Bitmap frame ;
    private Canvas frameDrawer;
    private Rect bounds;
    private Paint paint ;
    private Random random ;
    private int viewWidth, viewHeight;
    private int width , height;
    private ArrayList<Cell> cells = new ArrayList<>();
    private int size = 32;
    private boolean state = false;
    private boolean hasLoaded = false;
    private int numCellsHeight;
    private int numCellsWidth;
    public DynamicView(Context context, AttributeSet attr){
        super(context, attr);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        hasLoaded = true;
        viewWidth = getWidth();
        viewHeight = getHeight();
        frame = Bitmap.createBitmap(viewWidth,viewHeight,Bitmap.Config.ARGB_8888);
        frameDrawer = new Canvas(frame);
        bounds = new Rect(0 , 0, width,height);
        //this initialization will make the frameDrawer draw on the frame bitmap

        //always avoid allocating new objects in the draw method to optimize the performance
        paint = new Paint();
        random = new Random();

        numCellsHeight = viewHeight/size;
        numCellsWidth =  viewWidth/size;
        for(int i = 0; i < numCellsHeight;i++){
            for(int j = 0; j < numCellsWidth;j++){
                boolean l = false;
                int r = random.nextInt(100);
                if(r > 85){
                    //change for random generated alive
                    l = false;
                }
                Cell c = new Cell(j*size, i*size, size, l);
                cells.add(c);
            }
        }
        for(int i = 0; i < cells.size(); i++){
            ArrayList<Cell> neighbours = new ArrayList<>();
            if(i % numCellsWidth == 0){
                if(i < numCellsWidth){
                    neighbours.add(cells.get(i+1));
                    neighbours.add(cells.get(i+numCellsWidth));
                    neighbours.add(cells.get(i+numCellsWidth+1));
                    cells.get(i).setNeighbours(neighbours);
                }
                else if(i >= (cells.size()-1)-numCellsWidth){
                    neighbours.add(cells.get(i-numCellsWidth));
                    neighbours.add(cells.get(i-numCellsWidth+1));
                    neighbours.add(cells.get(i+1));
                    cells.get(i).setNeighbours(neighbours);
                }
                else{
                    neighbours.add(cells.get(i-numCellsWidth));
                    neighbours.add(cells.get(i-numCellsWidth+1));
                    neighbours.add(cells.get(i+1));
                    neighbours.add(cells.get(i+numCellsWidth));
                    neighbours.add(cells.get(i+numCellsWidth+1));
                    cells.get(i).setNeighbours(neighbours);
                }
            }
            else if(i % numCellsWidth == numCellsWidth-1){
                if(i < numCellsWidth){
                    neighbours.add(cells.get(i-1));
                    neighbours.add(cells.get(i+numCellsWidth-1));
                    neighbours.add(cells.get(i+numCellsWidth));
                    cells.get(i).setNeighbours(neighbours);
                }
                else if(i >= (cells.size())-numCellsWidth-1){
                    neighbours.add(cells.get(i-numCellsWidth));
                    neighbours.add(cells.get(i-numCellsWidth-1));
                    neighbours.add(cells.get(i-1));
                    cells.get(i).setNeighbours(neighbours);
                }
                else{
                    neighbours.add(cells.get(i-numCellsWidth-1));
                    neighbours.add(cells.get(i-numCellsWidth-1));
                    neighbours.add(cells.get(i-1));
                    neighbours.add(cells.get(i+numCellsWidth-1));
                    neighbours.add(cells.get(i+numCellsWidth));
                    cells.get(i).setNeighbours(neighbours);
                }
            }
            else{
                if(i < numCellsWidth){
                    neighbours.add(cells.get(i-1));
                    neighbours.add(cells.get(i+1));
                    neighbours.add(cells.get(i+numCellsWidth-1));
                    neighbours.add(cells.get(i+numCellsWidth));
                    neighbours.add(cells.get(i+numCellsWidth+1));
                    cells.get(i).setNeighbours(neighbours);
                }
                else if(i >= (cells.size()-1)-(numCellsWidth)){
                    int temp = (cells.size()-1)-numCellsWidth;
                    neighbours.add(cells.get(i-numCellsWidth-1));
                    neighbours.add(cells.get(i-numCellsWidth));
                    neighbours.add(cells.get(i-numCellsWidth+1));
                    neighbours.add(cells.get(i-1));
                    neighbours.add(cells.get(i+1));
                    cells.get(i).setNeighbours(neighbours);
                }
                else{
                    int temp = (cells.size()-1)-numCellsWidth;
                    neighbours.add(cells.get(i-numCellsWidth-1));
                    neighbours.add(cells.get(i-numCellsWidth));
                    neighbours.add(cells.get(i-numCellsWidth+1));
                    neighbours.add(cells.get(i-1));
                    neighbours.add(cells.get(i+1));
                    neighbours.add(cells.get(i+numCellsWidth-1));
                    neighbours.add(cells.get(i+numCellsWidth));
                    neighbours.add(cells.get(i+numCellsWidth+1));
                    cells.get(i).setNeighbours(neighbours);
                }
            }
        }
        i = 0;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(hasLoaded) {
            paint.setColor(Color.argb(255, random.nextInt(255),
            random.nextInt(255), random.nextInt(255)));
            if (state) {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).draw(canvas, paint);
                }
                // and then draw the bitmap on the view canvas
                canvas.drawBitmap(frame, null, bounds, null);
                if (i < 100) {
                    i++;
                    invalidate();
                } else {
                    i = 0;
                    for (int i = 0; i < cells.size(); i++) {
                        cells.get(i).calcAlive();
                    }
                    for (int i = 0; i < cells.size(); i++) {
                        cells.get(i).change();
                    }
                    invalidate();
                }
            } else {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).draw(canvas, paint);
                }
                invalidate();
            }
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent m){
        double x, y;
        switch (m.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = m.getX();
                y = m.getY();
                int pos = (int)Math.floor(y/size) + (int)Math.floor(x/size);
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent m){
        double x, y;
        switch (m.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = m.getX();
                y = m.getY();
                int pos = (int)(Math.floor(y/size)*numCellsWidth) + (int)Math.floor(x/size);
                Log.d("asd3", "" + viewWidth/size);
                cells.get(pos).changeAlive();
        }
        return super.onTouchEvent(m);
    }
    public void changeState(){
        state = !state;
    }
}
