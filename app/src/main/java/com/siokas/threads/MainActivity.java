package com.siokas.threads;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    // Declare the widgets
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate the widgets with their ids
        button = (Button) findViewById(R.id.bt);
        textView = (TextView) findViewById(R.id.tv);


        // Set an OnClickListener to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://applications.opap.gr/DrawsRestServices/joker/last.json";

                // To create the new Thread we have to make the Object of the class
                ThreadClass threadClass = new ThreadClass();

                // and call the execute() method of the object
                threadClass.execute(url);

            }
        });
    }

    public class ThreadClass extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPostExecute(String s) {
            // This method runs after the background thread finished
            textView.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            // This method gets the progress and runs in the UI (main) Thread
        }

        @Override
        protected String doInBackground(String... params) {
            String line = "";

            try {
                // Create the url object
                final URL url = new URL(params[0]);

                // Create the http connection and open it
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Get the input Stream of the connection and add it as parameter in a buffered reader object
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                // Run the code until there are no more lines in the response
                while((line = br.readLine()) != null){
                    line +=line;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return line;
        }
    }

}
