package com.sandbox.test.cleartextreqapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new TestManagerTask("http://www.httpbin.org/get", false).execute();
    }

    /**
     * Async Task to iterate through the Test's Trials and perform them until all the Trials are
     * completed
     */
    private class TestManagerTask extends AsyncTask<Void, Integer, Void> {

        URL url;
        boolean isHttps = false;

        TestManagerTask(String url, boolean isHttps) {
            this.isHttps = isHttps;
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                System.out.println("STARTING");


                HttpURLConnection con = null;

                con = (HttpURLConnection) url.openConnection();

                // optional default is GET´
                try {
                    con.setRequestMethod("GET");
                } catch (Exception e) {
                }

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }

}
