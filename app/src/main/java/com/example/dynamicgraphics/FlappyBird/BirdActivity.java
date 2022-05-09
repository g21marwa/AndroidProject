package com.example.dynamicgraphics.FlappyBird;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.dynamicgraphics.R;

public class BirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setTitle("Flappy Bird");
        }
    }
}