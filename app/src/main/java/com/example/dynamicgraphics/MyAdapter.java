package com.example.dynamicgraphics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dynamicgraphics.Arkanoid.ArkanoidActivity;
import com.example.dynamicgraphics.FlappyBird.BirdActivity;
import com.example.dynamicgraphics.Gol.GolActivity;
import com.example.dynamicgraphics.Hangman.HangmanActivity;
import com.example.dynamicgraphics.Snake.SnakeActivity;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
   private final List<GameInfo> localDataSet;
   private final Context main;
   private int[][] arkanoidData;
   private int[][] snakeData;
   private int[][] hangmanData;
   private int[][] golData;
   private int[][] flappyBirdData;
   public MyAdapter(List<GameInfo> dataSet, Context c){
      localDataSet = dataSet;
      main = c;
      Log.d("asd5", "" + localDataSet.size());
      arkanoidData = new int[][]{{}};
      snakeData = new int[][]{{}};
      hangmanData = new int[][]{{}};
      golData = new int[][]{{}};
      flappyBirdData = new int[][]{{}};
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Log.d("asd", localDataSet.get(position).getName());
      Log.d("asd", "" + position);
      holder.getTv().setText(localDataSet.get(position).getName());
      holder.getBut().setText(localDataSet.get(position).getName());

      switch (localDataSet.get(position).getID()) {
         case "arkanoid":
            holder.getBut().setOnClickListener(this::startActivityArkanoidClicked);
            arkanoidData = localDataSet.get(position).getAuxdata().getData();
            break;
         case "flappy_bird":
            holder.getBut().setOnClickListener(this::startActivityFlappyClicked);
            flappyBirdData = localDataSet.get(position).getAuxdata().getData();
            break;
         case "game_of_life":
            holder.getBut().setOnClickListener(this::startActivityGolClicked);
            golData = localDataSet.get(position).getAuxdata().getData();
            break;
         case "hangman":
            holder.getBut().setOnClickListener(this::startActivityHangmanClicked);
            hangmanData = localDataSet.get(position).getAuxdata().getData();
            break;
         case "snake":
            holder.getBut().setOnClickListener(this::startActivitySnakeClicked);
            snakeData = localDataSet.get(position).getAuxdata().getData();
            break;
      }
   }

   @Override
   public int getItemCount() {
      return localDataSet.size();
   }

   public void startActivityArkanoidClicked(View view) {
      Intent i = new Intent(main, ArkanoidActivity.class);
      i.putExtra("data", arkanoidData);
      main.startActivity(i);
   }

   public void startActivityGolClicked(View view) {
      Intent i = new Intent(main, GolActivity.class);
      i.putExtra("data", golData);
      main.startActivity(i);
   }

   public void startActivityFlappyClicked(View view) {
      Intent i = new Intent(main, BirdActivity.class);
      i.putExtra("data", flappyBirdData);
      main.startActivity(i);
   }

   public void startActivitySnakeClicked(View view) {
      Intent i = new Intent(main, SnakeActivity.class);
      i.putExtra("data", snakeData);
      main.startActivity(i);
   }

   public void startActivityHangmanClicked(View view) {
      Intent i = new Intent(main, HangmanActivity.class);
      i.putExtra("data", hangmanData);
      main.startActivity(i);
   }

   public static class ViewHolder extends RecyclerView.ViewHolder{
      private final TextView tv;
      private final Button but;
      public ViewHolder(View view ){
         super(view);
         tv = view.findViewById(R.id.itemHeader);
         but = view.findViewById(R.id.gameButton);

      }

      public Button getBut() {
         return but;
      }

      public TextView getTv() {
         return tv;
      }
   }
}
