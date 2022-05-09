package com.example.dynamicgraphics.Snake;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.dynamicgraphics.R;

public class SnakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setTitle("Snake");
        }
    }

    public void resetClicked(View view) {
    }

    public void startClicked(View view) {
    }
}