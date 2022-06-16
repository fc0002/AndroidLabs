package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        CatImages req = new CatImages(imageView);
        req.execute("https://cataas.com/cat?json=true");

    }

    private class CatImages extends AsyncTask<String, Integer, Bitmap> {

        ImageView imageView;

        public CatImages(ImageView imageView) {
            this.imageView = imageView;
        }
        public Bitmap doInBackground(String... args) {
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
                JSONObject jsonObject = new JSONObject(result);

                int id = jsonObject.getInt("id");
                String urlString = jsonObject.getString("url");
                String realUrl = "https://cataas.com" + urlString;

                InputStream in = new java.net.URL("https://cataas.com/cat/595f280e557291a9750ebfa3").openStream();
                bitmap = BitmapFactory.decodeStream(in);
                //imageView.setImageBitmap(bitmap);
                Log.i("MainActivity", "The id is: " + id);

            } catch (Exception e) {

            }

            return bitmap;

        }

        //Type 2
        public void onProgressUpdate(Integer... args) {

        }

        //Type3
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);

        }
    }
}