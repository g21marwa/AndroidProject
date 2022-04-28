package com.example.dynamicgraphics.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dynamicgraphics.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class HangmanActivity extends AppCompatActivity {
    private HangmanView hv;
    private WordView wv;
    private EditText et;
    private List<String> words;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        hv = findViewById(R.id.hangman_view);
        wv = findViewById(R.id.hangman_word_view);
        et = findViewById(R.id.hangman_text_input);
        send = findViewById(R.id.hangman_send);
        words = new ArrayList<>();
        words.add("test");
        words.add("something horrible is happening at the store where i work");
        words.add("puppy");
        wv.setWord(words.get(0));

    }

    public void sendClicked(View view) {
        //et.getText();
    }
}