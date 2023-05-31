package com.mgdapps.livemarketcap;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_pager1 extends Fragment {

    TextView tv_pager1_balancesheet, tv_pager1_balance, tv_pager1_targetbalance;
    List<GetSetHome> myList = new ArrayList<>();
    List<String> favbalancelist = null;
    List<GetSetBalanceDialogue> mgetsetlist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pager1, container, false);

        tv_pager1_balancesheet = (TextView) view.findViewById(R.id.tv_pager1_balancesheet);
        tv_pager1_balance = (TextView) view.findViewById(R.id.tv_pager1_balance);
        tv_pager1_targetbalance = (TextView) view.findViewById(R.id.tv_pager1_targetbalance);

        MainActivity activity = (MainActivity) getActivity();
        if (activity.mHomelist != null) {
            myList = activity.mHomelist;
        }


        //TODO: calculate here and show balance

        tv_pager1_balancesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                final View dialogueView = layoutInflater.inflate(R.layout.dialogue_balance, null);
                final AlertDialog dialog = builder.create();
                dialog.setView(dialogueView);

                ListView lv_balanceDialog = (ListView) dialogueView.findViewById(R.id.lv_balanceDialog);
                Button btn_dialogue_balance_cancel = (Button) dialogueView.findViewById(R.id.btn_dialogue_balance_cancel);
                Button btn_dialogue_balance_save = (Button) dialogueView.findViewById(R.id.btn_dialogue_balance_save);

                if (mgetsetlist != null) {
                    BalanceAdapter balanceAdapter = new BalanceAdapter(getActivity(), R.layout.row_dialogue_balance);
                    lv_balanceDialog.setAdapter(balanceAdapter);
                }

                btn_dialogue_balance_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

                btn_dialogue_balance_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("favlist", getActivity().MODE_PRIVATE);
                        Set<String> mybalanceset = sharedPreferences.getStringSet("balance", null);

                        List<String> favList = null;
                        if (mybalanceset != null) {
                            favList = new ArrayList<>(mybalanceset);
                        } else {
                            favList = new ArrayList<>();
                            favList.add("Bitcoin");
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                    }
                });
                dialog.show();

            }
        });

        return view;
    }

    /*private class BalanceAdapter extends ArrayAdapter {

        LayoutInflater layoutInflater;
        int resource;

        public BalanceAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mgetsetlist.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflater.inflate(resource, parent, false);
            }
            TextView tv_dialoguerow_price=(TextView) convertView.findViewById(R.id.tv_dialoguerow_price);
            TextView tv_dialoguerow_name=(TextView) convertView.findViewById(R.id.tv_dialoguerow_name);
            EditText et_dialoguerow_targetprice=(EditText)convertView.findViewById(R.id.et_dialoguerow_targetprice);
            EditText et_dialoguerow_availableCoins=(EditText)convertView.findViewById(R.id.et_dialoguerow_availableCoins);

            tv_dialoguerow_name.setText(mgetsetlist.get(position).getName());
            tv_dialoguerow_price.setText(mgetsetlist.get(position).getCurrentPrice());
            et_dialoguerow_availableCoins.setHint("1000");
            et_dialoguerow_targetprice.setHint(mgetsetlist.get(position).getCurrentPrice());

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("favlist", getActivity().MODE_PRIVATE);
            Set<String> mybalanceset = sharedPreferences.getStringSet("balance", null);
            if (mybalanceset != null) {
                favbalancelist = new ArrayList<>(mybalanceset);
            }

*//*
            for (String a:favbalancelist)
            {
                String[] parts=a.split(",");
                String title=parts[0];
                String balance=parts[1];
                String target=parts[2];

                if (mgetsetlist.contains(title))
                {
                    if (title!=null&&target!=null)
                    {
                        et_dialoguerow_availableCoins.setText(balance);
                        et_dialoguerow_targetprice.setText(target);
                    }
                }
            }
*//*



//btc,0.00000085


            SharedPreferences.Editor editor = sharedPreferences.edit();
*//*
            if (!favList.contains(coinName)) {
                favList.add(coinName);
                Set<String> myset1 = new HashSet<>();
                myset1.addAll(favList);
                editor.putStringSet("mylist", myset1);
                editor.commit();
            }
*//*

            return convertView;
        }
    }*/

    //started

    private class BalanceAdapter extends ArrayAdapter {

        int resource;
        LayoutInflater layoutInflater;

        public BalanceAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);

            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = layoutInflater.inflate(resource, parent, false);
            }
            TextView tv_dialoguerow_price = (TextView) convertView.findViewById(R.id.tv_dialoguerow_price);
            TextView tv_dialoguerow_name = (TextView) convertView.findViewById(R.id.tv_dialoguerow_name);
            EditText et_dialoguerow_targetprice = (EditText) convertView.findViewById(R.id.et_dialoguerow_targetprice);
            EditText et_dialoguerow_availableCoins = (EditText) convertView.findViewById(R.id.et_dialoguerow_availableCoins);

            tv_dialoguerow_name.setText(myList.get(position).getSymbol());
            tv_dialoguerow_price.setText(myList.get(position).getPrice_btc());
            et_dialoguerow_availableCoins.setHint("1000");
            et_dialoguerow_targetprice.setHint(myList.get(position).getPrice_btc());

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

    }
}
