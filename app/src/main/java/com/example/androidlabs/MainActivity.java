package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ToDoList";
    private ArrayList<Item> itemsList = new ArrayList<Item>();
    private MyListAdapter myAdapter;
    private EditText editText;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the fields from the screen:
        EditText editText = (EditText) findViewById(R.id.editText);
        Switch theSwitch = (Switch) findViewById(R.id.switchOption);
        Button addButton = (Button) findViewById(R.id.button);
        ListView listViewItems = (ListView) findViewById(R.id.listviewItems);

        listViewItems.setAdapter(myAdapter = new MyListAdapter());

        loadDataFromDatabase();

        listViewItems.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message
                    .setMessage("The selected row is: " + pos)

                    //What the YES button does
                    .setPositiveButton("Delete item", (click, arg) -> {
                        Item item = itemsList.get(pos);
                        deleteItem(item);
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

        addButton.setOnClickListener(click -> {

            String item = editText.getText().toString();
            boolean switchOption = theSwitch.isChecked();
            int urgency;
            if (switchOption == true) {
                urgency = 1;
            } else {
                urgency = 0;
            }

            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            newRowValues.put(MyOpener.COL_ITEM, item);
            newRowValues.put(MyOpener.COL_URGENCY, urgency);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            Item newItem = new Item(item, urgency, newId);

            //add the new contact to the list:
            itemsList.add(newItem);

            //clear the EditText fields:
            editText.setText("");
            theSwitch.setChecked(false);
            urgency = 0;

            myAdapter.notifyDataSetChanged();

            //Show the id of the inserted item:
            Toast.makeText(this, "Inserted item id:" + newId, Toast.LENGTH_LONG).show();

        });

    }

    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(MainActivity.this);

        db = dbOpener.getWritableDatabase();

        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_ITEM, MyOpener.COL_URGENCY};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int itemColumnIndex = results.getColumnIndex(MyOpener.COL_ITEM);
        int urgencyColIndex = results.getColumnIndex(MyOpener.COL_URGENCY);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String item = results.getString(itemColumnIndex);
            int urgency = results.getInt(urgencyColIndex);
            long id = results.getInt(idColIndex);

            itemsList.add(new Item(item, urgency, id));
        }
        printCursor(results);
    }

    protected void deleteItem(Item i) {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(i.getId())});
    }

    private void printCursor(Cursor c) {
        Log.d(TAG, "Database version " + db.getVersion());
        Log.d(TAG, "Number of columns " + c.getColumnNames().length);
        Log.d(TAG,"Names of columns " + c.getColumnNames()[0] + ", " + c.getColumnNames()[1] + ", " + c.getColumnNames()[2]);
        Log.d(TAG,"Number of results " + c.getCount());

        int itemColumnIndex = c.getColumnIndex(MyOpener.COL_ITEM);
        int urgencyColIndex = c.getColumnIndex(MyOpener.COL_URGENCY);
        int idColIndex = c.getColumnIndex(MyOpener.COL_ID);

        while (c.moveToNext()) {
            String item = c.getString(itemColumnIndex);
            int urgency = c.getInt(urgencyColIndex);
            long id = c.getInt(idColIndex);

            Log.d(TAG,"Result row " + id + " " + item + " " + urgency);
        }

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

            if (newView == null) {
                newView = inflater.inflate(R.layout.lvlayout, parent, false);
            }

            TextView textView = newView.findViewById(R.id.textView);
            Item item = (Item) getItem(position);
            textView.setText(item.getText());

            if (item.urgent == 1) {
                textView.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
            }
            return newView;
        }
    }
}

