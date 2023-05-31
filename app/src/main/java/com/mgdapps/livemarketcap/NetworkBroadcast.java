package com.mgdapps.livemarketcap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by HP on 12/10/2017.
 */

public class NetworkBroadcast extends BroadcastReceiver {


    InterfaceNetwork mnetworkInterfaceNetwork;

    @Override
    public void onReceive(final Context context, Intent intent) {

        mnetworkInterfaceNetwork =(InterfaceNetwork)context;

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo==null || !networkInfo.isConnectedOrConnecting())
        {
            mnetworkInterfaceNetwork.Callback(1);
        }
        else
        {
            mnetworkInterfaceNetwork.Callback(2);
        }

    }
}
