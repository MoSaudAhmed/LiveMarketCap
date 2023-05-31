package com.mgdapps.livemarketcap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by HP on 12/14/2017.
 */

public class InternetCheckBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
            Intent intent1=new Intent(context,BackgroundService.class);
            context.startService(intent1);
        } else {
            Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
        }

    }
}
