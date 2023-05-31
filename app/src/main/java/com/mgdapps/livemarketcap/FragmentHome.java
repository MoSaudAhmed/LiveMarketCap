package com.mgdapps.livemarketcap;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by HP on 12/7/2017.
 */

public class FragmentHome extends android.support.v4.app.Fragment {

    List<String> favlist = null;
    List<GetSetHome> myList = new ArrayList<>();

    myRecyclerAdapter myRecyclerAdapter;
    LinearLayout lay_home_options, lay_home_searchSnapbar;
    ImageView img_home_name, img_home_price, img_home_volume, img_home_change;
    LinearLayout lay_home_market, lay_home_change;
    TextView lay_home_price, lay_home_volume, tv_homerow_change;
    Button btn_fragmenthome_cancel, btn_fragmenthome_add;
    EditText et_fragmenthome_search;
    ViewPager vp_homepager;

    RecyclerView mRecyclerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lay_home_options = (LinearLayout) view.findViewById(R.id.lay_home_options);
        img_home_name = (ImageView) view.findViewById(R.id.img_home_name);
        img_home_price = (ImageView) view.findViewById(R.id.img_home_price);
        img_home_volume = (ImageView) view.findViewById(R.id.img_home_volume);
        img_home_change = (ImageView) view.findViewById(R.id.img_homerow_change);
        lay_home_market = (LinearLayout) view.findViewById(R.id.lay_home_market);
        lay_home_change = (LinearLayout) view.findViewById(R.id.lay_home_change);
        lay_home_price = (TextView) view.findViewById(R.id.lay_home_price);
        tv_homerow_change = (TextView) view.findViewById(R.id.tv_homerow_change);
        lay_home_volume = (TextView) view.findViewById(R.id.lay_home_volume);
        btn_fragmenthome_cancel = (Button) view.findViewById(R.id.btn_fragmenthome_cancel);
        btn_fragmenthome_add = (Button) view.findViewById(R.id.btn_fragmenthome_add);
        et_fragmenthome_search = (EditText) view.findViewById(R.id.et_fragmenthome_search);
        lay_home_searchSnapbar = (LinearLayout) view.findViewById(R.id.lay_home_searchSnapbar);
        vp_homepager = (ViewPager) view.findViewById(R.id.vp_homepager);
        lay_home_searchSnapbar.setVisibility(View.INVISIBLE);

        mRecyclerList = (RecyclerView) view.findViewById(R.id.rv_mrecyclerhome);

        OptionsMenu();

        ViewpagerAdapterHome viewpagerAdapterHome = new ViewpagerAdapterHome(getFragmentManager());
        viewpagerAdapterHome.addFragment(new Home_pager1());
        viewpagerAdapterHome.addFragment(new Home_Pager2());
        viewpagerAdapterHome.addFragment(new Home_pager3());
        viewpagerAdapterHome.addFragment(new Home_pager4());
        vp_homepager.setAdapter(viewpagerAdapterHome);

            MainActivity activity = (MainActivity) getActivity();
            myList = activity.mHomelist;

            final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("favlist", getActivity().MODE_PRIVATE);
            Set<String> myset = sharedPreferences.getStringSet("mylist", null);
            if (myset != null) {
                favlist = new ArrayList<>(myset);
            }

            if (myList != null) {
                myRecyclerAdapter = new myRecyclerAdapter(R.layout.row_market);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerList.setLayoutManager(layoutManager);
                mRecyclerList.setAdapter(myRecyclerAdapter);
            }

