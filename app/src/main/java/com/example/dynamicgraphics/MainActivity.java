package com.example.dynamicgraphics;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {
    private RecyclerView myRecyclerView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView = findViewById(R.id.recycler_view);
        myRecyclerView.setLayoutManager(mLayoutManager);
        String JSON_URL = "https://mobprog.webug.se/json-api?login=G21MARWA";
        new JsonTask(this).execute(JSON_URL);
    }



    @Override
    public void onPostExecute(String json) {
        Log.d("asd1", json);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, GameInfo.class);
        JsonAdapter<List<GameInfo>> jsonAdapter = moshi.adapter(type);
        try {
            Log.d("asd3", json);
            List<GameInfo> gameInfo = jsonAdapter.fromJson(json);
            MyAdapter adapter = new MyAdapter(gameInfo, this);
            Log.d("asd55", "" + adapter.getItemCount());
            myRecyclerView.setAdapter(adapter);
        }
        catch (Exception e){
            Log.d("error", e.toString());

        }
    }
}