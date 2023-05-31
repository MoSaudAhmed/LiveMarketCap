package com.mgdapps.livemarketcap;

import android.app.ProgressDialog;
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
 * Created by HP on 1/4/2018.
 */

public class BackgroundTaskHome extends AsyncTask<String, String, String> {
    String line;
    ProgressDialog progressdialogue;
    Context context;
    List<GetSetHome> myList = new ArrayList<>();
    InterfaceHome interfaceHome;
    List<String> favlist = new ArrayList<>();
    String abc="";

    BackgroundTaskHome(Context context,List<String> homelist,String progress)
    {
        this.context=context;
        interfaceHome=(InterfaceHome) context;
        favlist=homelist;
        this.abc=progress;
    }

    BackgroundTaskHome(Context context,List<String> homelist)
    {
        this.context=context;
        interfaceHome=(InterfaceHome) context;
        favlist=homelist;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (abc.equals("abc"))
        {
            progressdialogue = new ProgressDialog(context);
            progressdialogue.setCancelable(false);
            progressdialogue.setTitle("Please wait!");
            progressdialogue.setMessage("Loading data...");
            progressdialogue.show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        for (int i = 0; i < favlist.size(); i++) {
            String murl = favlist.get(i);

            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/" + murl);
                HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpUrlConnection.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                while ((line = bufferReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                GetSetHome getSetHome = new GetSetHome();
                getSetHome.setName(jsonObject.getString("name"));
                getSetHome.setSymbol(jsonObject.getString("symbol"));
                getSetHome.setRank(jsonObject.getInt("rank"));
                getSetHome.setPrice_usd(jsonObject.getString("price_usd"));
                getSetHome.setPrice_btc(jsonObject.getString("price_btc"));
                getSetHome.setVolume_usd(jsonObject.getString("24h_volume_usd"));
                getSetHome.setMarket_cap_usd(jsonObject.getString("market_cap_usd"));
                getSetHome.setAvailable_supply(jsonObject.getString("available_supply"));
                getSetHome.setTotal_supply(jsonObject.getString("total_supply"));
                getSetHome.setMax_supply(jsonObject.getString("max_supply"));
                getSetHome.setPercent_change_1h(jsonObject.getString("percent_change_1h"));
                getSetHome.setPercent_change_24h(jsonObject.getString("percent_change_24h"));
                getSetHome.setPercent_change_7d(jsonObject.getString("percent_change_7d"));
                getSetHome.setLast_updated(jsonObject.getString("last_updated"));

                myList.add(getSetHome);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        interfaceHome.HomeCallback(myList);
        if (abc.equals("abc"))
        {
            progressdialogue.dismiss();
        }
        else
        {
            Toast.makeText(context,"Oops, there was an error",Toast.LENGTH_LONG).show();
        }
    }
}