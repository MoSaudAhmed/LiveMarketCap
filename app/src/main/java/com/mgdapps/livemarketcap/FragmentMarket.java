package com.mgdapps.livemarketcap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by HP on 12/7/2017.
 */

public class FragmentMarket extends android.support.v4.app.Fragment {

    ListView lv_marketlist;
    List<GetSetMarket> myList = new ArrayList<>();
    LinearLayout lay_market_options;
    RelativeLayout marketLayout;
    marketAdapter marketAdapter;
    LinearLayout lay_market_market, lay_market_change;
    TextView lay_market_price, lay_market_volume, tv_marketrow_change;
    ImageView img_market_name, img_market_price, img_market_volume, img_market_change;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_market, container, false);
        lv_marketlist = (ListView) view.findViewById(R.id.lv_marketlist);
        marketLayout = (RelativeLayout) view.findViewById(R.id.lay_market_searchSnapbar);
        lay_market_options = (LinearLayout) view.findViewById(R.id.lay_market_options);
        lay_market_market = (LinearLayout) view.findViewById(R.id.lay_market_market);
        lay_market_change = (LinearLayout) view.findViewById(R.id.lay_market_change);
        lay_market_price = (TextView) view.findViewById(R.id.lay_market_price);
        lay_market_volume = (TextView) view.findViewById(R.id.lay_market_volume);
        img_market_name = (ImageView) view.findViewById(R.id.img_market_name);
        img_market_price = (ImageView) view.findViewById(R.id.img_market_price);
        img_market_volume = (ImageView) view.findViewById(R.id.img_market_volume);
        img_market_change = (ImageView) view.findViewById(R.id.img_marketrow_change);
        tv_marketrow_change = (TextView) view.findViewById(R.id.tv_marketrow_change);

        optionsMenu();
        MainActivity activity = (MainActivity) getActivity();
        myList = activity.mMarketlist;
        if (myList != null) {
            marketAdapter = new marketAdapter(getActivity(), R.layout.row_market);
            lv_marketlist.setAdapter(marketAdapter);
        }

        lv_marketlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

        lay_market_market.setOnClickListener(new View.OnClickListener() {
            int a = 0;

            @Override
            public void onClick(View view) {

                img_market_price.setVisibility(View.INVISIBLE);
                img_market_volume.setVisibility(View.INVISIBLE);
                img_market_change.setVisibility(View.INVISIBLE);

                if (a == 0) {
                    a++;
                    Collections.sort(myList, new SorterName(1));
                    img_market_name.setVisibility(View.VISIBLE);
                    img_market_name.setImageResource(R.drawable.icon_white_arrowdown);
                    marketAdapter.notifyDataSetChanged();

                } else {
                    a = 0;
                    Collections.sort(myList, new SorterName(-1));
                    img_market_name.setVisibility(View.VISIBLE);
                    img_market_name.setImageResource(R.drawable.icon_white_arrowup);
                    marketAdapter.notifyDataSetChanged();
                }
            }
        });
        lay_market_price.setOnClickListener(new View.OnClickListener() {
            int a = 0;

            @Override
            public void onClick(View view) {

                img_market_name.setVisibility(View.INVISIBLE);
                img_market_volume.setVisibility(View.INVISIBLE);
                img_market_change.setVisibility(View.INVISIBLE);
                if (a == 0) {
                    a++;
                    Collections.sort(myList, new SorterPrice(1));
                    img_market_price.setVisibility(View.VISIBLE);
                    img_market_price.setImageResource(R.drawable.icon_white_arrowdown);
                    marketAdapter.notifyDataSetChanged();

                } else {
                    a = 0;
                    Collections.sort(myList, new SorterPrice(-1));
                    img_market_price.setVisibility(View.VISIBLE);
                    img_market_price.setImageResource(R.drawable.icon_white_arrowup);
                    marketAdapter.notifyDataSetChanged();
                }
            }
        });

        lay_market_volume.setOnClickListener(new View.OnClickListener() {
            int a = 0;

            @Override
            public void onClick(View view) {
                img_market_price.setVisibility(View.INVISIBLE);
                img_market_name.setVisibility(View.INVISIBLE);
                img_market_change.setVisibility(View.INVISIBLE);
                if (a == 0) {
                    a++;
                    Collections.sort(myList, new SorterVolume(1));
                    img_market_volume.setVisibility(View.VISIBLE);
                    img_market_volume.setImageResource(R.drawable.icon_white_arrowdown);
                    marketAdapter.notifyDataSetChanged();

                } else {
                    a = 0;
                    Collections.sort(myList, new SorterVolume(-1));
                    img_market_volume.setVisibility(View.VISIBLE);
                    img_market_volume.setImageResource(R.drawable.icon_white_arrowup);
                    marketAdapter.notifyDataSetChanged();
                }
            }
        });
        lay_market_change.setOnClickListener(new View.OnClickListener() {
            int a = 0;

            @Override
            public void onClick(View view) {
                img_market_price.setVisibility(View.INVISIBLE);
                img_market_volume.setVisibility(View.INVISIBLE);
                img_market_name.setVisibility(View.INVISIBLE);
                if (a == 0) {
                    a++;
                    Collections.sort(myList, new SorterChange(1));
                    img_market_change.setVisibility(View.VISIBLE);
                    img_market_change.setImageResource(R.drawable.icon_white_arrowdown);
                    marketAdapter.notifyDataSetChanged();

                } else {
                    a = 0;
                    Collections.sort(myList, new SorterChange(-1));
                    img_market_change.setVisibility(View.VISIBLE);
                    img_market_change.setImageResource(R.drawable.icon_white_arrowup);
                    marketAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    private void optionsMenu() {
        lay_market_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), lay_market_options);
                popupMenu.getMenuInflater().inflate(R.menu.popup_options_market, popupMenu.getMenu());
                final MenuItem priceitem = popupMenu.getMenu().findItem(R.id.popup_options_market_price);
                final MenuItem change1 = popupMenu.getMenu().findItem(R.id.popup_options_market_percent1);
                final MenuItem change24 = popupMenu.getMenu().findItem(R.id.popup_options_market_percent24);
                final MenuItem change7 = popupMenu.getMenu().findItem(R.id.popup_options_market_percent7);
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
                            case R.id.popup_market_search: {
                                final Snackbar snackbar = Snackbar.make(marketLayout, "", Snackbar.LENGTH_INDEFINITE);
                                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

                                LayoutInflater layoutInflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View snackview = layoutInflator.inflate(R.layout.search_snackbar_layout, null);

                                Button btn_snackbar_done = (Button) snackview.findViewById(R.id.btn_snackbar_done);
                                Button btn_snackbar_cancel = (Button) snackview.findViewById(R.id.btn_snackbar_cancel);
                                final EditText et_snackbar_search = (EditText) snackview.findViewById(R.id.et_snackbar_search);
                                layout.addView(snackview, 0);
                                snackbar.show();

                                btn_snackbar_done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        snackbar.dismiss();

                                        InputMethodManager inputManager = (InputMethodManager)
                                                getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

                                        if (getActivity().getCurrentFocus() != null) {
                                            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                                    InputMethodManager.HIDE_NOT_ALWAYS);
                                        }
                                    }
                                });

                                btn_snackbar_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        marketAdapter.filter("");
                                        et_snackbar_search.setText("");
                                        img_market_price.setVisibility(View.INVISIBLE);
                                        img_market_volume.setVisibility(View.INVISIBLE);
                                        img_market_change.setVisibility(View.INVISIBLE);
                                        img_market_name.setVisibility(View.INVISIBLE);
                                    }
                                });
                                et_snackbar_search.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String text = et_snackbar_search.getText().toString().toLowerCase(Locale.getDefault());
                                        marketAdapter.filter(text);
                                    }
                                });
                                break;
                            }
                            case R.id.popup_market_default: {
                                marketAdapter.filter("");
                                img_market_price.setVisibility(View.INVISIBLE);
                                img_market_volume.setVisibility(View.INVISIBLE);
                                img_market_change.setVisibility(View.INVISIBLE);
                                img_market_name.setVisibility(View.INVISIBLE);
                                break;
                            }
                            case R.id.popup_market_refresh: {
                                BackgroundTaskMarket backgroundTaskMarket = new BackgroundTaskMarket(getActivity(), "https://api.coinmarketcap.com/v1/ticker/?start=0&limit=250");
                                backgroundTaskMarket.execute();
                                break;
                            }
                            case R.id.popup_options_market_price: {

                                if (priceitem.isChecked()) {
                                    priceitem.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("price", null);
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                } else {
                                    priceitem.setChecked(true);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("price", "checked");
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                }

                                break;
                            }
                            case R.id.popup_options_market_percent1: {
                                if (change1.isChecked()) {
                                    change1.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("percent", null);
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                } else {
                                    change1.setChecked(true);
                                    change24.setChecked(false);
                                    change7.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("percent", "change1");
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                            case R.id.popup_options_market_percent24: {
                                if (change24.isChecked()) {
                                    change24.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("percent", null);
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                } else {
                                    change24.setChecked(true);
                                    change7.setChecked(false);
                                    change1.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("percent", "change24");
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                            case R.id.popup_options_market_percent7: {
                                if (change7.isChecked()) {
                                    change7.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("percent", null);
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                } else {
                                    change7.setChecked(true);
                                    change24.setChecked(false);
                                    change1.setChecked(false);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("percent", "change7");
                                    editor.commit();
                                    marketAdapter.notifyDataSetChanged();
                                }
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

    static class SorterName implements Comparator<GetSetMarket> {
        int order = -1;

        SorterName(int order) {
            this.order = order;
        }

        @Override
        public int compare(GetSetMarket o, GetSetMarket t1) {
            if (o.getSymbol().toString().compareTo(t1.getSymbol().toString()) == 0) return 0;

            else if (o.getSymbol().toString().compareTo(t1.getSymbol().toString()) < 0)
                return order;
            else {
                return (-1 * order);
            }
        }
    }

    static class SorterPrice implements Comparator<GetSetMarket> {
        int order = -1;

        SorterPrice(int order) {
            this.order = order;
        }

        @Override
        public int compare(GetSetMarket o, GetSetMarket t1) {
            if (Double.valueOf(o.getPrice_btc()).compareTo(Double.valueOf(t1.getPrice_btc())) == 0)
                return 0;

            else if (Double.valueOf(o.getPrice_btc()).compareTo(Double.valueOf(t1.getPrice_btc())) < 0)
                return order;
            else {
                return (-1 * order);
            }
        }
    }

    static class SorterVolume implements Comparator<GetSetMarket> {
        int order = -1;

        SorterVolume(int order) {
            this.order = order;
        }

        @Override
        public int compare(GetSetMarket o, GetSetMarket t1) {
            if (Double.valueOf(o.getVolume_usd()).compareTo(Double.valueOf(t1.getVolume_usd())) == 0)
                return 0;

            else if (Double.valueOf(o.getVolume_usd()).compareTo(Double.valueOf(t1.getVolume_usd())) < 0)
                return order;
            else {
                return (-1 * order);
            }
        }
    }

    static class SorterChange implements Comparator<GetSetMarket> {
        int order = -1;

        SorterChange(int order) {
            this.order = order;
        }

        @Override
        public int compare(GetSetMarket o, GetSetMarket t1) {
            if (Double.valueOf(o.getPercent_change_24h()).compareTo(Double.valueOf(t1.getPercent_change_24h())) == 0)
                return 0;

            else if (Double.valueOf(o.getPercent_change_24h()).compareTo(Double.valueOf(t1.getPercent_change_24h())) < 0)
                return order;
            else {
                return (-1 * order);
            }
        }
    }

    private class marketAdapter extends ArrayAdapter {

        int resource;
        LayoutInflater layoutInflater;
        private ArrayList<GetSetMarket> arraylist = null;

        public marketAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);

            this.resource = resource;
            this.arraylist = new ArrayList<GetSetMarket>();
            this.arraylist.addAll(myList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = layoutInflater.inflate(resource, parent, false);
            }
            TextView tv_marketname = (TextView) convertView.findViewById(R.id.tv_name_marketrow);
            TextView tv_marketprice = (TextView) convertView.findViewById(R.id.tv_price_marketrow);
            TextView tv_volume_marketrow = (TextView) convertView.findViewById(R.id.tv_volume_marketrow);
            TextView tv_change_marketrow = (TextView) convertView.findViewById(R.id.tv_change_marketrow);

            tv_marketname.setText(myList.get(position).getSymbol());
            tv_volume_marketrow.setText(myList.get(position).getVolume_usd());

            final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("checkbox", getActivity().MODE_PRIVATE);
            if (sharedPreferences.getString("price", null) == null) {
                tv_marketprice.setText(myList.get(position).getPrice_btc() + "");
                lay_market_price.setText("Price btc");
            } else {
                lay_market_price.setText("Price USD");
                tv_marketprice.setText(myList.get(position).getPrice_usd() + "");
            }
            Double change = 0.0;
            if (sharedPreferences.getString("percent", null) == null) {
                change = Double.valueOf(myList.get(position).getPercent_change_24h());
                tv_marketrow_change.setText("24h %");

            } else if (sharedPreferences.getString("percent", null).equals("change24")) {
                change = Double.valueOf(myList.get(position).getPercent_change_24h());
                tv_marketrow_change.setText("24h %");
            } else if (sharedPreferences.getString("percent", null).equals("change1")) {
                tv_marketrow_change.setText("1h %");
                change = Double.valueOf(myList.get(position).getPercent_change_1h());
            } else {
                change = Double.valueOf(myList.get(position).getPercent_change_7d());
                tv_marketrow_change.setText("7d %");
            }

            if (change > 0) {
                tv_change_marketrow.setTextColor(Color.parseColor("#008000"));
                tv_change_marketrow.setText(change + "");
            } else if (change < 0) {
                tv_change_marketrow.setTextColor(Color.parseColor("#FF0000"));
                tv_change_marketrow.setText(change + "");

            }

            return convertView;
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            myList.clear();
            if (charText.length() == 0) {
                myList.addAll(arraylist);
            } else {
                for (GetSetMarket wp : arraylist) {
                    if (wp.getSymbol().toLowerCase(Locale.getDefault()).contains(charText)) {
                        myList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
