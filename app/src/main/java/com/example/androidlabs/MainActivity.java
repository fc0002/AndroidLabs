package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<Map<String, String>> source = new ArrayList<>();

    public void setList(ArrayList<Map<String, String>> list) {
        this.source = list;
    }


    List<String> names = new ArrayList<String>();

    private ListViewAdapter myAdapter;
    public static final String NAME = "NAME";
    public static final String HEIGHT = "HEIGHT";
    public static final String MASS = "MASS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);

        ListView theList = (ListView) findViewById(R.id.theList);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded



        StarWars req = new StarWars();
        req.execute("https://swapi.dev/api/people/?format=json");

        theList.setAdapter(myAdapter = new ListViewAdapter());

        theList.setOnItemClickListener((list, item, position, id) -> {

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentLocation);
            Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

            if (isTablet) {
                DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                dFragment.setArguments(dataToPass); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment.
            } else //isPhone
            {
                Intent nextActivity = new Intent(MainActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });



    }

    private class StarWars extends AsyncTask<String, Integer, ArrayList<Map<String, String>>> {

        ArrayList<Map<String, String>> source = new ArrayList<>();
        private MainActivity activity;

        public StarWars(MainActivity activity) {
            this.activity = activity;
        }

        public StarWars() {

        }

        public ArrayList<Map<String, String>> doInBackground(String... args) {

            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON:
                JSONArray characters = new JSONObject(result).getJSONArray("results");
                int length = characters.length();

                for (int i = 0; i < length; i++) {
                    source.add((Map<String, String>) characters.getJSONObject(i));
                }
                
                myAdapter.notifyDataSetChanged();

            } catch (Exception e) {

            }

            return source;
        }

        public void onProgressUpdate(ArrayList<Map<String, String>>... args) {
            //imageView.setImageBitmap(bitmap);
            activity.setList(source);
        }

        public void onPostExecute(ArrayList<Map<String, String>> result) {
            //activity.setList(source);
        }
    }

    private class ListViewAdapter extends BaseAdapter {

        public int getCount() {
            return source.size();
        }

        public Object getItem(int position) {
            return source.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            if (newView == null) {
                newView = inflater.inflate(R.layout.fragment_detail, parent, false);
            }

            ListView listView = newView.findViewById(R.id.theList);

            TextView nameView = newView.findViewById(R.id.nameView1);
            TextView heightView = newView.findViewById(R.id.heightView1);
            TextView massView = newView.findViewById(R.id.massView1);

            Map<String, String> character = source.get(position);
            nameView.setText(character.get("name"));
            heightView.setText(character.get("height"));
            massView.setText(character.get("mass"));

            return newView;
        }
    }
}