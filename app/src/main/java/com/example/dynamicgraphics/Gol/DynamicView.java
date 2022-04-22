package com.example.dynamicgraphics.Gol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DynamicView extends View {
    //Variables for drawing
    private int i = 0;
    private Bitmap frame ;
    private Rect bounds;
    private Paint paint ;

    //variables for handling cells
    private final ArrayList<Cell> cells = new ArrayList<>();
    private final int size = 32;
    private int numCellsHeight;
    private int numCellsWidth;
    //variables for handling shapes and states
    private boolean state = false;
    private boolean hasLoaded = false;
    private HashMap<String, List<int[]>> shapes;
    private String selectedShape = "";
    private List<Cell> prevTempCells;
    private int prevFreehand = 0;


    public DynamicView(Context context, AttributeSet attr){
        super(context, attr);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        //we only want to init everything on the first time we gain focus.
        if(!hasLoaded) {
            prevTempCells = new ArrayList<>();
            initShapes();
            initVariables();
            initCells();
            i = 0;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        //Error handler. onDraw executes before we initialize variables
        if(hasLoaded) {
            if (state) {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).draw(canvas, paint);
                }
                // and then draw the bitmap on the view canvas
                canvas.drawBitmap(frame, null, bounds, null);
                //the speed of every frame(just how many times the function has run)
                if (i < 20) {
                    i++;
                } else {
                    i = 0;
                    for (int i = 0; i < cells.size(); i++) {
                        cells.get(i).calcAlive();
                    }
                    for (int i = 0; i < cells.size(); i++) {
                        cells.get(i).change();
                    }
                }
            } else {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).draw(canvas, paint);
                }
            }
            invalidate();
        }
    }
    //handles touch events
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent m){
        double x, y;
        x = m.getX();
        y = m.getY();
        //translate x and y to pos in array
        int pos = (int)(Math.floor(y/size)*numCellsWidth) + (int)Math.floor(x/size);
        //handles touch down, up, and move
        int currentFreeHand;
        switch (m.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(selectedShape.equals("Freehand") ){
                    //makes sure that if we move while freehand, we don't reset the already defined cell.
                    currentFreeHand = (int)(Math.floor(m.getY()/size)*numCellsWidth)+(int)Math.floor(m.getX()/size);
                    prevFreehand = currentFreeHand;
                    cells.get(pos).changeAlive();
                }
                else{
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
                //actually sets the values for the cells, not just the temp.
                case MotionEvent.ACTION_UP:
                    if(selectedShape.equals("Freehand")){
                        prevFreehand = 0;
                    }
                    else {
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
    //set the tempAlive to true when placing predefined shapes
    private void showShape(int pos, double y){
        int arrHeight = Objects.requireNonNull(shapes.get(selectedShape)).size();
        int arrWidth = Objects.requireNonNull(shapes.get(selectedShape)).get(0).length;
        if (cells.size() - numCellsWidth > ((arrHeight + Math.floor((y / size))) * numCellsWidth) && pos >= 0) {
            if (pos + arrWidth % numCellsWidth >= numCellsWidth - 1) {
                for (int i = 0; i < prevTempCells.size(); i++) {
                    prevTempCells.get(i).setTempAlive(false);
                }
                prevTempCells.clear();
                for (int i = 0; i < arrHeight; i++) {
                    for (int j = 0; j < arrWidth; j++) {
                        if (Objects.requireNonNull(shapes.get(selectedShape)).get(i)[j] == 1) {
                            cells.get(pos + (i * numCellsWidth) + j).setTempAlive(true);
                            prevTempCells.add(cells.get(pos + (i * numCellsWidth) + j));
                        }
                    }
                }
            }
        }
    }
    //creates predefines shapes(Hopefully JSON will do this later)
    private void initShapes(){
        shapes = new HashMap<>();
        List<int[]> shapeList = new ArrayList<>();
        shapeList.add(new int[] {1,1,1});
        shapeList.add(new int[] {0,0,1});
        shapeList.add(new int[] {0,1,0});
        shapes.put("Shape 1", shapeList);
    }
    //loop through all possible cells based on the width of the window and create cells.
    private void initCells(){
        for (int i = 0; i < numCellsHeight; i++) {
            for (int j = 0; j < numCellsWidth; j++) {
                Cell c = new Cell(j * size, i * size, size, false);
                cells.add(c);
            }
        }
        //all cells has neighbours. we predefine them here.
        for (int i = 0; i < cells.size(); i++) {
            ArrayList<Cell> neighbours = new ArrayList<>();
            // check if the cell is to the left
            if (i % numCellsWidth == 0) {
                //top
                if (i < numCellsWidth) {
                    neighbours.add(cells.get(i + 1));
                    neighbours.add(cells.get(i + numCellsWidth));
                    neighbours.add(cells.get(i + numCellsWidth + 1));
                    cells.get(i).setNeighbours(neighbours);
                }
                //bot
                else if (i >= (cells.size() - 1) - numCellsWidth) {
                    neighbours.add(cells.get(i - numCellsWidth));
                    neighbours.add(cells.get(i - numCellsWidth + 1));
                    neighbours.add(cells.get(i + 1));
                    cells.get(i).setNeighbours(neighbours);
                } else {
                    neighbours.add(cells.get(i - numCellsWidth));
                    neighbours.add(cells.get(i - numCellsWidth + 1));
                    neighbours.add(cells.get(i + 1));
                    neighbours.add(cells.get(i + numCellsWidth));
                    neighbours.add(cells.get(i + numCellsWidth + 1));
                    cells.get(i).setNeighbours(neighbours);
                }
            }
            //To the right
            else if (i % numCellsWidth == numCellsWidth - 1) {
                //Top
                if (i < numCellsWidth) {
                    neighbours.add(cells.get(i - 1));
                    neighbours.add(cells.get(i + numCellsWidth - 1));
                    neighbours.add(cells.get(i + numCellsWidth));
                    cells.get(i).setNeighbours(neighbours);
                }
                //bot
                else if (i >= (cells.size()) - numCellsWidth - 1) {
                    neighbours.add(cells.get(i - numCellsWidth));
                    neighbours.add(cells.get(i - numCellsWidth - 1));
                    neighbours.add(cells.get(i - 1));
                    cells.get(i).setNeighbours(neighbours);
                } else {
                    neighbours.add(cells.get(i - numCellsWidth - 1));
                    neighbours.add(cells.get(i - numCellsWidth - 1));
                    neighbours.add(cells.get(i - 1));
                    neighbours.add(cells.get(i + numCellsWidth - 1));
                    neighbours.add(cells.get(i + numCellsWidth));
                    cells.get(i).setNeighbours(neighbours);
                }
            } else {
                //top
                if (i < numCellsWidth) {
                    neighbours.add(cells.get(i - 1));
                    neighbours.add(cells.get(i + 1));
                    neighbours.add(cells.get(i + numCellsWidth - 1));
                    neighbours.add(cells.get(i + numCellsWidth));
                    neighbours.add(cells.get(i + numCellsWidth + 1));
                    cells.get(i).setNeighbours(neighbours);
                }
                //bot
                else if (i >= (cells.size() - 1) - (numCellsWidth)) {
                    neighbours.add(cells.get(i - numCellsWidth - 1));
                    neighbours.add(cells.get(i - numCellsWidth));
                    neighbours.add(cells.get(i - numCellsWidth + 1));
                    neighbours.add(cells.get(i - 1));
                    neighbours.add(cells.get(i + 1));
                    cells.get(i).setNeighbours(neighbours);
                } else {
                    neighbours.add(cells.get(i - numCellsWidth - 1));
                    neighbours.add(cells.get(i - numCellsWidth));
                    neighbours.add(cells.get(i - numCellsWidth + 1));
                    neighbours.add(cells.get(i - 1));
                    neighbours.add(cells.get(i + 1));
                    neighbours.add(cells.get(i + numCellsWidth - 1));
                    neighbours.add(cells.get(i + numCellsWidth));
                    neighbours.add(cells.get(i + numCellsWidth + 1));
                    cells.get(i).setNeighbours(neighbours);
                }
            }
        }
    }
    private void initVariables(){
        hasLoaded = true;
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        frame = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        bounds = new Rect(0, 0, 0, 0);
        paint = new Paint();

        numCellsHeight = viewHeight / size;
        numCellsWidth = viewWidth / size;
    }

    public void reset(){
        for(int i = 0; i < cells.size(); i++){
            cells.get(i).reset();
        }
    }
}
