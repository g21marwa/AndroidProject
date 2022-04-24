package com.example.dynamicgraphics;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.dynamicgraphics.FlappyBird.BirdActivity;
import com.example.dynamicgraphics.Gol.DynamicView;
import com.example.dynamicgraphics.Gol.GolActivity;
import com.example.dynamicgraphics.Snake.SnakeActivity;

public class MainActivity extends AppCompatActivity{


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //content is gol atm. This should be in the gol activity late(maybe)
        setContentView(R.layout.activity_main);
    }

    public void startActivityClicked(View view) {
        Intent i = new Intent(MainActivity.this, SnakeActivity.class);
        startActivity(i);
    }
}