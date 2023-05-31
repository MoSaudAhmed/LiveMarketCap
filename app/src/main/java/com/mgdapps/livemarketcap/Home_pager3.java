package com.mgdapps.livemarketcap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Home_pager3 extends Fragment {

    TextView tv_pager3_cryptolink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_pager3, container, false);

        tv_pager3_cryptolink=(TextView)view.findViewById(R.id.tv_pager3_cryptolink);

        tv_pager3_cryptolink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link="https://www.cryptopia.co.nz/Register?referrer=SaudAhmed";
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });

        return view;
    }

}
