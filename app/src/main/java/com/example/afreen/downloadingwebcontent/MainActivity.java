package com.example.afreen.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    public class DownloadTask extends AsyncTask<String, Void, String>//to reun large code on a seperate thread othet than the main thread.
    {


        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection= null;

            try{

                url=new URL(urls[0]);
                urlConnection= (HttpURLConnection) url.openConnection(); //open up the browser and establish connection

                InputStream in=urlConnection.getInputStream();//stream to hold input of data as it comes in
                InputStreamReader reader=new InputStreamReader(in);//read the data in the stream

                int data= reader.read();

                while (data!= -1){

                    char current = (char) data;

                    result+= current;
                    data= reader.read();
                }

                return result;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return "Failed";
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task= new DownloadTask();
        String result= null;
        try {
            result = task.execute("https://www.ecowebhosting.co.uk/").get();
        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }

        Log.i("Contents of URL", result);

    }
}
