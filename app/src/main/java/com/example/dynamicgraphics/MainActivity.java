package com.example.dynamicgraphics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DynamicView dv = new DynamicView(this, 500, 500);
        setContentView(R.layout.activity_gol);
    }
}