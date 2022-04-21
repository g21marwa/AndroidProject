package com.example.dynamicgraphics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DynamicView dv;
    private Button startButton;
    private Spinner shapeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DynamicView dv = new DynamicView(this, 500, 500);
        setContentView(R.layout.activity_gol);
        dv = findViewById(R.id.gol_view);
        startButton = findViewById(R.id.gol_start);
        startButton.setText("Start");
        shapeSpinner = findViewById(R.id.gol_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shapeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(adapter);
        shapeSpinner.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String a = (String)adapterView.getItemAtPosition(i);
        Log.d("asd", a);
        dv.setSelectedShape(a);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("asd", "a");
    }
}