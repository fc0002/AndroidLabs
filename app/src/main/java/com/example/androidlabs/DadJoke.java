package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DadJoke extends Navigation {

    String joke = "Q: What do you call a dad joke that falls on its head? A: A dud pun.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);

        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.)
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(joke);
    }

}