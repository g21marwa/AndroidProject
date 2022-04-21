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
import java.util.HashMap;
import java.util.List;
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
    private HashMap<String, List<int[]>> shapes;
    private String selectedShape = "";
    private List<Cell> prevTempCells;
    private int prevFreehand = 0;
    private int currentFreeHand = 0;
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
        prevTempCells = new ArrayList<>();
        initShapes();
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
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent m){
        double x, y;
        x = m.getX();
        y = m.getY();
        int pos = (int)(Math.floor(y/size)*numCellsWidth) + (int)Math.floor(x/size);
        Log.d("asd3", "" + m.getAction());
        Log.d("asd3", selectedShape);
        switch (m.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("asd3", selectedShape);
                if(selectedShape.equals("Freehand") ){
                    Log.d("asd3", "" + viewWidth/size);
                                      //(int)(Math.floor(y/size)*numCellsWidth) + (int)Math.floor(x/size);
                    currentFreeHand = (int)(Math.floor(m.getY()/size)*numCellsWidth)+(int)Math.floor(m.getX()/size);
                    prevFreehand = currentFreeHand;

                    cells.get(pos).changeAlive();
                }
                else{
                    Log.d("asd3", selectedShape);

                    showShape(pos, y);
                }
                return true;
                case MotionEvent.ACTION_MOVE:
                    if(selectedShape.equals("Freehand")) {
                        currentFreeHand = (int)(Math.floor(m.getY()/size)*numCellsWidth)+(int)Math.floor(m.getX()/size);
                        if(prevFreehand != currentFreeHand){
                            cells.get(pos).changeAlive();
                            prevFreehand = currentFreeHand;
                        }
                    }
                    else {
                        showShape(pos, y);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if(selectedShape.equals("Freehand")){
                        currentFreeHand = 0;
                        prevFreehand = 0;
                    }
                    else {
                        Log.d("asd4", "" + prevTempCells.size());
                        for (int i = 0; i < prevTempCells.size(); i++) {
                            prevTempCells.get(i).insertTempAlive();
                            prevTempCells.get(i).setTempAlive(false);
                        }
                        prevTempCells.clear();
                    }
                    return true;
        }
        return super.onTouchEvent(m);
    }
    public void changeState(){
        state = !state;
    }
    public void setSelectedShape(String getShape){
        selectedShape = getShape;
    }
    private void showShape(int pos, double y){
        int arrHeight = shapes.get(selectedShape).size();
        int arrWidth = shapes.get(selectedShape).get(0).length;
        if (cells.size() - numCellsWidth > ((arrHeight + Math.floor((y / size))) * numCellsWidth) && pos >= 0) {
            if (pos + arrWidth % numCellsWidth >= numCellsWidth - 1) {
                Log.d("asd2", "" +prevTempCells.size());
                for (int i = 0; i < prevTempCells.size(); i++) {
                    prevTempCells.get(i).setTempAlive(false);
                }
                prevTempCells.clear();
                for (int i = 0; i < arrHeight; i++) {
                    for (int j = 0; j < arrWidth; j++) {
                        if (shapes.get(selectedShape).get(i)[j] == 1) {
                            cells.get(pos + (i * numCellsWidth) + j).setTempAlive(true);
                            prevTempCells.add(cells.get(pos + (i * numCellsWidth) + j));
                        }
                    }
                }
            }
        }
    }
    private void initShapes(){
        shapes = new HashMap<>();
        List<int[]> shapeList = new ArrayList<int[]>();
        shapeList.add(new int[] {1,1,1});
        shapeList.add(new int[] {0,0,1});
        shapeList.add(new int[] {0,1,0});
        shapes.put("Shape 1", shapeList);
    }
}
