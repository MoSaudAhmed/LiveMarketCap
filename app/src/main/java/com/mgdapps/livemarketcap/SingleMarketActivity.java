package com.mgdapps.livemarketcap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleMarketActivity extends AppCompatActivity {

    CircleImageView img_singlemarket;
    ImageView img_market_change1h, img_market_change24h, img_market_change7d, img_fav_addremove;
    TextView tv_market_lastupdate, tv_market_title, tv_market_rank, tv_market_priceBTC, tv_market_priceUSD, tv_market_volume,
            tv_market_marketCapital, tv_market_available_supply, tv_market_total_supply, tv_market_max_supply,
            tv_market_percent_change_1h, tv_market_percent_change_24h, tv_market_percent_change_7d, tv_single_fav_addremove;

    List<String> favList = null;
    String newname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_market);

        getSupportActionBar().hide();

        img_singlemarket = (CircleImageView) findViewById(R.id.img_singlemarket);
        img_market_change1h = (ImageView) findViewById(R.id.img_market_change1h);
        img_market_change24h = (ImageView) findViewById(R.id.img_market_change24h);
        img_market_change7d = (ImageView) findViewById(R.id.img_market_change7d);
        tv_market_lastupdate = (TextView) findViewById(R.id.tv_market_lastupdate);
        tv_market_title = (TextView) findViewById(R.id.tv_market_title);
        tv_market_rank = (TextView) findViewById(R.id.tv_market_rank);
        tv_market_priceBTC = (TextView) findViewById(R.id.tv_market_priceBTC);
        tv_market_priceUSD = (TextView) findViewById(R.id.tv_market_priceUSD);
        tv_market_volume = (TextView) findViewById(R.id.tv_market_volume);
        tv_market_marketCapital = (TextView) findViewById(R.id.tv_market_marketCapital);
        tv_market_available_supply = (TextView) findViewById(R.id.tv_market_available_supply);
        tv_market_total_supply = (TextView) findViewById(R.id.tv_market_total_supply);
        tv_market_max_supply = (TextView) findViewById(R.id.tv_market_max_supply);
        tv_market_percent_change_1h = (TextView) findViewById(R.id.tv_market_percent_change_1h);
        tv_market_percent_change_24h = (TextView) findViewById(R.id.tv_market_percent_change_24h);
        tv_market_percent_change_7d = (TextView) findViewById(R.id.tv_market_percent_change_7d);

        img_fav_addremove = (ImageView) findViewById(R.id.img_fav_addremove);
        tv_single_fav_addremove = (TextView) findViewById(R.id.tv_single_fav_addremove);

        Intent intent = getIntent();
        if (intent != null) {
            final String name = intent.getStringExtra("name");
            final String symbol = intent.getStringExtra("symbol");
            int rank = intent.getIntExtra("rank", 0);
            String price_usd = intent.getStringExtra("price_usd");
            String price_btc = intent.getStringExtra("price_btc");
            String volume_usd = intent.getStringExtra("24h_volume_usd");
            String market_cap_usd = intent.getStringExtra("market_cap_usd");
            String available_supply = intent.getStringExtra("available_supply");
            String total_supply = intent.getStringExtra("total_supply");
            String max_supply = intent.getStringExtra("max_supply");
            String percent_change_1h = intent.getStringExtra("percent_change_1h");
            String percent_change_24h = intent.getStringExtra("percent_change_24h");
            String percent_change_7d = intent.getStringExtra("percent_change_7d");
            String last_updated = intent.getStringExtra("last_updated");

            tv_market_title.setText(name + "(" + symbol + ")");
            tv_market_rank.setText("Rank: " + rank);
            tv_market_priceBTC.setText("Price (BTC): " + price_btc);
            tv_market_priceUSD.setText("Price (USD): " + price_usd);
            tv_market_volume.setText("24h Volume (USD): " + volume_usd);
            tv_market_marketCapital.setText("Market Cap (USD): " + market_cap_usd);
            tv_market_available_supply.setText("Available Coins Supply: " + available_supply);
            tv_market_total_supply.setText("Total Coins Supply: " + total_supply);
            tv_market_max_supply.setText("Max Coins Supply: " + max_supply);

            newname = name.replaceAll("\\s+", "-").toLowerCase();

            long timestampString = Long.parseLong(last_updated);
            String value = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
                    format(new java.util.Date(timestampString * 1000));
            tv_market_lastupdate.setText("Last Update: " + value);

            SingleMarketBackground singleMarketBackground = new SingleMarketBackground(this, symbol);
            singleMarketBackground.execute();
            if (Double.valueOf(percent_change_1h) > 0) {
                img_market_change1h.setImageResource(R.drawable.icon_arrowup);
            } else {
                img_market_change1h.setImageResource(R.drawable.icon_arrowdown);
            }

            if (Double.valueOf(percent_change_24h) > 0) {
                img_market_change24h.setImageResource(R.drawable.icon_arrowup);
            } else {
                img_market_change24h.setImageResource(R.drawable.icon_arrowdown);
            }

            if (Double.valueOf(percent_change_7d) > 0) {
                img_market_change7d.setImageResource(R.drawable.icon_arrowup);
            } else {
                img_market_change7d.setImageResource(R.drawable.icon_arrowdown);
            }

            tv_market_percent_change_1h.setText("Percentage Change in 1hr: " + percent_change_1h + " %");
            tv_market_percent_change_24h.setText("Percentage Change in 21hr: " + percent_change_24h + " %");
            tv_market_percent_change_7d.setText("Percentage Change in 7d: " + percent_change_7d + " %");

            final SharedPreferences sharedPreferences = getSharedPreferences("favlist", MODE_PRIVATE);
            Set<String> myset = sharedPreferences.getStringSet("mylist", null);
            if (myset != null) {
                favList = new ArrayList<>(myset);
            } else {
                favList = new ArrayList<>();
                favList.add("Bitcoin");
            }
            if (favList.contains(newname)) {
                img_fav_addremove.setImageResource(R.drawable.icon_fav_remove);
                tv_single_fav_addremove.setText("Remove Favoutire");
            } else {
                img_fav_addremove.setImageResource(R.drawable.icon_fav_add);
                tv_single_fav_addremove.setText("Add to Favourite");
            }

            img_fav_addremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (favList.contains(newname)) {
                        img_fav_addremove.setImageResource(R.drawable.icon_fav_add);
                        tv_single_fav_addremove.setText("Add to Favourite");
                        Toast.makeText(SingleMarketActivity.this, "Removed from favourites", Toast.LENGTH_LONG).show();
                        favList.remove(newname);
                        Set<String> myset = new HashSet<>();
                        myset.addAll(favList);
                        editor.putStringSet("mylist", myset);
                        editor.commit();

                    } else {

                        img_fav_addremove.setImageResource(R.drawable.icon_fav_remove);
                        tv_single_fav_addremove.setText("Remove Favourite");
                        Toast.makeText(SingleMarketActivity.this, "Added to favourites", Toast.LENGTH_LONG).show();
                        favList.add(newname);
                        Set<String> myset = new HashSet<>();
                        myset.addAll(favList);
                        editor.putStringSet("mylist", myset);
                        editor.commit();
                    }
                }
            });

        }
    }

    private class SingleMarketBackground extends AsyncTask<String, String, String> {
        Context context;
        String target;

        SingleMarketBackground(Context context, String target) {
            this.context = context;
            this.target = target;
        }

        @Override
        protected String doInBackground(String... strings) {

            String link;
            try {
                URL url = new URL("https://www.cryptocompare.com/api/data/coinlist/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((link = bufferedReader.readLine()) != null) {
                    stringBuffer.append(link);
                }

                JSONObject jsonObject = new JSONObject(stringBuffer.toString());
                if (jsonObject.getString("Response").equals("Success")) {
                    JSONObject dataObject = jsonObject.getJSONObject("Data");
                    JSONObject targetObject = dataObject.getJSONObject(target);

                    return targetObject.getString("ImageUrl");
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Picasso.with(context).load("https://www.cryptocompare.com" + String.valueOf(s)).fit().error(R.drawable.logo).into(img_singlemarket);
        }
    }
}
