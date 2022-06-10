package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> itemsList = new ArrayList<Item>();
    private MyListAdapter myAdapter;
    private Button addButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listViewItems = findViewById(R.id.listviewItems);
        listViewItems.setAdapter(myAdapter = new MyListAdapter());

        listViewItems.setOnItemLongClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message
                    .setMessage("The selected row is: " + pos)

                    //What the YES button does
                    .setPositiveButton("Delete item", (click, arg) -> {
                        itemsList.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })

                    //What the No button does
                    .setNegativeButton("No", (click, arg) -> {
                    })

                    //Show the dialog
                    .create().show();
            return true;
        });

        addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editText = (EditText)findViewById(R.id.editText);
                Switch theSwitch = (Switch)findViewById(R.id.switchOption);
                boolean switchOption = theSwitch.isChecked();

                itemsList.add(new Item(editText.getText().toString(), switchOption));
                editText.setText("");
                theSwitch.setChecked(false);
                myAdapter.notifyDataSetChanged();
            }
        });

    }

    private class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return itemsList.size();
        }

        public Object getItem(int position) {
            return itemsList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            if(newView == null) {
                newView = inflater.inflate(R.layout.lvlayout, parent, false);
            }

            TextView textView = newView.findViewById(R.id.textView);
            Item item = (Item)getItem(position);
            textView.setText(item.getText());

            if(item.urgent) {
                textView.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
            }
            else {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
            }
            return newView;
        }
    }
}