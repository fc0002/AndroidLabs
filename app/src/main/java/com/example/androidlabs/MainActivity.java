package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        CatImages req = new CatImages(imageView);
        req.execute("https://cataas.com/cat?json=true");

    }

    private class CatImages extends AsyncTask<String, Integer, Void> {

        ImageView imageView;

        public CatImages(ImageView imageView) {
            this.imageView = imageView;
        }

        public Void doInBackground(String... args) {
            while (true) {
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

                    String id = jsonObject.getString("id");
                    String urlString = jsonObject.getString("url");
                    String realUrl = "https://cataas.com" + urlString;
                    URL url2 = new URL(realUrl);

                    // check if file with id exists on device
                    File file = getBaseContext().getFileStreamPath(id);

                    // if yes, set imageview to use local file
                    if (file.exists()) {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    } else {
                        // if no, download image, save to device, and set imageview
                        bitmap = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                    }

                } catch (Exception e) {

                }

                progressBar.setVisibility(View.VISIBLE);

                for (int i = 0; i < 100; i++) {
                    try {
                        publishProgress(i);
                        Thread.sleep(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //return bitmap;

            } // end of while
        }

        public void onProgressUpdate(Integer... args) {
            // If a new cat picture has just been selected, update the ImageView with the new picture.
            imageView.setImageBitmap(bitmap);

            // Update the progress bar with the current status of the timer
            progressBar.setProgress(args[0]);
        }

        public void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
        }
    }
}