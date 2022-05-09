package com.example.dynamicgraphics.Hangman;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicgraphics.R;

import java.util.ArrayList;
import java.util.List;

public class HangmanActivity extends AppCompatActivity {
    private HangmanView hv;
    private WordView wv;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setTitle("Hangman");
        }
        hv = findViewById(R.id.hangman_view);
        wv = findViewById(R.id.hangman_word_view);
        et = findViewById(R.id.hangman_text_input);
        List<String> words = new ArrayList<>();
        words.add("test");
        words.add("something horrible is happening at the store where i work");
        words.add("puppy");
        wv.setWord(words.get(0));
        et.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                makeGuess();
                return true;
            }
            return false;
        });
    }

    public void sendClicked(View view) {
        //et.getText();
        makeGuess();
    }
    private void makeGuess(){
        String text = et.getText().toString();
        if(text.length() > 1){
            if(!wv.makeFullGuess(text)){
                hv.wrong();
            }
        }
        else{
            if(wv.makeGuess(text) == 1){
                hv.wrong();
            }
            else {
                wv.makeGuess(text);//guess has already been made
//correct guess
            }
        }
    }
}