# Rapport

Det här projektet består av 5 st spel, Arkanoid, Flappy bird, Game of life, Hangman, och snake.
Dessa är gjorde med en custom view, där onDraw metoden används för att rita ut all grafik med. 
Huvudsidan visar alla 5 spelen och en knapp till varje spel. Det finns även lite information om varje spel. 
Den info hämtas från en JSON-query. För att hämta den så använder jag mig av jsonadapter moshi.

```
String JSON_URL = "https://mobprog.webug.se/json-api?login=G21MARWA";
new JsonTask(this).execute(JSON_URL);
.
.
.
@Override
public void onPostExecute(String json) {
    Moshi moshi = new Moshi.Builder().build();
    Type type = Types.newParameterizedType(List.class, GameInfo.class);
    JsonAdapter<List<GameInfo>> jsonAdapter = moshi.adapter(type);
    try {
        List<GameInfo> gameInfo = jsonAdapter.fromJson(json);
        MyAdapter adapter = new MyAdapter(gameInfo, this);
        myRecyclerView.setAdapter(adapter);
    }
        catch (Exception e){
        Log.d("error", e.toString());

    }
}
```

Jag lägger in varje del av JSON datan i en lista i en recyclerview

```
@Override
public void onWindowFocusChanged(boolean hasWindowFocus) {
    super.onWindowFocusChanged(hasWindowFocus);
    //we only want to init everything on the first time we gain focus.
    if(!hasLoaded) {
        prevTempCells = new ArrayList<>();
        initShapes();
        initVariables();
        initCells();
        i = 0;
        invalidate();
    }
}
```

För att hålla koll så att en view har laddats in ordentligt och är i fokus så använder jag mig
av eventet onWindowFocusChanged. Här initerar jag alla variabler som behövs.

```
if (state) {
    for (int i = 0; i < cells.size(); i++) {
        cells.get(i).draw(canvas, paint);
    }
    // and then draw the bitmap on the view canvas
    canvas.drawBitmap(frame, null, bounds, null);
    //the speed of every frame(just how many times the function has run)
    if (i < 20) {
        i++;
    } else {
        i = 0;
        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).calcAlive();
        }
        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).change();
        }
    }
} else {
    for (int i = 0; i < cells.size(); i++) {
        cells.get(i).draw(canvas, paint);
    }
}
invalidate();
```

Detta är ett exempel på Game of life. Här ritar jag ut celler på ett canvas och räknar ut om de ska vara
levande eller döda i nästa frame. Jag kallar på metoden invalidate() för att få view att rita ut allt igen.
Alla andra spel använde liknande upplägg. Det är bara logiken som ändras för att få de att fungera.

```
switch (localDataSet.get(position).getID()) {
 case "arkanoid":
    holder.getBut().setOnClickListener(this::startActivityArkanoidClicked);
    arkanoidData = localDataSet.get(position).getAuxdata().getData();
    break;
.
.
.
public void startActivityArkanoidClicked(View view) {
  Intent i = new Intent(main, ArkanoidActivity.class);
  i.putExtra("data", arkanoidData);
  main.startActivity(i);
}
```

Här kan vi se hur vi lägger till en clicklistener på varje knapp. Och startar en ny aktivitet när det sker. 

