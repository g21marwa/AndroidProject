package com.example.dynamicgraphics.Arkanoid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicgraphics.R;

public class ArkanoidActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkanoid);
        Intent i = getIntent();
        int[][] data = (int[][]) i.getSerializableExtra("data");
        ArkanoidView aa = findViewById(R.id.arkanoid_view);
        aa.setBlockShape(data);

    }
}