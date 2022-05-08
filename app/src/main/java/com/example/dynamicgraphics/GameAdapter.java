package com.example.dynamicgraphics;

import android.util.Log;

import androidx.annotation.NonNull;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.List;

class GameAdapter {
   @ToJson
   public String toJson(GameInfo gameInfo) {
      Log.d("asd4", gameInfo.toString());
      return "null";
   }
   @FromJson
   public List<GameInfo> fromJson(@NonNull String json){
      //Log.d("asd2", json);
      return null;
   }
}
