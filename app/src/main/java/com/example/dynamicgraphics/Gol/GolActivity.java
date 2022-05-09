package com.example.dynamicgraphics.Gol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.dynamicgraphics.R;

public class GolActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DynamicView dv;
    private Button startButton;
    private Button resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gol);
        //content is gol atm. This should be in the gol activity late(maybe)
        setContentView(R.layout.activity_gol);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setTitle("Game Of Life");
        }
        dv = findViewById(R.id.gol_view);
        startButton = findViewById(R.id.gol_start);
        startButton.setText(R.string.gol_start_name);
        resetButton = findViewById(R.id.gol_reset);
        resetButton.setText(R.string.gol_reset_name);
        Spinner shapeSpinner = findViewById(R.id.gol_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shapeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(adapter);
        shapeSpinner.setOnItemSelectedListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void startClicked(View v){
        if(startButton.getText().toString().equals("Start")){
            startButton.setText(R.string.gol_stop_name);
        }
        else{
            startButton.setText("Start");
        }
        dv.changeState();
    }

    @SuppressLint("SetTextI18n")
    public void resetClicked(View v){
        dv.reset();
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