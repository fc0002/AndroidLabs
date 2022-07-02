package com.example.androidlabs;

import android.os.Bundle;
import android.widget.TextView;

public class DadJoke extends Navigation {

    String joke = "What do you call a mermaid on a roof? Aerial!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);

        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.)
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(joke);
    }

}