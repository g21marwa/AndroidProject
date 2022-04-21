package com.example.dynamicgraphics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private DynamicView dv;
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DynamicView dv = new DynamicView(this, 500, 500);
        setContentView(R.layout.activity_gol);
        dv = findViewById(R.id.gol_view);
        startButton = findViewById(R.id.gol_start);
        startButton.setText("Start");
    }

    public void startClicked(View v){
        if(startButton.getText().toString() == "Start"){
            startButton.setText("Stop");
        }
        else{
            startButton.setText("Start");
        }
        dv.changeState();
    }
}