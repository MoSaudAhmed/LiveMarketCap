package com.mgdapps.livemarketcap;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 12/16/2017.
 */

public class BackgroundTaskNews extends AsyncTask<Object, Object, List<GetSetNews>> {

    Context context;
    String murl;

    InterfaceNews interfaceNews;

    BackgroundTaskNews(Context context, String url) {
        this.context = context;
        murl = url;
        interfaceNews = (InterfaceNews) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<GetSetNews> doInBackground(Object... strings) {

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

            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            if (jsonObject.getString("status").equals("ok")) {
                JSONArray itemsArray = jsonObject.getJSONArray("articles");

                List<GetSetNews> getSetNewsList = new ArrayList<>();
                for (int i = 0; i < itemsArray.length(); i++) {
                    GetSetNews getSetNews = new GetSetNews();

                    JSONObject itemObject = itemsArray.getJSONObject(i);
                    getSetNews.setTitle(itemObject.getString("title"));
                    getSetNews.setPubDate(itemObject.getString("publishedAt"));
                    getSetNews.setLink(itemObject.getString("url"));
                    getSetNews.setAuthor(itemObject.getString("author"));
                    getSetNews.setContent(itemObject.getString("description"));
                    getSetNews.setImage(itemObject.getString("urlToImage"));

                    getSetNewsList.add(getSetNews);
                }

                return getSetNewsList;
            } else {
                Toast.makeText(context, "Error, please try again later...", Toast.LENGTH_LONG).show();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(List<GetSetNews> s) {
        super.onPostExecute(s);
        interfaceNews.NewsCallback(s);

    }
}
