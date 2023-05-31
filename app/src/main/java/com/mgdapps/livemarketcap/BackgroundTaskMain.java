package com.mgdapps.livemarketcap;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HP on 12/12/2017.
 */

public class BackgroundTaskMain extends AsyncTask<String, String, String> {

    String murl;
    ProgressBar progressBar;
    TextView textView;
    Context context;

    BackgroundTaskMain(String url) {
        this.murl = url;
    }

    BackgroundTaskMain(Context context, TextView textView, ProgressBar progressBar, String url) {
        murl = url;
        this.progressBar = progressBar;
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {

        String line;
        try {
            URL url = new URL(murl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {

                stringBuffer.append(line);
            }

            if (stringBuffer.toString() != null) {

            } else {
                Toast.makeText(context, "Error, please try again later...", Toast.LENGTH_LONG).show();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }
}
