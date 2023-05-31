package com.mgdapps.livemarketcap;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 12/21/2017.
 */

public class BackgroundTaskMarket extends AsyncTask<Object, Object, List<GetSetMarket>> {

    String murl;
    Context context;
    List<GetSetMarket> mylist = new ArrayList<>();
    InterfaceMarket interfaceMarket;
    InterfaceMarket interfaceMarket;
    ProgressBar progressBar;
    TextView textView;

    BackgroundTaskMarket(Context context, TextView textView, ProgressBar progressBar, String murl) {
        this.progressBar = progressBar;
        this.context = context;
        this.textView = textView;
        this.murl = murl;
        interfaceMarket = (InterfaceMarket) context;
    }

    BackgroundTaskMarket(Context context, String murl) {
        this.context = context;
        this.murl = murl;
        interfaceMarket = (InterfaceMarket) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (textView != null) {
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected List<GetSetMarket> doInBackground(Object... strings) {

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
                JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    GetSetMarket getSetMarket = new GetSetMarket();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    getSetMarket.setName(jsonObject.getString("name"));
                    getSetMarket.setSymbol(jsonObject.getString("symbol"));
                    getSetMarket.setRank(jsonObject.getInt("rank"));
                    getSetMarket.setPrice_usd(jsonObject.getString("price_usd"));
                    getSetMarket.setPrice_btc(jsonObject.getString("price_btc"));
                    getSetMarket.setVolume_usd(jsonObject.getString("24h_volume_usd"));
                    getSetMarket.setMarket_cap_usd(jsonObject.getString("market_cap_usd"));
                    getSetMarket.setAvailable_supply(jsonObject.getString("available_supply"));
                    getSetMarket.setTotal_supply(jsonObject.getString("total_supply"));
                    getSetMarket.setMax_supply(jsonObject.getString("max_supply"));
                    getSetMarket.setPercent_change_1h(BigDecimal.valueOf(jsonObject.getDouble("percent_change_1h")).toPlainString());
                    getSetMarket.setPercent_change_24h(BigDecimal.valueOf(jsonObject.getDouble("percent_change_24h")).toPlainString());
                    getSetMarket.setPercent_change_7d(String.valueOf(BigDecimal.valueOf(jsonObject.getDouble("percent_change_7d")).toPlainString()));
                    getSetMarket.setLast_updated(jsonObject.getString("last_updated"));

                    mylist.add(getSetMarket);
                }

                return mylist;
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
    protected void onPostExecute(List<GetSetMarket> s) {
        super.onPostExecute(s);
        if (textView != null) {
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        interfaceMarket.MarketCallback(mylist);
    }
}