            lay_home_market.setOnClickListener(new View.OnClickListener() {
                int a = 0;

                @Override
                public void onClick(View view) {

                    img_home_price.setVisibility(View.INVISIBLE);
                    img_home_volume.setVisibility(View.INVISIBLE);
                    img_home_change.setVisibility(View.INVISIBLE);

                    if (a == 0) {
                        a++;
                        Collections.sort(myList, new homeSorterName(1));
                        img_home_name.setVisibility(View.VISIBLE);
                        img_home_name.setImageResource(R.drawable.icon_white_arrowdown);
                        myRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        a = 0;
                        Collections.sort(myList, new homeSorterName(-1));
                        img_home_name.setVisibility(View.VISIBLE);
                        img_home_name.setImageResource(R.drawable.icon_white_arrowup);
                        myRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            });
            lay_home_price.setOnClickListener(new View.OnClickListener() {
                int a = 0;

                @Override
                public void onClick(View view) {

                    img_home_name.setVisibility(View.INVISIBLE);
                    img_home_volume.setVisibility(View.INVISIBLE);
                    img_home_change.setVisibility(View.INVISIBLE);
                    if (a == 0) {
                        a++;
                        Collections.sort(myList, new homeSorterPrice(1));
                        img_home_price.setVisibility(View.VISIBLE);
                        img_home_price.setImageResource(R.drawable.icon_white_arrowdown);
                        myRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        a = 0;
                        Collections.sort(myList, new homeSorterPrice(-1));
                        img_home_price.setVisibility(View.VISIBLE);
                        img_home_price.setImageResource(R.drawable.icon_white_arrowup);
                        myRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            });

            lay_home_volume.setOnClickListener(new View.OnClickListener() {
                int a = 0;

                @Override
                public void onClick(View view) {
                    img_home_price.setVisibility(View.INVISIBLE);
                    img_home_name.setVisibility(View.INVISIBLE);
                    img_home_change.setVisibility(View.INVISIBLE);
                    if (a == 0) {
                        a++;
                        Collections.sort(myList, new homeSorterVolume(1));
                        img_home_volume.setVisibility(View.VISIBLE);
                        img_home_volume.setImageResource(R.drawable.icon_white_arrowdown);
                        myRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        a = 0;
                        Collections.sort(myList, new homeSorterVolume(-1));
                        img_home_volume.setVisibility(View.VISIBLE);
                        img_home_volume.setImageResource(R.drawable.icon_white_arrowup);
                        myRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            });
            lay_home_change.setOnClickListener(new View.OnClickListener() {
                int a = 0;

                @Override
                public void onClick(View view) {
                    img_home_price.setVisibility(View.INVISIBLE);
                    img_home_volume.setVisibility(View.INVISIBLE);
                    img_home_name.setVisibility(View.INVISIBLE);
                    if (a == 0) {
                        a++;
                        Collections.sort(myList, new homeSorterChange(1));
                        img_home_change.setVisibility(View.VISIBLE);
                        img_home_change.setImageResource(R.drawable.icon_white_arrowdown);
                        myRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        a = 0;
                        Collections.sort(myList, new homeSorterChange(-1));
                        img_home_change.setVisibility(View.VISIBLE);
                        img_home_change.setImageResource(R.drawable.icon_white_arrowup);
                        myRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            });


            return view;
        }

        class myRecyclerAdapter extends RecyclerView.Adapter<mHolderClass> {

            int resource;
            private ArrayList<GetSetHome> arraylist = null;

            myRecyclerAdapter(int resource) {
                this.resource = resource;
                this.arraylist = new ArrayList<GetSetHome>();
                this.arraylist.addAll(myList);
            }

            @Override
            public mHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(resource, parent, false);

                mHolderClass mHolderClass = new mHolderClass(view);
                return mHolderClass;
            }

            @Override
            public void onBindViewHolder(mHolderClass holder, int position) {

                holder.tv_marketname.setText(myList.get(position).getSymbol());
                holder.tv_volume_marketrow.setText(myList.get(position).getVolume_usd());

                final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("checkbox", getActivity().MODE_PRIVATE);
                if (sharedPreferences.getString("price", null) == null) {
                    holder.tv_marketprice.setText(myList.get(position).getPrice_btc() + "");
                    lay_home_price.setText("Price btc");
                } else {
                    lay_home_price.setText("Price USD");
                    holder.tv_marketprice.setText(myList.get(position).getPrice_usd() + "");
                }
                Double change = 0.0;
                if (sharedPreferences.getString("percent", null) == null) {
                    change = Double.valueOf(myList.get(position).getPercent_change_24h());
                    tv_homerow_change.setText("24h %");

                } else if (sharedPreferences.getString("percent", null).equals("change24")) {
                    change = Double.valueOf(myList.get(position).getPercent_change_24h());
                    tv_homerow_change.setText("24h %");
                } else if (sharedPreferences.getString("percent", null).equals("change1")) {
                    tv_homerow_change.setText("1h %");
                    change = Double.valueOf(myList.get(position).getPercent_change_1h());
                } else {
                    change = Double.valueOf(myList.get(position).getPercent_change_7d());
                    tv_homerow_change.setText("7d %");
                }

                if (change > 0) {
                    holder.tv_change_marketrow.setTextColor(Color.parseColor("#008000"));
                    holder.tv_change_marketrow.setText(change + "");
                } else if (change < 0) {
                    holder.tv_change_marketrow.setTextColor(Color.parseColor("#FF0000"));
                    holder.tv_change_marketrow.setText(change + "");

                }
            }

            @Override
            public int getItemCount() {
                return myList.size();
            }

        }

        public class mHolderClass extends RecyclerView.ViewHolder {
            TextView tv_marketname, tv_marketprice, tv_volume_marketrow, tv_change_marketrow;

            public mHolderClass(View itemView) {
                super(itemView);
                tv_marketname = (TextView) itemView.findViewById(R.id.tv_name_marketrow);
                tv_marketprice = (TextView) itemView.findViewById(R.id.tv_price_marketrow);
                tv_volume_marketrow = (TextView) itemView.findViewById(R.id.tv_volume_marketrow);
                tv_change_marketrow = (TextView) itemView.findViewById(R.id.tv_change_marketrow);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = mRecyclerList.getChildAdapterPosition(view);
                        Intent intent = new Intent(getActivity(), SingleMarketActivity.class);
                        intent.putExtra("name", myList.get(i).getName());
                        intent.putExtra("symbol", myList.get(i).getSymbol());
                        intent.putExtra("rank", myList.get(i).getRank());
                        intent.putExtra("price_usd", myList.get(i).getPrice_usd());
                        intent.putExtra("price_btc", myList.get(i).getPrice_btc());
                        intent.putExtra("24h_volume_usd", myList.get(i).getVolume_usd());
                        intent.putExtra("market_cap_usd", myList.get(i).getMarket_cap_usd());
                        intent.putExtra("available_supply", myList.get(i).getAvailable_supply());
                        intent.putExtra("total_supply", myList.get(i).getTotal_supply());
                        intent.putExtra("max_supply", myList.get(i).getMax_supply());
                        intent.putExtra("percent_change_1h", myList.get(i).getPercent_change_1h());
                        intent.putExtra("percent_change_24h", myList.get(i).getPercent_change_24h());
                        intent.putExtra("percent_change_7d", myList.get(i).getPercent_change_7d());
                        intent.putExtra("last_updated", myList.get(i).getLast_updated());
                        intent.putExtra("link", myList.get(i).getLink());

                        startActivity(intent);

                    }
                });
            }
        }


        private void OptionsMenu() {
            lay_home_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu popupMenu = new PopupMenu(getActivity(), lay_home_options);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_options_home, popupMenu.getMenu());
                    final MenuItem priceitem = popupMenu.getMenu().findItem(R.id.popup_options_home_price);
                    final MenuItem change1 = popupMenu.getMenu().findItem(R.id.popup_options_home_change1);
                    final MenuItem change24 = popupMenu.getMenu().findItem(R.id.popup_options_home_change24);
                    final MenuItem change7 = popupMenu.getMenu().findItem(R.id.popup_options_home_change7);

                    final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("checkbox", getActivity().MODE_PRIVATE);
                    if (sharedPreferences.getString("price", null) == null) {
                        priceitem.setChecked(false);
                    } else {
                        priceitem.setChecked(true);
                    }

                    if (sharedPreferences.getString("percent", null) != null) {
                        if (sharedPreferences.getString("percent", null).equals("change1")) {
                            change1.setChecked(true);
                        } else if (sharedPreferences.getString("percent", null).equals("change24")) {
                            change24.setChecked(true);
                        } else if (sharedPreferences.getString("percent", null).equals("change7")) {
                            change7.setChecked(true);
                        }
                    } else {
                        change24.setChecked(true);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {

                                case R.id.popup_options_home_refresh: {
                                    myList.clear();
                                    BackgroundTaskHome backgroundTaskHome = new BackgroundTaskHome(getActivity(), favlist, "abc");
                                    backgroundTaskHome.execute();
                                    myRecyclerAdapter.notifyDataSetChanged();
                                    break;
                                }
                                case R.id.popup_options_home_add: {

                                    lay_home_searchSnapbar.setVisibility(View.VISIBLE);

                                    btn_fragmenthome_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            et_fragmenthome_search.setText("");
                                            lay_home_searchSnapbar.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                    btn_fragmenthome_add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (et_fragmenthome_search.getText().length() > 0) {

                                                String newname = et_fragmenthome_search.getText().toString().replaceAll("\\s+", "-");
                                                AddBackgroundTask addBackgroundtask = new AddBackgroundTask(newname);
                                                addBackgroundtask.execute();
                                            }
                                        }
                                    });

                                    break;
                                }
                                case R.id.popup_options_home_price: {

                                    if (priceitem.isChecked()) {
                                        priceitem.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("price", null);
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    } else {
                                        priceitem.setChecked(true);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("price", "checked");
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    }

                                    break;
                                }
                                case R.id.popup_options_home_change1: {
                                    if (change1.isChecked()) {
                                        change1.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("percent", null);
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    } else {
                                        change1.setChecked(true);
                                        change24.setChecked(false);
                                        change7.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("percent", "change1");
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                }
                                case R.id.popup_options_home_change24: {//sharedPreferences.getString("percent",null)
                                    if (change24.isChecked()) {
                                        change24.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("percent", null);
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    } else {
                                        change24.setChecked(true);
                                        change7.setChecked(false);
                                        change1.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("percent", "change24");
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                }
                                case R.id.popup_options_home_change7: {
                                    if (change7.isChecked()) {
                                        change7.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("percent", null);
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    } else {
                                        change7.setChecked(true);
                                        change24.setChecked(false);
                                        change1.setChecked(false);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("percent", "change7");
                                        editor.commit();
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }

                            return true;
                        }
                    });

                    popupMenu.show();

                }
            });
        }

        static class homeSorterName implements Comparator<GetSetHome> {
            int order = -1;

            homeSorterName(int order) {
                this.order = order;
            }

            @Override
            public int compare(GetSetHome o, GetSetHome t1) {
                if (o.getSymbol().toString().compareTo(t1.getSymbol().toString()) == 0) return 0;

                else if (o.getSymbol().toString().compareTo(t1.getSymbol().toString()) < 0)
                    return order;
                else {
                    return (-1 * order);
                }
            }
        }

        static class homeSorterPrice implements Comparator<GetSetHome> {
            int order = -1;

            homeSorterPrice(int order) {
                this.order = order;
            }

            @Override
            public int compare(GetSetHome o, GetSetHome t1) {
                if (Double.valueOf(o.getPrice_btc()).compareTo(Double.valueOf(t1.getPrice_btc())) == 0)
                    return 0;

                else if (Double.valueOf(o.getPrice_btc()).compareTo(Double.valueOf(t1.getPrice_btc())) < 0)
                    return order;
                else {
                    return (-1 * order);
                }
            }
        }

        static class homeSorterVolume implements Comparator<GetSetHome> {
            int order = -1;

            homeSorterVolume(int order) {
                this.order = order;
            }

            @Override
            public int compare(GetSetHome o, GetSetHome t1) {
                if (Double.valueOf(o.getVolume_usd()).compareTo(Double.valueOf(t1.getVolume_usd())) == 0)
                    return 0;

                else if (Double.valueOf(o.getVolume_usd()).compareTo(Double.valueOf(t1.getVolume_usd())) < 0)
                    return order;
                else {
                    return (-1 * order);
                }
            }
        }

        static class homeSorterChange implements Comparator<GetSetHome> {
            int order = -1;

            homeSorterChange(int order) {
                this.order = order;
            }

            @Override
            public int compare(GetSetHome o, GetSetHome t1) {
                if (Double.valueOf(o.getPercent_change_24h()).compareTo(Double.valueOf(t1.getPercent_change_24h())) == 0)
                    return 0;

                else if (Double.valueOf(o.getPercent_change_24h()).compareTo(Double.valueOf(t1.getPercent_change_24h())) < 0)
                    return order;
                else {
                    return (-1 * order);
                }
            }
        }

        private class AddBackgroundTask extends AsyncTask<String, String, String> {
            String line;
            String coinName;

            AddBackgroundTask(String coinname) {
                this.coinName = coinname.toLowerCase();
            }

            ProgressDialog progressDialogue = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialogue.setTitle("Please Wait!");
                progressDialogue.setMessage("Searching " + et_fragmenthome_search.getText().toString());
                progressDialogue.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url = new URL("https://api.coinmarketcap.com/v1/ticker/" + coinName);
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
                    String name = jsonObject.getString("symbol");
                    getSetHome.setSymbol(name);
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

                    return name;

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
                progressDialogue.dismiss();

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.create();
                if (s == null) {
                    alert.setTitle("Oops couldn't find " + et_fragmenthome_search.getText().toString());
                    alert.setMessage("Please enter correct Coin name like Bitcoin,Ripple,Bitcoin Cash...");
                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                } else {
                    Toast.makeText(getActivity(), "Coin Added Successful", Toast.LENGTH_LONG).show();
                    myRecyclerAdapter.notifyDataSetChanged();
                    final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("favlist", MODE_PRIVATE);
                    Set<String> myset = sharedPreferences.getStringSet("mylist", null);
                    List<String> favList = null;
                    if (myset != null) {
                        favList = new ArrayList<>(myset);
                    } else {
                        favList = new ArrayList<>();
                        favList.add("Bitcoin");
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (!favList.contains(coinName)) {
                        favList.add(coinName);
                        Set<String> myset1 = new HashSet<>();
                        myset1.addAll(favList);
                        editor.putStringSet("mylist", myset1);
                        editor.commit();
                    }
                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new MyTimerTask(), 5000, 7500);
        }

        public class MyTimerTask extends TimerTask {

            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (vp_homepager.getCurrentItem() == 0) {
                                vp_homepager.setCurrentItem(1);
                            } else if (vp_homepager.getCurrentItem() == 1) {
                                vp_homepager.setCurrentItem(2);
                            } else if (vp_homepager.getCurrentItem() == 2) {
                                vp_homepager.setCurrentItem(3);
                            } else {
                                vp_homepager.setCurrentItem(0);
                            }
                        }
                    });
                }
            }
        }
}
